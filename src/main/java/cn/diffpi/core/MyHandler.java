package cn.diffpi.core;

import cn.diffpi.security.SecurityManager;
import cn.dreampie.common.http.HttpRequest;
import cn.dreampie.common.http.HttpResponse;
import cn.dreampie.log.Logger;
import cn.dreampie.route.handler.Handler;

public class MyHandler extends Handler {
    private static final Logger logger = Logger.getLogger(MyHandler.class);

    @Override
    public void handle(HttpRequest request, HttpResponse response, boolean[] isHandled) {
        MyHttpRequest myHttpRequest = (MyHttpRequest) request;
        SecurityManager securityManager = myHttpRequest.getSecurityManager();

        Boolean bool = securityManager.isValiDecrypt();
        if(bool == true) {
            logger.warn("本请求已验证数据加密并填充到steam");
        } else {
            logger.warn("本请求并未验证到数据加密");
        }

        nextHandler.handle(request, response ,isHandled);
    }
}
