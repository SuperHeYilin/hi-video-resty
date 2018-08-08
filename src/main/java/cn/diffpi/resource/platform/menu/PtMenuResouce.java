package cn.diffpi.resource.platform.menu;

import java.util.List;

import cn.diffpi.resource.ApiResource;
import cn.diffpi.resource.platform.menu.model.PtMenu;
import cn.diffpi.security.annotation.AuthSign;
import cn.dreampie.orm.Record;
import cn.dreampie.route.annotation.API;
import cn.dreampie.route.annotation.DELETE;
import cn.dreampie.route.annotation.GET;
import cn.dreampie.route.annotation.POST;
import cn.dreampie.route.annotation.PUT;

/***
 * 菜单管理控制类
 *  Created by one__l on 2016年8月18日
 */
@API("/pt/menus")
@AuthSign
public class PtMenuResouce extends ApiResource {

    /***
     * 获取用户所有菜单
     * @return
     */
    @GET
    public List<Record> menusAll() {
        return PtMenu.dao.menusAll();
    }

    /***
     * 获取用户所有菜单
     * @return
     */
    @GET(value = "/user", isverify = false)
    public List<Record> userMenusAll() {
        return PtMenu.dao.userMenusAll(getUserId());
    }

    /***
     * 添加菜单按钮
     * @param menu 菜单
     */
    @POST
    public PtMenu saveMenu(PtMenu menu) {
        PtMenu.dao.save(menu);

        return menu;
    }

    /***
     * 添加菜单按钮
     * @param menu 菜单
     */
    @PUT
    public boolean upMenu(PtMenu menu) {
        return PtMenu.dao.update(menu);
    }

    /***
     * 删除菜单按钮
     * @param menu 菜单
     */
    @DELETE("/:id")
    public boolean delMenu(Long id) {
        return PtMenu.dao.delete(id);
    }
}
