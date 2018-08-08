package cn.diffpi.resource.platform.permission.model;

import java.util.List;

import cn.diffpi.resource.BaseModel;
import cn.diffpi.resource.platform.role.model.PtRole;
import cn.diffpi.resource.platform.role.model.PtUserRole;
import cn.dreampie.orm.annotation.Table;

/**
 * 权限角色关联表
 *
 * @author lb
 */
@Table(name = "pt_role_permission", cached = true)
public class PtRolePermission extends BaseModel<PtRolePermission> {
    public static final PtRolePermission dao = new PtRolePermission();

    /**
     * 获取用户的数据权限
     *
     * @return
     */
    public String getDataAuth(Integer uid, String url) {
        List<PtRole> pr = PtUserRole.dao.getUserByRole(uid);
        for (PtRole ptRole : pr) {
            ptRole.get("id");
        }
        return "";
    }
}
