package cn.diffpi.resource.platform.role.model;

import java.util.ArrayList;
import java.util.List;

import cn.diffpi.resource.platform.menu.model.PtMenu;
import cn.dreampie.orm.Model;
import cn.dreampie.orm.Record;
import cn.dreampie.orm.annotation.Table;

@Table(name="pt_menu_role")
public class PtMenuRole extends Model<PtMenuRole>{
	public static final PtMenuRole dao = new PtMenuRole();
	
	/***
	 * 获取菜单列表
	 * @return
	 */
	public List<Record> menusAll(Long roleId , Integer state){
		
		List<Record> menus = new Record().find("SELECT pm.*,pmr.id as pmr_id FROM pt_menu pm LEFT JOIN (SELECT * FROM pt_menu_role where role_id = "+roleId+") pmr on pm.id = pmr.menu_id order by pm.id asc");
		
		return PtMenu.dao.builderMenus(menus);
	}
	
	public boolean upMenusRole(Long roleId , String[] menusId){
		dao.deleteBy(" role_id = ? ", roleId);
		
		List<PtMenuRole> menuRoles = new ArrayList<PtMenuRole>();
		for (String menuId : menusId) {
			menuRoles.add(new PtMenuRole().set("role_id", roleId).set("menu_id", menuId));
		}
		
		if(menuRoles.size() > 0){
			return dao.save(menuRoles);
		}
		
		return true;
	}
}
