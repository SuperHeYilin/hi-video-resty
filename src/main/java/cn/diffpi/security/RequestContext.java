package cn.diffpi.security;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/***
 * request上下文类 Created by one__l on 2016年4月14日
 */
public interface RequestContext {

    /***
     * 得到访问标记
     * @return
     */
    String getAppToken();

    /***
     * 得到密钥
     * @return
     */
    String getSecret();

    /***
     * 得到验证参数
     * @return
     */
    Map<String, String> getAuthParam();

    String getQueryParam(String param);

    InputStream getOriginalStream() throws IOException;

    InputStream getDecryptStream();

    void setDecryptStream(InputStream inputStream);

    String getHttpMethod();

    String getContentType();

    String getUrl();
}
