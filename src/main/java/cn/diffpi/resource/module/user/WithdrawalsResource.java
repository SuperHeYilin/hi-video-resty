package cn.diffpi.resource.module.user;

import cn.diffpi.core.kit.SplitPage;
import cn.diffpi.kit.StrKit;
import cn.diffpi.kit.StringKit;
import cn.diffpi.resource.ApiResource;
import cn.diffpi.resource.module.user.model.UserWithdrawals;
import cn.diffpi.security.annotation.AuthSign;
import cn.dreampie.client.Client;
import cn.dreampie.client.ClientRequest;
import cn.dreampie.common.http.result.HttpStatus;
import cn.dreampie.common.util.properties.Proper;
import cn.dreampie.orm.transaction.Transaction;
import cn.dreampie.route.annotation.API;
import cn.dreampie.route.annotation.GET;
import cn.dreampie.route.annotation.POST;

@API("/module/client/users/withdrawals")
@AuthSign
public class WithdrawalsResource extends ApiResource {

    @GET
    public SplitPage list() {
        SplitPage page = getModel(SplitPage.class,true);

        String sql = "SELECT\n" +
                "	cuw.*,\n" +
                "	cua.ali_name,\n" +
                "	cua.ali_account,\n" +
                "	cua.wechat_account,\n" +
                "	cua.wechat_name,\n" +
                "	cua.wechat_payment_code,\n" +
                "	cua.bank_name,\n" +
                "	cua.bank_card,\n" +
                "	cua.account_name,\n" +
                "	cua.open_city,\n" +
                "	cp.nickname,\n" +
                "	cp.avatar,\n" +
                "	cu.phonenum \n" +
                "FROM\n" +
                "	client_user_withdrawals cuw\n" +
                "	LEFT JOIN client_user cu ON cu.id = cuw.`user`\n" +
                "	LEFT JOIN client_person cp ON cuw.`user` = cp.`user`\n" +
                "	LEFT JOIN client_user_account cua ON cua.id = cuw.account\n" +
                "WHERE\n" +
                "	1 = 1";

        String name = getParam("name");
        String state = getParam("state");
        if(StringKit.isNotBlank(name)){
            sql += " and (cp.nickname like '%"+name+"%' or cu.phonenum like '%"+name+"%')";
        }
        if(StringKit.isNotBlank(state)){
            sql += " and cuw.state = '"+state+"'";
        }

        UserWithdrawals.dao.splitPageBaseSql(page, "", sql);
        return page;
    }

    /**
     *
     * @param ids
     * @param feedback
     * @return
     */
    @POST("/confirm")
    public boolean confirmProcess (String ids , String feedback) {

        if(StrKit.notBlank(ids)) {
            for (String id: ids.split(",") ){
                UserWithdrawals withdrawals = UserWithdrawals.dao.findById(id);

                Client c = new Client(Proper.use("application.properties").get("finish.url"));
                ClientRequest cr = new ClientRequest();
                cr.addParam("no",withdrawals.get("no").toString());
                c.build(cr);
                if(c.post().getStatus()== HttpStatus.OK){
                    withdrawals
                            .set("state","1")
                            .update();
                }
            }

            return true;
        }

        return false;
    }

    /***
     * 拒绝提现
     * @param ids
     * @param feedback
     * @return
     */
    @POST("/refuse")
    @Transaction
    public boolean refuse (String ids  , String feedback) {
        if(StrKit.notBlank(ids)) {
            for (String id: ids.split(",") ){
                UserWithdrawals withdrawals = UserWithdrawals.dao.findById(id);

                Client c = new Client(Proper.use("application.properties").get("close.url"));
                ClientRequest cr = new ClientRequest();
                cr.addParam("no",withdrawals.get("no").toString());
                cr.addParam("feedback",feedback);
                c.build(cr);
                if(c.post().getStatus()== HttpStatus.OK){
                    withdrawals
                            .set("feedback",feedback)
                            .set("state","2")
                            .update();
                }
            }

            return true;
        }

        return false;
    }
}
