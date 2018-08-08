package cn.diffpi.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import cn.diffpi.security.RequestContext;
import cn.diffpi.security.SecurityManager;
import cn.diffpi.security.aes.AesTool;

/**
 * Created by one__l on 2016年4月18日
 */
public class MyRequestContext implements RequestContext {

    private MyHttpRequest request;
    private InputStream inputStream;
    private SecurityManager securityManager;

    public MyRequestContext(MyHttpRequest httpRequest) {
        this.request = httpRequest;

        securityManager = new MySecurityManager(this);
        request.setContext(this);
        request.setSecurityManager(securityManager);
    }

    public String getAppToken() {
        String token = request.getHeader("Authorization");
        if (token == null) {
            token = request.getQueryParam("token");
        }
        return token;
    }

    public String getSecret() {
        String key = "123456789012345678901234567890~!";
        return key;
    }

    public Map<String, String> getAuthParam() {
        String signature = getQueryParam("sign");
        String appid = getQueryParam("appid");
        String timestamp = getQueryParam("timestamp");
        String lol = getQueryParam("lol");

        Map<String, String> authParam = new HashMap<String, String>();
        authParam.put("sign", signature);
        authParam.put("appid", appid);
        authParam.put("timestamp", timestamp);
        authParam.put("lol", lol);

        return authParam;
    }

    public String getQueryParam(String param) {
        return request.getQueryParam(param);
    }

    public String getHeader(String name) {
        return request.getHeader(name);
    }

    public InputStream getOriginalStream() throws IOException {
        return request.getContentStream();
    }

    public String getHttpMethod() {
        return request.getHttpMethod();
    }

    public String getContentType() {
        return request.getContentType();
    }

    public InputStream getDecryptStream() {
        return this.inputStream;
    }

    public void setDecryptStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getUrl() {
        return request.getRestUri();
    }

}
