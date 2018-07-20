package cn.diffpi.resource.module.user.model;

import cn.diffpi.resource.BaseModel;
import cn.dreampie.orm.annotation.Table;

/***
 * 用户账户信息
 */
@Table(name="client_user_account")
public class UserAccount extends BaseModel<UserAccount>{
    public static final UserAccount dao = new UserAccount();

    /***
     * 得到账户信息
     * @param userId
     * @return
     */
    public UserAccount account(Integer userId){

        UserAccount userAccount = UserAccount.dao.findFirstBy(" user = ? and is_stop = '1'", userId);

        return userAccount;
    }

    public void filter(){
        this.remove("id","user");
    }
}
