package cn.diffpi.resource.platform.role;

import cn.diffpi.core.kit.BaseSv;
import cn.diffpi.core.kit.SplitPage;
import cn.diffpi.resource.ApiResource;
import cn.diffpi.resource.platform.role.model.PtUserRole;
import cn.dreampie.route.annotation.API;
import cn.dreampie.route.annotation.DELETE;
import cn.dreampie.route.annotation.GET;
import cn.dreampie.route.annotation.POST;

@API("/pt/users/roles")
public class PtUserRoleResource extends ApiResource{
	
	@GET()
	public SplitPage getUserRoles(Long roleId , Integer state){
		
		SplitPage page = getModel(SplitPage.class,true);
		
		StringBuffer sb =  new StringBuffer();
		
		sb.append("SELECT pu.*,pur.id as pur_id FROM pt_user pu LEFT JOIN (SELECT * FROM pt_user_role where role_id = "+roleId+") pur on pu.id = pur.user_id");
		sb.append(" where 1 = 1");
		
		if(state != null && state == 2){
			sb.append(" and pur.id IS NOT NULL");
		} else if(state != null && state == 1){
			sb.append(" and pur.id IS NULL");
		}
		 
		BaseSv.me.splitPageBaseSql(page , "", sb.toString());
		
	    return page; 
	}
	
	@POST
	public PtUserRole saveUserRole(Long userId , Long roleId){
		return PtUserRole.dao.save(userId,roleId);
	}
	
	@DELETE("/:id")
	public boolean delUserRole(Long id){
		return PtUserRole.dao.deleteById(id);
	}
}
