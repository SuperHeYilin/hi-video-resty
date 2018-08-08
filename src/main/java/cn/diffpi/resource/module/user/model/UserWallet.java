package cn.diffpi.resource.module.user.model;

import cn.diffpi.kit.DateUtil;
import cn.diffpi.resource.BaseModel;
import cn.dreampie.common.http.exception.HttpException;
import cn.dreampie.common.http.result.HttpStatus;
import cn.dreampie.orm.annotation.Table;

/***
 * 用户钱包
 */
@Table(name = "client_user_wallet")
public class UserWallet extends BaseModel<UserWallet> {
    public static final UserWallet dao = new UserWallet();

    /***
     * 得到钱包信息
     * @param userId
     * @return
     */
    public UserWallet wallet(Integer userId) {

        UserWallet userWallet = UserWallet.dao.findFirstBy(" user = ?", userId);

        return userWallet;
    }

    /***
     * 通过明细更新钱包
     * @param clientUserWalletDetail 钱包明细
     * @return
     */
    public boolean updateWalletByDetail(UserWalletDetail clientUserWalletDetail) {

        if (clientUserWalletDetail == null) {
            throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "paramErr", "未找到余额使用明细");
        }

        UserWallet clientUserWallet = dao.findById(clientUserWalletDetail.get("wallet"));
        double balance = clientUserWallet.<Double>get("balance");
        double value = clientUserWalletDetail.<Double>get("value");
        if (clientUserWalletDetail.get("plus_minus").equals("0")) {
            balance = balance - value;
            if (balance < 0) {
                throw new HttpException(HttpStatus.UNPROCESSABLE_ENTITY, "paramErr", "余额不足");
            }
        } else {
            balance = balance + value;
        }

        return clientUserWallet
                .set("balance", balance)
                .set("last_use_time", DateUtil.getCurrentDate())
                .update();
    }

    public void filter() {
        this.remove("id", "user");
    }
}
