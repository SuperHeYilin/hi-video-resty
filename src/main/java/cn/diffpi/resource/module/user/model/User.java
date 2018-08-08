package cn.diffpi.resource.module.user.model;

import cn.diffpi.resource.BaseModel;
import cn.dreampie.orm.annotation.Table;

/***
 * 用户账户信息类
 */
@Table(name = "client_user")
public class User extends BaseModel<User> {
    public static final User dao = new User();

    public void filter() {
        this.remove(new String[]{"pwd", "del", "pay_pwd"});
    }
}
