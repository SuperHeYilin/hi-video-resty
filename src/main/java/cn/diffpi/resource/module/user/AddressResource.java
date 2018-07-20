package cn.diffpi.resource.module.user;

import cn.diffpi.core.kit.SplitPage;
import cn.diffpi.kit.StringKit;
import cn.diffpi.resource.ApiResource;
import cn.diffpi.resource.module.user.model.UserAddress;
import cn.dreampie.orm.transaction.Transaction;
import cn.dreampie.route.annotation.API;
import cn.dreampie.route.annotation.GET;
import cn.dreampie.route.annotation.POST;

@API("/module/client/users/address")
public class AddressResource extends ApiResource {

    @GET
    public SplitPage list() {
        SplitPage page = getModel(SplitPage.class,true);

        String sql = "SELECT\n" +
                "	cua.*,\n" +
                "	cu.phonenum,\n" +
                "	cu.nickname, \n" +
                "	cu.realname \n" +
                "FROM\n" +
                "	client_user_address cua\n" +
                "	LEFT JOIN client_user cu ON cua.`user` = cu.id \n" +
                "WHERE\n" +
                "	1 = 1";

        String name = getParam("name");
        if(StringKit.isNotBlank(name)){
            sql += " and cp.nickname like '%"+name+"%'";
        }

        UserAddress.dao.splitPageBaseSql(page, "", sql);
        return page;
    }

    /**
     * 批量上传地址
     * @param fileName
     * @return
     */
    @POST("/import")
    @Transaction
    public boolean uploadGoods(String fileName) {
        return UserAddress.dao.upload(fileName, getRequest().getRealPath("upload"));
    }

}
