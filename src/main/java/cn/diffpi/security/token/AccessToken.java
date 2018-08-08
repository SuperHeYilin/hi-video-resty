package cn.diffpi.security.token;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * 通用接口凭证
 *
 * @author luoxun
 */
public class AccessToken implements Serializable {
    private String tokenId;
    // 获取到的凭证
    private String token;
    // 凭证有效时间，单位：秒
    private long expiresIn;
    //凭证创建时间(时间戳)
    private long createtime;
    //外加字段
    private Map<String, Object> extras = new HashMap<String, Object>();

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(long createtime) {
        this.createtime = createtime;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public void putExtras(String key, Object value) {
        this.extras.put(key, value);
    }

    public void putExtras(Map<String, Object> extras) {
        this.extras.putAll(extras);
    }

    public void rmExtras(String key) {
        this.extras.remove(key);
    }

    public Object getExtra(String key) {
        return extras.containsKey(key) ? extras.get(key) : null;
    }

}
