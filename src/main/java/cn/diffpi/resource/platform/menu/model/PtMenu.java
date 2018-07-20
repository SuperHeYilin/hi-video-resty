package cn.diffpi.resource.platform.menu.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.diffpi.kit.DateUtil;
import cn.diffpi.kit.StringKit;
import cn.diffpi.resource.platform.role.model.PtRole;
import cn.diffpi.resource.platform.user.model.PtUser;
import cn.dreampie.orm.Model;
import cn.dreampie.orm.Record;
import cn.dreampie.orm.annotation.Table;

@Table(name="pt_menu",cached=true)
public class PtMenu extends Model<PtMenu>{
	public static final PtMenu dao = new PtMenu();
	
	/***
	 * 构造menus达到前端解析结构
	 * @param menus
	 * @return
	 */
	public List<Record> builderMenus(List<Record> menus){
		
		List<Record> bMenus = new ArrayList<Record>();
		
		if(menus == null || menus.size() < 1) return bMenus;
		
		Map<Long , Record> mapMenus = new HashMap<Long, Record>();
		for (Record ptMenu : menus) {
			if(ptMenu.get("p_menu") == null){
				bMenus.add(ptMenu);
			}
			mapMenus.put(ptMenu.<Long>get("id"), ptMenu);
		}
		
		for (Record ptMenu : menus) {
			if(ptMenu.get("p_menu") != null){
				Record pMenu = mapMenus.get(ptMenu.<Integer>get("p_menu"));
				if(pMenu == null) continue;
				List<Record> childMenus;
				if(pMenu.get("childs") != null){
					childMenus = pMenu.get("childs");
				} else {
					childMenus = new ArrayList<Record>();
					pMenu.put("childs", childMenus);
				}
				childMenus.add(ptMenu);
			}
		}
		
		return bMenus;
	}
	
	/***
	 * 获取菜单列表
	 * @return
	 */
	public List<Record> menusAll(){
		
		List<Record> menus = new Record().find("SELECT * FROM pt_menu");
		
		return builderMenus(menus);
	}
	
	/***
	 * 获取用户菜单列表
	 * @return
	 */
	public List<Record> userMenusAll(Integer userId){
		List<PtRole> roles = new PtUser().set("id", userId).getRoles();
		
		if(roles.size() < 1) return new ArrayList<Record>();
		
		List<String> strings = new ArrayList<String>();
		for (PtRole role : roles) {
			strings.add(role.<Integer>get("id") + "");
		}
		
		List<Record> menus = new Record().find("SELECT pm.* FROM pt_menu pm LEFT JOIN pt_menu_role pmr ON pm.id = pmr.menu_id where role_id in ("+StringKit.getListStr(strings, ",")+") GROUP BY pm.id");
		
		return builderMenus(menus);
	}
	
	/***
	 * 保存菜单
	 * @param name 菜单名称
	 * @param p_menu 父级菜单id
	 * @return
	 */
	public boolean save(String name , Integer p_menu){
		
		PtMenu menu = new PtMenu();
		
		menu.set("p_menu", p_menu);
		menu.set("menu_name", name);
		menu.set("create_at", DateUtil.getCurrentDate());
		menu.set("update_at", DateUtil.getCurrentDate());
		
		return menu.save();
	}
	
	/***
	 * 保存菜单
	 * @param menu
	 * @return
	 */
	public boolean update(PtMenu menu){
		menu.set("update_at", DateUtil.getCurrentDate());
		
		return menu.update();
	}
	
	/***
	 * 更改菜单索引
	 * @param firstMenu
	 * @param lastMenu
	 * @return
	 */
	public boolean changeIndex(PtMenu firstMenu , PtMenu lastMenu){
		
		return true;
	}
	
	/***
	 * 更改菜单索引
	 * @param id 菜单id
	 * @return
	 */
	public boolean delete(Long id){
		return dao.deleteById(id);
	}
}
