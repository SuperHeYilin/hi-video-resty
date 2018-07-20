package cn.diffpi.resource.platform.role.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.diffpi.kit.DateUtil;
import cn.diffpi.resource.platform.menu.model.PtMenu;
import cn.diffpi.resource.platform.user.model.PtUser;
import cn.dreampie.orm.Model;
import cn.dreampie.orm.Record;
import cn.dreampie.orm.annotation.Table;

/**
 *  Created by one__l on 2016年8月18日
 */
@Table(name="pt_role")
public class PtRole extends Model<PtRole>{
	public static final PtRole dao = new PtRole();
	
	/***
	 * 构造roles达到前端解析结构
	 * @param roles
	 * @return
	 */
	public List<PtRole> builderRoles(List<PtRole> roles){
		
		List<PtRole> bRoles = new ArrayList<PtRole>();
		
		if(roles == null || roles.size() < 1) return bRoles;
		
		Map<Long , PtRole> mapRoles = new HashMap<Long, PtRole>();
		for (PtRole ptRole : roles) { 
			if(ptRole.get("pid") == null ){
				bRoles.add(ptRole);
			}
			mapRoles.put(ptRole.<Long>get("id"), ptRole);
		}
		
		for (PtRole ptRole : roles) {
			if(ptRole.get("pid") != null){
				PtRole pRole = mapRoles.get(ptRole.<Long>get("pid"));
				if(pRole == null) continue;
				List<PtRole> childRoles;
				if(pRole.get("childs") != null){
					childRoles = pRole.get("childs");
				} else {
					childRoles = new ArrayList<PtRole>();
					pRole.put("childs", childRoles);
				}
				childRoles.add(ptRole);
			}
		}
		
		return bRoles;
	}
	/***
	 * 获取指定角色下的角色和用户
	 * @return
	 */
	public List<Record> getRbyid(int id){
		List<PtRole> roles = dao.findBy("pid = ?", id);
		List<PtUserRole> userroles = PtUserRole.dao.findBy(" role_id = ?", id);
		List<Record> lr = new ArrayList<Record>();
		lr.addAll(buildRoles(roles));
		lr.addAll(buildUser(userroles));
		return lr;
	}
	/***
	 * 获取角色列表
	 * @return
	 */
	public List<PtRole> rolesAll(){
		
		List<PtRole> roles = dao.findAll();
		
		return builderRoles(roles);
	}
	/***
	 * 获取最顶层角色列表
	 * @return
	 */
	public Object getPall(){
		
		List<PtRole> roles = dao.findBy("pid is null");
		
		return buildRoles(roles);
	}
	/**
	    * @Title: inRoleGetAllUser
	    * @Description: TODO(根据 role_id 获取该角色下的所有角色包含当前角色)
	    * @author FlyLB
	    * @param role_id
	    * @throws
	    */
	public List<PtRole> inRoleGetAllRole(String role_id){
	    List<PtRole> lnewRole = new ArrayList<PtRole>();
	    PtRole pr =  dao.findById(role_id);
	    lnewRole.add(pr);
        List<PtRole> lpr = dao.findBy("pid = ?", role_id);
        if(lpr.size()>0){
            for (PtRole ptRole : lpr)
            {
                lnewRole.addAll(inRoleGetAllRole(ptRole.get("id").toString()));
            }
        }
        return lnewRole;
	}
	/**
	* @Title: inRoleGetAllUser
	* @Description: TODO(根据 role_id 获取该角色下的所有用户包括子角色)
	* @author FlyLB
	* @param role_id
	* @throws
	*/
	public void inRoleGetAllUser(String role_id){
	    List<PtRole> lpRole = inRoleGetAllRole(role_id);
	    System.out.println(lpRole);
	}
	
	public List<Record> buildUser(List<PtUserRole> users){
		List<Record> lr = new ArrayList<Record>();
		String uids = "";
		for (PtUserRole ptur : users) {
			uids+=ptur.get("user_id")+",";
		}
		if(uids.length()>0){
			uids=uids.substring(0,uids.length()-1);
			List<PtUser> lpu = PtUser.dao.findBy("id in ("+uids+")");
			for (PtUser pu : lpu) {
				Record r = new Record();
				r.put("name",pu.get("username"));
				r.put("key","u-"+pu.get("id"));
				r.put("isLeaf",true);
				r.put("icon",true);
				lr.add(r);
			}
		}
		
		return lr;
	}
	public List<Record> buildRoles(List<PtRole> roles){
		List<Record> lr = new ArrayList<Record>();
		for (PtRole pt : roles) {
			Record r = new Record();
			r.put("name", pt.get("name"));
			r.put("key", "r-"+pt.get("id"));
			r.put("isLeaf",false);
			r.put("disabled",true);
			r.put("disableCheckbox",true);
			lr.add(r);
		}
		return lr;
	}
	
	/***
	 * 保存角色
	 * @param role
	 * @return
	 */
	public boolean save(PtRole role){
		
		role.set("updated_at", DateUtil.getCurrentDate());
		
		if(role.get("id") != null){
			return role.update();
		} else {
			role.set("created_at", DateUtil.getCurrentDate());
			return role.save();
		}
	}
	
	/***
	 * 更改角色索引
	 * @param firstRole
	 * @param lastRole
	 * @return
	 */
	public boolean changeIndex(PtRole firstRole , PtMenu lastRole){
		return true;
	}
	
	/***
	 * 删除角色
	 * @param id 角色id
	 * @return
	 */
	public boolean delete(Long id){
		return dao.deleteById(id);
	}
}
