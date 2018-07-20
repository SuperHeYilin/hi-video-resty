package cn.diffpi.resource.session;

import java.util.HashMap;
import java.util.Map;

import cn.diffpi.resource.ApiResource;
import cn.diffpi.resource.platform.user.model.PtUser;
import cn.diffpi.security.token.AccessToken;
import cn.diffpi.security.token.TokenManager;
import cn.dreampie.common.util.Maper;
import cn.dreampie.route.annotation.API;
import cn.dreampie.route.annotation.POST;
import cn.dreampie.route.annotation.PUT;

/**
 * Created by one__l on 15-1-16.
 */
@API(value = "/sessions",name="登录管理")
public class SessionResource extends ApiResource {

	@POST(des="登陆",isverify=false)
	public Map<String, Object> login(String username, String password) {
		PtUser user = PtUser.dao.login(username, password);
		AccessToken token = null;
		if(user != null){
			Map<String, Object> extras = new HashMap<String, Object>();
			extras.put("userId", user.get("id"));
			
			token = TokenManager.craeteToken(extras, username);
			// TokenManager.putMultTokens(String.valueOf(user.get("id")), token);

			return Maper.of("userId" , user.get("id") , "token" , token,"userInfo",user);
		} else {
			return new HashMap<String, Object>();
		}
	}

	@PUT
	public boolean logout(String token){
		TokenManager.removeByToken(token);
		
		return true;
	} 
}
