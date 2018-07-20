package cn.diffpi.security.token;

import cn.diffpi.kit.StringKit;
import cn.dreampie.cache.SimpleCache;
import com.alibaba.fastjson.JSONException;

import java.util.Map;
import java.util.UUID;

public class TokenManager {

	private static final TokenCache TOKEN_CACHE = new TokenCache();
	private static final MultTokenCache MULT_TOKEN_CACHE = new MultTokenCache();
	private static final String GROUP = "auth";
	private static final SimpleCache SIMPLE_CACHE = SimpleCache.instance();
	private static final int EXPIRES_IN = 60*60*24*10;//过期时间
	
	public static AccessToken craeteToken(Map<String, Object> extras , String... params){
		AccessToken accessToken = null;
		
		try {
			accessToken = new AccessToken();
			
			String msg = StringKit.getStr(params);
			
			accessToken.setTokenId(UUID.randomUUID().toString().replace("-", ""));
			accessToken.setToken(TokenProcessor.getInstance().generateToken(msg, true));
			accessToken.setExpiresIn(EXPIRES_IN);
			accessToken.setCreatetime(System.currentTimeMillis());
			accessToken.putExtras(extras);
			
			//TOKEN_CACHE.put(accessToken);
			//使用redis缓存
			SIMPLE_CACHE.add(GROUP,accessToken.getToken(),accessToken,EXPIRES_IN);
			
		} catch (JSONException e) {
			accessToken = null;
			// 获取token失败
		}
		
		return accessToken;
	}
	
	public static synchronized boolean validateToken(String token) {
		AccessToken accessToken = SIMPLE_CACHE.get(GROUP,token);
		if(accessToken != null){
			return true;
		}

		return false;
	}
	
	public static AccessToken getAccessToken(String token){
		//return TOKEN_CACHE.get(token);
		return SIMPLE_CACHE.get(GROUP,token);
	}
	
//	public static Map<String, AccessToken> getAccessTokens(String key){
//		return MULT_TOKEN_CACHE.get(key);
//	}

	/***
	 * 配置多点登录
	 * @param key
	 * @param token
	 */
	public static void putMultTokens(String key , AccessToken token){
		MULT_TOKEN_CACHE.put(key, token);
	}
	
	public static void removeByToken(String token){
		//TOKEN_CACHE.remove(token);
		SIMPLE_CACHE.remove(GROUP,token);
		//MULT_TOKEN_CACHE.remove(TOKEN_CACHE.get(token));
	}
	
	public static void removeByKey(String key){
		Map<String, AccessToken> tokens = MULT_TOKEN_CACHE.get(key);
		
		if(tokens == null || tokens.size() < 1){
			return;
		}
		for (Map.Entry<String, AccessToken> entry : tokens.entrySet()) {
			TOKEN_CACHE.remove(entry.getValue().getToken());
		}
		
		MULT_TOKEN_CACHE.remove(key);
	}
}
