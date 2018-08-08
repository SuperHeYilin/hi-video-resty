package cn.diffpi.resource.platform.permission.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.diffpi.kit.StrKit;
import cn.diffpi.resource.BaseModel;
import cn.diffpi.resource.platform.role.model.PtRole;
import cn.diffpi.resource.platform.role.model.PtUserRole;
import cn.dreampie.orm.annotation.Table;

@Table(name = "pt_permission", cached = true)
public class PtPermission extends BaseModel<PtPermission> {
    public static final PtPermission dao = new PtPermission();

    /**
     * 判断是否需要验证
     *
     * @param url
     * @param methd
     * @return
     */
    public Boolean isVerify(String url, String methd, String Mname) {
        List<PtPermission> lp = PtPermission.dao.findAll();
        PtPermission ps1 = new PtPermission();
        for (PtPermission ps : lp) {
            String sb = StrKit.regExString(ps.get("url").toString(), url);
            if (url.contains("?")) {
                url = url.substring(0, url.indexOf("?"));
            }
            if (sb != null && methd.equals(ps.get("value")) && sb.length() == url.length()) {
                ps1 = ps;
                break;
            }
        }
        if (ps1 != null && ps1.get("isverify").equals("1")) {
            return true;
        }
        return false;
    }

    /**
     * 获取用户的数据权限
     *
     * @param uid   用户ID
     * @param url   请求URL
     * @param methd 请求类型
     * @return
     */
    public Boolean isAuth(Integer uid, String url, String methd, String Mname) {
        List<PtRole> pr = PtUserRole.dao.getUserByRole(uid);
        pr = pr == null ? new ArrayList<PtRole>() : pr;
        Set<PtPermission> sprp = new HashSet<PtPermission>();
        for (PtRole ptRole : pr) {
            List<PtPermission> listpp = PtPermission.dao.find("select * from pt_permission pp where pp.id in (select prp.permission_id from pt_role_permission prp where prp.role_id = ?)", ptRole.get("id"));
            sprp.addAll(listpp);
        }
        PtPermission ps1 = null;
        for (PtPermission ps : sprp) {
            String sb = StrKit.regExString(ps.get("url").toString(), url);
            if (url.contains("?")) {
                url = url.substring(0, url.indexOf("?"));
            }
            if (sb != null && methd.equals(ps.get("value")) && sb.length() == url.length()) {
                ps1 = ps;
                break;
            }
        }
        if (ps1 != null) {
            return true;
        }
        return getIsPerurl(url, methd, Mname);
    }

    public boolean getIsPerurl(String url, String val, String Mname) {
        PtPermission pp = PtPermission.dao.findFirstBy(" ? REGEXP url and value = ? and method = ?", url, val, Mname);
        if (pp != null && pp.get("isverify").equals("1")) {
            return true;
        }
        return false;
    }

    /**
     * 获取用户的数据权限
     *
     * @param uid   用户ID
     * @param url   请求URL
     * @param methd 请求类型
     * @return
     */
    public PtPermissionData getDataAuth(Integer uid, String url, String methd) {
        List<PtRole> pr = PtUserRole.dao.getUserByRole(uid);
        pr = pr == null ? new ArrayList<PtRole>() : pr;
        Set<PtPermission> sprp = new HashSet<PtPermission>();
        for (PtRole ptRole : pr) {
            List<PtPermission> listpp = PtPermission.dao.find("select * from pt_permission pp where pp.id in (select prp.permission_id from pt_role_permission prp where prp.role_id = ?)", ptRole.get("id"));
            sprp.addAll(listpp);
        }
        PtPermission ps1 = null;
        for (PtPermission ps : sprp) {
            String sb = StrKit.regExString(ps.get("url").toString(), url);
            if (url.contains("?")) {
                url = url.substring(0, url.indexOf("?"));
            }
            if (sb != null && methd.equals(ps.get("value")) && sb.length() == url.length()) {
                ps1 = ps;
            }
        }
        if (ps1 != null) {
            return PtPermissionData.dao.findFirst("select * from pt_role_permission_data where id = (select prp.data_id from pt_role_permission prp where prp.permission_id = ? LIMIT 1 OFFSET 0 )", ps1.get("id"));
        }
        return null;
    }
}
