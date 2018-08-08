package cn.diffpi.resource.platform.user;

import cn.diffpi.core.kit.BaseSv;
import cn.diffpi.core.kit.SplitPage;
import cn.diffpi.kit.DateUtil;
import cn.diffpi.kit.StrKit;
import cn.diffpi.resource.ApiResource;
import cn.diffpi.resource.platform.user.model.PtUser;
import cn.diffpi.security.annotation.AuthSign;
import cn.diffpi.security.token.TokenManager;
import cn.dreampie.route.annotation.API;
import cn.dreampie.route.annotation.DELETE;
import cn.dreampie.route.annotation.GET;
import cn.dreampie.route.annotation.POST;
import cn.dreampie.route.annotation.PUT;

@API("/pt/users")
@AuthSign
public class PtUserResource extends ApiResource {

    @GET(isverify = false)
    public SplitPage users() {
        SplitPage page = getModel(SplitPage.class, true);

        StringBuffer sb = new StringBuffer();

        sb.append("select * from pt_user where 1 = 1");
        String name = getParam("name");
        if (StrKit.notBlank(name)) {
            sb.append(" and full_name like '%" + name + "%'");
        }

        BaseSv.me.splitPageBaseSql(page, "", sb.toString());
        return page;
    }

    /***
     * 得到用户信息
     * @return
     */
    @GET("/:id")
    public PtUser getPtUser(Object id) {
        return PtUser.dao.unCache().findById(id).remove("password");
    }

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    @GET("/present")
    public PtUser getPresent() {
        return getPtUser(getUserId());
    }

    /***
     * 保存用户信息
     * @param user
     * @return
     */
    @POST
    public boolean savePtUser(PtUser user) {
        return PtUser.dao.save(user);
    }

    /***
     * 删除用户信息
     * @return
     */
    @DELETE("/:id")
    public boolean delPtUser(Long id) {
        return PtUser.dao.deleteById(id);
    }

    /***
     * 删除用户信息
     * @return
     */
    @DELETE()
    public boolean delPtUser(String ids) {
        return PtUser.dao.deleteInIds((Object[]) ids.split(","));
    }

    /***
     * 修改用户密码
     * @return
     */
    @PUT("/pwd")
    public boolean upPtUserPwd(String username, String oldpwd, String newpwd) {

        PtUser user = PtUser.dao.upPwd(username, oldpwd, newpwd);

        TokenManager.removeByKey(String.valueOf(user.get("id")));//删除所有登录信息

        return true;
    }

    /***
     * 更新用户信息
     * @param user
     * @return
     */
    @PUT
    public PtUser upPtUser(PtUser user) {

        user
                .set("updated_at", DateUtil.getCurrentDate())
                .update();

        return getPtUser(user.<Long>get("id"));

    }
}
