package cn.diffpi.resource.platform.user.model;

import cn.diffpi.resource.BaseModel;
import cn.dreampie.orm.annotation.Table;

@Table(name = "client_user", cached = true, primaryKey = "id", generatedKey = "")
public class ClientUser extends BaseModel<ClientUser> {
    public final static ClientUser dao = new ClientUser();
}
