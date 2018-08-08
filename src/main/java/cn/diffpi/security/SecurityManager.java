package cn.diffpi.security;

import cn.diffpi.resource.platform.permission.model.PtPermissionData;


/**
 * Created by one__l on 2016年4月15日
 */
public interface SecurityManager {

    /***
     * 对请求服务的参数进行验证
     * @return
     */
    boolean isValiAuthParam();

    /**
     * 对请求服务的上下文进行授权校验
     *
     * @return
     */
    boolean isValiAuth();

    /**
     * 对请求服务的上下文进行授权是否过期
     *
     * @return
     */
    boolean isValiAuthTimeout();

    /***
     * 对请求服务的token是否正确
     * @return
     */
    boolean isValiToken();

    /***
     * 对请求服务的token检查是否过期
     * @return
     */
    boolean isValiTokenTimeout();

    /***
     * 对请求服务的签名进行验证
     * @return
     */
    boolean isValiSign();

    /***
     * 对请求服务的加密字符进行解密验证
     * @return
     */
    boolean isValiDecrypt();

    /***
     * 对请求数据进行解密
     * @return
     */
    boolean buildDecrypt();

    /***
     * 获取当前用户请求的数据权限
     * @return
     */
    public PtPermissionData getNowRouteData();

    /***
     * 判断权限
     * @return
     */
    public Boolean isAuth(String Mname);
}	
