package cn.diffpi.security.token;

import java.util.HashMap;
import java.util.Map;

public class TokenCache {
	private Map<String , AccessToken> tokenMap = new HashMap<String, AccessToken>();
	
    public void put(AccessToken token){
    	tokenMap.put(token.getToken(), token);
    }
	
	public void remove(String token){
		tokenMap.remove(token);
	}
	
	public boolean contains(AccessToken token){
		return tokenMap.containsKey(token.getTokenId());
	}
	
	public Map<String , AccessToken> getAll(){
		return tokenMap;
	}
	
	public AccessToken get(String token){
		return tokenMap.get(token);
	}
}
