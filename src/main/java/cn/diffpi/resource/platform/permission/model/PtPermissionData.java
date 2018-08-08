package cn.diffpi.resource.platform.permission.model;

import cn.diffpi.resource.BaseModel;
import cn.dreampie.common.http.HttpRequest;
import cn.dreampie.orm.annotation.Table;

@Table(name = "pt_role_permission_data", cached = true)
public class PtPermissionData extends BaseModel<PtPermissionData> {
    public static final PtPermissionData dao = new PtPermissionData();

}
