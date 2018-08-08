package cn.diffpi.resource.module.user.model;

import cn.diffpi.resource.BaseModel;
import cn.dreampie.orm.annotation.Table;

/***
 * 用户个人信息类
 */
@Table(name = "client_person")
public class UserPerson extends BaseModel<UserPerson> {
    public final static UserPerson dao = new UserPerson();

    /***
     * 得到用户
     * @param userId
     * @return
     */
    public UserPerson person(Integer userId) {

        UserPerson clientPerson = UserPerson.dao.findFirstBy(" user = ?", userId);

        return clientPerson;
    }

    public void filter() {
        this.remove("id", "user", "state");
    }
}
