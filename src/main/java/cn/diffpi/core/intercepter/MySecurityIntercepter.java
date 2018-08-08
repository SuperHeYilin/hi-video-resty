package cn.diffpi.core.intercepter;

import cn.diffpi.core.MyHttpRequest;
import cn.diffpi.security.SecurityManager;
import cn.diffpi.security.annotation.AuthSign;
import cn.diffpi.security.credential.CredentialType;
import cn.dreampie.common.http.HttpMessage;
import cn.dreampie.common.http.exception.HttpException;
import cn.dreampie.common.http.result.HttpStatus;
import cn.dreampie.route.core.RouteInvocation;
import cn.dreampie.route.interceptor.Interceptor;

/***
 * 权限拦截
 * Created by one__l on 2016年4月15日
 */
public class MySecurityIntercepter implements Interceptor {

    public void intercept(RouteInvocation ri) {
        MyHttpRequest myHttpRequest = (MyHttpRequest) ri.getRouteMatch().getRequest();
        SecurityManager securityManager = myHttpRequest.getSecurityManager();
        check(securityManager, ri);
//		System.out.println(ri.getMethod());
        ri.invoke();
    }

    /***
     * 检查权限
     * @param securityManager
     */
    @SuppressWarnings("unchecked")
    public void check(SecurityManager securityManager, RouteInvocation ri) {
        AuthSign authAnnotation = ri.getMethod().getAnnotation(AuthSign.class);

        if (authAnnotation == null) {
            authAnnotation = (AuthSign) ri.getResourceClass().getAnnotation(AuthSign.class);
            if (authAnnotation == null) {
                return;
            }
        }

        CredentialType credentialType = authAnnotation.valiLevel();

        if (credentialType.equals(CredentialType.IS_AUTH)) {
            if (!securityManager.isValiToken()) {
                throw new HttpException(HttpStatus.UNAUTHORIZED, "未登录");
            }
            return;
        }

        if (credentialType.equals(CredentialType.REQS_SIGN)) {
            if (!securityManager.isValiAuthParam()) {
                throw new HttpException(HttpStatus.FORBIDDEN, HttpMessage.FORBIDDEN, "请求验证参数不完整");
            }

            if (!securityManager.isValiAuth()) {//进行授权校验（用于开发者授权）
                throw new HttpException(HttpStatus.FORBIDDEN, HttpMessage.FORBIDDEN, "开发者ID并未授权");
            }

            if (!securityManager.isValiSign()) {
                throw new HttpException(HttpStatus.FORBIDDEN, HttpMessage.FORBIDDEN, "请求签名错误");
            }

//            if(!securityManager.isValiDecrypt()){
//                throw new HttpException(HttpStatus.FORBIDDEN,"请求签名错误");
//            }

            return;
        }
    }

}
