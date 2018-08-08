package cn.diffpi.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.diffpi.resource.ApiResource;
import cn.diffpi.resource.platform.permission.model.PtPermission;
import cn.dreampie.common.util.scan.ClassScaner;
import cn.dreampie.route.annotation.API;
import cn.dreampie.route.annotation.DELETE;
import cn.dreampie.route.annotation.GET;
import cn.dreampie.route.annotation.PATCH;
import cn.dreampie.route.annotation.POST;
import cn.dreampie.route.annotation.PUT;
import cn.dreampie.route.core.Resource;
import cn.dreampie.route.core.Route;


public class PerUrlManager {
    public static PerUrlManager me = new PerUrlManager();
    public static final String PARAM_PATTERN = "([^\\/]+)";
    private Set<Class<? extends ApiResource>> includeResources = new HashSet<Class<? extends ApiResource>>();

    /**
     * @throws
     * @Title: pass
     * @Description: TODO(扫描所有请求更新权限URL)
     * @author FlyLB
     */
    @SuppressWarnings({"unchecked", "unused"})
    public void pass() {
        Set<Route> routesSet;
        String apipath = "";
        includeResources = ClassScaner.of(ApiResource.class)
                .include("cn.diffpi.resource")
                .scanToClass();
        List<PtPermission> newest = new ArrayList<PtPermission>();
        for (Class<? extends ApiResource> class1 : includeResources) {
            apipath = getApi(class1);

            API api = class1.getAnnotation(API.class);

            if (api != null) {
                String apiurl = getApi(class1);
                PtPermission per = ParentSaveOrUpdate(api.name(), apiurl);
                newest.add(per);
                long pid = per.get("id");
                Method[] methods = class1.getMethods();

                for (Method method : methods) {
                    GET g = method.getAnnotation(GET.class);

                    if (g != null) {
                        newest.add(ParentSaveOrUpdate(api.name(),
                                getApi(apipath, g.value()), method.getName(),
                                "GET", g.des(), pid, g.isverify()));

                        continue;
                    }

                    POST post = method.getAnnotation(POST.class);

                    if (post != null) {
                        newest.add(ParentSaveOrUpdate(api.name(),
                                getApi(apipath, post.value()), method.getName(),
                                "POST", post.des(), pid, post.isverify()));

                        continue;
                    }

                    PUT put = method.getAnnotation(PUT.class);

                    if (put != null) {
                        newest.add(ParentSaveOrUpdate(api.name(),
                                getApi(apipath, put.value()), method.getName(),
                                "PUT", put.des(), pid, put.isverify()));

                        continue;
                    }

                    DELETE del = method.getAnnotation(DELETE.class);

                    if (del != null) {
                        newest.add(ParentSaveOrUpdate(api.name(),
                                getApi(apipath, del.value()), method.getName(),
                                "DELETE", del.des(), pid, del.isverify()));

                        continue;
                    }

                    PATCH patch = method.getAnnotation(PATCH.class);

                    if (patch != null) {
                        newest.add(ParentSaveOrUpdate(api.name(),
                                getApi(apipath, patch.value()), method.getName(),
                                "PATCH", patch.des(), pid, patch.isverify()));

                        continue;
                    }
                }
            }
        }
        flushDatas(newest);
    }

    /**
     * @return
     * @throws
     * @Title: ParentSaveOrUpdate
     * @Description: TODO(新增或更新子权限)
     * @author FlyLB
     */
    private PtPermission ParentSaveOrUpdate(String name, String url,
                                            String method, String metype, String info, long pid, Boolean isverify) {
        if (url.contains(":")) {
            url = url.replaceAll("(:[^\\/]+)", PARAM_PATTERN);
        }

        PtPermission p = null;
        PtPermission pp = PtPermission.dao.findFirstBy(" url = ? and  value = ? and pid = ? ",
                url, metype, pid);
        p = (pp != null) ? pp : new PtPermission();
        p.set("name", name).set("url", url).set("method", method)
                .set("value", metype).set("intro", info).set("pid", pid)
                .set("isverify", isverify ? 0 : 1);

        boolean b = (pp != null) ? p.update() : p.save();
        return b ? p : null;
    }

    /**
     * @param name
     * @param url
     * @return
     * @throws
     * @Title: ParentSaveOrUpdate
     * @Description: TODO(新增或更新父权限)
     * @author FlyLB
     */
    private PtPermission ParentSaveOrUpdate(String name, String url) {
        PtPermission p = null;
        PtPermission pp = PtPermission.dao.findFirstBy(" url = ? and pid = 0 ",
                url);
        p = (pp != null) ? pp : new PtPermission();

        p.set("name", name).set("url", url).set("isverify", 1);

        boolean b = (pp != null) ? p.update() : p.save();
        return b ? p : null;
    }

    /**
     * @param lpp 最新的权限集合
     * @throws
     * @Title: flushDatas
     * @Description: TODO(根据最新的权限跟数据库的权限对比把不存在的权限删除)
     * @author FlyLB
     */
    private void flushDatas(List<PtPermission> lpp) {
        List<PtPermission> lper = PtPermission.dao.findAll();
        for (PtPermission per : lper) {
            int num = 0;
            for (PtPermission pp : lpp) {
                if (per.get("id").equals(pp.get("id"))) {
                    num = 1;
                    break;
                } else {
                    num = 0;
                }
            }
            if (num == 0) {
                per.delete();
            }
        }
    }

    /**
     * 获取api部分
     *
     * @param resourceClazz resource class
     * @return url apiPath
     */
    @SuppressWarnings("unchecked")
    private String getApi(Class<? extends Resource> resourceClazz) {
        API api;
        String apiPath = "";
        api = resourceClazz.getAnnotation(API.class);

        if (api != null) {
            apiPath = api.value();

            if (!apiPath.equals("")) {
                if (!apiPath.startsWith("/")) {
                    apiPath = "/" + apiPath;
                }
            }
        }

        Class<?> superClazz = resourceClazz.getSuperclass();

        if (Resource.class.isAssignableFrom(superClazz)) {
            apiPath = getApi((Class<? extends Resource>) superClazz) + apiPath;
        }

        return apiPath;
    }

    /**
     * 最终生成的apiPath
     *
     * @param apiPath
     * @param methodPath
     * @return
     */
    private String getApi(String apiPath, String methodPath) {
        if (!methodPath.equals("")) {
            if (!methodPath.startsWith("/")) {
                apiPath = apiPath + "/" + methodPath;
            } else {
                apiPath = apiPath + methodPath;
            }
        }

        return apiPath;
    }
}