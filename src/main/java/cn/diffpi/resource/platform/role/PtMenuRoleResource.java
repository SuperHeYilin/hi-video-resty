package cn.diffpi.resource.platform.role;

import java.util.List;

import cn.diffpi.resource.ApiResource;
import cn.diffpi.resource.platform.role.model.PtMenuRole;
import cn.dreampie.orm.Record;
import cn.dreampie.route.annotation.API;
import cn.dreampie.route.annotation.GET;
import cn.dreampie.route.annotation.PUT;

@API("/pt/menus/roles")
public class PtMenuRoleResource extends ApiResource{
	
	@GET
	public List<Record> getMenuRoles(Long roleId , Integer state){
		
		return PtMenuRole.dao.menusAll(roleId, state);
	}
	
	@PUT
	public boolean upMenuRoles(String menus , Long roleId){
		String[] menuIds = null;
 		if(menus != null && !menus.equals("")){
			menuIds = menus.split(",");
		} else {
			menuIds = new String[0];
		}
		
		return PtMenuRole.dao.upMenusRole(roleId , menuIds);
	}
	
}
