package cn.diffpi.resource.platform.role;

import java.util.ArrayList;
import java.util.List;

import cn.diffpi.resource.ApiResource;
import cn.diffpi.resource.platform.role.model.PtRole;
import cn.diffpi.security.annotation.AuthSign;
import cn.dreampie.route.annotation.API;
import cn.dreampie.route.annotation.DELETE;
import cn.dreampie.route.annotation.GET;
import cn.dreampie.route.annotation.POST;

@API("/pt/roles")
@AuthSign
public class PtRoleResource extends ApiResource {

    /***
     * 获取所有角色
     * @return
     */
    @GET
    public List<PtRole> rolesAll() {

        return PtRole.dao.rolesAll();
    }

    /***
     * 获取顶层角色
     * @return
     */
    @GET("/parent")
    public Object getPall() {
        return PtRole.dao.getPall();
    }

    /***
     * 获取角色下的用户和子角色
     * @return
     */
    @GET("/parent/:id")
    public Object getRbyid(int id) {
        return PtRole.dao.getRbyid(id);
    }

    /***
     * 根据选中的角色和用户遍历返回用户
     * @return
     */
    @GET("/parent/listbyru")
    public Object getRbuser() {
//        JSONArray json  = JSONArray.parseArray(getParam("keys"));
//        for (Object o : json)
//        {
//            JSONObject jo = (JSONObject) o;
//            System.out.println(jo.get("key"));
//        }
        String str = getParam("keys");
        String ss[] = str.split(",");
        List<PtRole> pr = new ArrayList<PtRole>();
        for (String s : ss) {
            if (s.contains("u-")) {
                System.out.println(s);
            } else if (s.contains("r-")) {
                PtRole.dao.inRoleGetAllUser(s.substring(2, s.length()));
                System.out.println(s);
            }
        }
        return null;
    }

    /***
     * 保存角色
     * @param role
     */
    @POST
    public PtRole saveMenu(PtRole role) {
        PtRole.dao.save(role);
        return role;
    }

    /***
     * 删除角色
     * @param role_id
     */
    @DELETE("/:id")
    public boolean delRole(Long id) {
        return PtRole.dao.delete(id);
    }
}	
