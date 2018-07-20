package cn.diffpi.resource.module.user.model;

import cn.diffpi.kit.DateUtil;
import cn.diffpi.kit.StringKit;
import cn.diffpi.resource.BaseModel;
import cn.diffpi.resource.module.user.constant.UserConstant;
import cn.dreampie.orm.annotation.Table;

/***
 * 用户钱包明细
 */
@Table(name="client_user_wallet_detail")
public class UserWalletDetail extends BaseModel<UserWalletDetail>{
    public static final UserWalletDetail dao = new UserWalletDetail();

    /***
     * 使用钱包
     * @param wallet 钱包id
     * @param userId 用户id
     * @param body 使用内容
     * @param detail 使用明细
     * @param money 金额大小
     * @param plusMinus 是增是减
     * @param type 使用类型
     * @param walletState 使用状态 0需要后续自己调用更新 1立即执行一般用于需要预先扣钱更新钱包的情况
     * @return
     */
    public String useWallet(Integer wallet , Integer userId , String body , String detail , Double money , UserConstant.PlusMinus plusMinus , UserConstant.WalletType type , String doState , UserConstant.WalletState walletState) {
        UserWalletDetail clientUserWalletDetail = new UserWalletDetail();

        String no = StringKit.currentTimeMillis()+userId+"";

        clientUserWalletDetail
                .set("no",no)
                .set("wallet",wallet)
                .set("user",userId)
                .set("body",body)
                .set("detail",detail)
                .set("value",money)
                .set("plus_minus",plusMinus.getValue()+"")
                .set("use_type",type.getValue()+"")
                .set("use_state",walletState.getValue()+"")
                .set("do_state", doState)//默认都是待处理
                .set("create_time", DateUtil.getCurrentDate())
                .set("update_time", DateUtil.getCurrentDate())
                .save();

        if(walletState == UserConstant.WalletState.TIMELY) {
            UserWallet.dao.updateWalletByDetail(clientUserWalletDetail);
        }

        return no;
    }
}
