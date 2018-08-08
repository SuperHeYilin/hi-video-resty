package cn.diffpi.security.token;

import java.util.HashMap;
import java.util.Map;

public class MultTokenCache {
    private Map<String, Map<String, AccessToken>> multTokenMap = new HashMap<String, Map<String, AccessToken>>();

    public void put(String key, AccessToken token) {
        if (multTokenMap.containsKey(key)) {
            multTokenMap.get(key).put(token.getToken(), token);
        } else {
            Map<String, AccessToken> map = new HashMap<String, AccessToken>();
            map.put(token.getToken(), token);
            multTokenMap.put(key, map);
        }
    }

    public void remove(AccessToken token) {
        if (token == null) return;
        for (Map.Entry<String, Map<String, AccessToken>> entry : multTokenMap.entrySet()) {
            if (entry.getValue().containsValue(token)) {
                entry.getValue().remove(token.getToken());
                break;
            }
        }
    }

    public void remove(String key) {
        if (multTokenMap.containsKey(key)) {
            multTokenMap.remove(key);
        }
    }

    public Map<String, AccessToken> get(String key) {
        if (multTokenMap.containsKey(key)) {
            return multTokenMap.get(key);
        }

        return null;
    }

}
