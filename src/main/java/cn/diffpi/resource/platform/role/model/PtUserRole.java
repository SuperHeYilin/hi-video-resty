package cn.diffpi.resource.platform.role.model;

import java.util.List;

import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;

@Table(name="pt_user_role")
public class PtUserRole extends Model<PtUserRole>{
	public static final PtUserRole dao = new PtUserRole();
	
	public PtUserRole save(Long userId , Long roleId){
		
		PtUserRole userRole = new PtUserRole();
		userRole.set("user_id", userId).set("role_id", roleId);
		
		userRole.save();
		return userRole;
	}
	
	/**
	 * 根据用户ID 查询角色
	 * @param uid
	 * @return
	 */
	public List<PtRole> getUserByRole(Integer uid){
		List<PtRole> pt=PtRole.dao.find("select * from pt_role pr where pr.id in (select pur.role_id from pt_user_role pur where pur.user_id=? )",uid);
		return pt.size()>0?pt:null;
	}
	
}
