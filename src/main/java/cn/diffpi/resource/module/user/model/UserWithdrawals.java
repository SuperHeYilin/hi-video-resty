package cn.diffpi.resource.module.user.model;

import cn.diffpi.resource.BaseModel;
import cn.dreampie.orm.annotation.Table;

/***
 * 用户提现类
 */
@Table(name = "client_user_withdrawals")
public class UserWithdrawals extends BaseModel<UserWithdrawals> {
    public static final UserWithdrawals dao = new UserWithdrawals();
}
