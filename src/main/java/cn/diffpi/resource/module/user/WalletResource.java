package cn.diffpi.resource.module.user;

import cn.diffpi.core.kit.SplitPage;
import cn.diffpi.kit.StringKit;
import cn.diffpi.resource.ApiResource;
import cn.diffpi.resource.module.user.constant.UserConstant;
import cn.diffpi.resource.module.user.model.User;
import cn.diffpi.resource.module.user.model.UserWallet;
import cn.diffpi.resource.module.user.model.UserWalletDetail;
import cn.dreampie.common.http.exception.HttpException;
import cn.dreampie.common.http.result.HttpStatus;
import cn.dreampie.route.annotation.API;
import cn.dreampie.route.annotation.GET;
import cn.dreampie.route.annotation.POST;

@API("/module/client/users/wallets")
public class WalletResource extends ApiResource {

    @GET
    public SplitPage list() {
        SplitPage page = getModel(SplitPage.class,true);

        String sql = "SELECT\n" +
                "	cuwd.*,\n" +
                "	cu.phonenum,\n" +
                "	cp.nickname,\n" +
                "	cuw.balance \n" +
                "FROM\n" +
                "	client_user_wallet_detail cuwd\n" +
                "	LEFT JOIN client_user_wallet cuw ON cuw.USER = cuwd.\n" +
                "	USER LEFT JOIN client_user cu ON cu.id = cuwd.\n" +
                "	USER LEFT JOIN client_person cp ON cp.`user` = cuwd.USER \n" +
                "WHERE\n" +
                "	1 =1";

        String name = getParam("name");
        String mode = getParam("mode");
        if(StringKit.isNotBlank(name)){
            sql += " and (cp.nickname like '%"+name+"%' or cu.phonenum like '%"+name+"%')";
        }
        if(StringKit.isNotBlank(mode)){
            sql += " and cuwd.use_type = '"+mode+"'";
        }

        UserWallet.dao.splitPageBaseSql(page, "", sql);
        return page;
    }

    /***
     * 余额变更
     * @param userId
     * @param body
     * @param money
     * @param plusMinus
     */
    @POST("/change")
    public void changeBalance(Integer userId , String body , Double money , String plusMinus){
        UserWallet clientUserWallet = UserWallet.dao.findFirstBy(" user = ? ",userId);
        if(clientUserWallet == null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY,"paramErr","未找到用户钱包");
        }

        UserConstant.PlusMinus plusMinus1 = plusMinus.equals("0") ? UserConstant.PlusMinus.MINUS : UserConstant.PlusMinus.PLUS;
        UserWalletDetail.dao.useWallet(clientUserWallet.<Integer>get("id") , userId , body , "" , money , plusMinus1 , UserConstant.WalletType.CHANGE , "1" , UserConstant.WalletState.TIMELY);

    }

}
