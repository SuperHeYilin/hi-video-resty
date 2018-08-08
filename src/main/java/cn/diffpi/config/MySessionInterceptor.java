package cn.diffpi.config;

import cn.dreampie.common.http.HttpRequest;
import cn.dreampie.common.http.HttpResponse;
import cn.dreampie.route.core.RouteInvocation;
import cn.dreampie.route.interceptor.Interceptor;
import cn.dreampie.security.AuthenticateService;
import cn.dreampie.security.Session;
import cn.dreampie.security.SessionBuilder;
import cn.dreampie.security.Subject;
import cn.dreampie.security.builder.BothSessionBuilder;

public class MySessionInterceptor implements Interceptor {

    private final SessionBuilder sessionBuilder;

    public MySessionInterceptor(AuthenticateService authenticateService) {
        this.sessionBuilder = new BothSessionBuilder(authenticateService);
    }

    public MySessionInterceptor(SessionBuilder sessionBuilder) {
        this.sessionBuilder = sessionBuilder;
    }

    public void intercept(RouteInvocation ri) {
        HttpRequest request = ri.getRouteMatch().getRequest();
        HttpResponse response = ri.getRouteMatch().getResponse();

        if (request.getRestPath().equals("/api/b/v1.0/sessions")) {
            //从cookie/header 构建session
            Session oldSession = sessionBuilder.in(request, response);
            ri.invoke();
            //把session  写入cookie/header
            sessionBuilder.out(oldSession, response);
            return;
        }

        //从cookie/header 构建session
        Session oldSession = sessionBuilder.in(request, response);
        //检测权限
        Subject.check(request.getHttpMethod(), request.getRestPath());
        //执行resource
        ri.invoke();
        //把session  写入cookie/header
        sessionBuilder.out(oldSession, response);
    }

}
