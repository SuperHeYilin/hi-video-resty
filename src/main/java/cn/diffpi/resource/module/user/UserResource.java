package cn.diffpi.resource.module.user;

import cn.diffpi.core.kit.SplitPage;
import cn.diffpi.kit.StringKit;
import cn.diffpi.resource.ApiResource;
import cn.diffpi.resource.module.user.model.User;
import cn.diffpi.resource.module.user.model.UserAccount;
import cn.diffpi.resource.module.user.model.UserPerson;
import cn.diffpi.resource.module.user.model.UserWallet;
import cn.dreampie.client.Client;
import cn.dreampie.client.ClientRequest;
import cn.dreampie.common.http.HttpMessage;
import cn.dreampie.common.http.exception.HttpException;
import cn.dreampie.common.http.result.HttpStatus;
import cn.dreampie.common.util.properties.Proper;
import cn.dreampie.route.annotation.API;
import cn.dreampie.route.annotation.GET;
import cn.dreampie.route.annotation.POST;

/***
 * 用户管理控制类
 */
@API("/module/client/users")
public class UserResource extends ApiResource {

    @GET
    public SplitPage list() {
        SplitPage page = getModel(SplitPage.class, true);

        String sql = "SELECT\n" +
                "	cu.*, cp.avatar,\n" +
                "	cp.nickname as name,\n" +
                "	cua.ali_account,\n" +
                "	cua.wechat_account,\n" +
                "	cua.bank_card,\n" +
                "	cuw.balance\n" +
                "FROM\n" +
                "	client_user cu\n" +
                "LEFT JOIN client_person cp ON cu.id = cp.`user`\n" +
                "LEFT JOIN client_user_account cua ON cu.id = cua.`user`\n" +
                "LEFT JOIN client_user_wallet cuw ON cu.id = cuw.`user`\n" +
                "WHERE 1 = 1 and cua.is_stop = '1'";

        String name = getParam("name");
        if (StringKit.isNotBlank(name)) {
            sql += " and cp.nickname like '%" + name + "%'";
        }

        User.dao.splitPageBaseSql(page, "", sql);
        return page;
    }

    @GET("/:id")
    public User get(Integer id) {
        User user = User.dao.findFirstBy("id=? and del = '0'", id);
        if (user != null) {
            user.put("person", UserPerson.dao.person(id))
                    .put("account", UserAccount.dao.account(id))
                    .put("wallet", UserWallet.dao.wallet(id));
            user.filter();
        } else {
            throw new HttpException(HttpStatus.NOT_FOUND, HttpMessage.NOT_FOUND);
        }

        return user;
    }

    /***
     * 停用用户
     */
    @POST("/stop")
    public void stop(Integer id) {
        Client c = new Client(Proper.use("application.properties").get("stop.url"));
        ClientRequest cr = new ClientRequest();
        cr.addParam("key", id + "");
        c.build(cr);
        if (c.delete().getStatus() == HttpStatus.OK) {
            new User()
                    .set("id", id)
                    .set("state", "3")
                    .update();
        }

    }

    /***
     * 启用用户
     */
    @POST("/start")
    public void start(Integer id) {
        new User()
                .set("id", id)
                .set("state", "0")
                .update();
    }
}
