package cn.diffpi.core;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import cn.diffpi.resource.platform.permission.model.PtPermission;
import cn.diffpi.resource.platform.permission.model.PtPermissionData;
import cn.diffpi.security.RequestContext;
import cn.diffpi.security.SecurityManager;
import cn.diffpi.security.aes.AesTool;
import cn.diffpi.security.aes.Signature;
import cn.diffpi.security.token.AccessToken;
import cn.diffpi.security.token.TokenManager;
import cn.dreampie.log.Logger;

import com.alibaba.druid.util.StringUtils;

/**
 * Created by one__l on 2016年4月15日
 */
public class MySecurityManager implements SecurityManager {
	private static final Logger log = Logger.getLogger(MySecurityManager.class);

	private RequestContext context;

	private final AesTool aes = new AesTool();
	private final Signature signatureUtil = new Signature();

	public MySecurityManager(RequestContext context) {
		this.context = context;
	}

	public boolean isValiSign() {
		/*if(!isValiTokenTimeout()){
			return false;
		}*/
		
		String signature = context.getAuthParam().get("sign");
		String appid = context.getAuthParam().get("appid");
		String timestamp = context.getAuthParam().get("timestamp");
		String lol = context.getAuthParam().get("lol");
		String token = "cbt_kuaigou_token";
		long millis = Long.valueOf(timestamp);

		if (signatureUtil.isValid(signature, appid, lol, millis, token)) {
			return true;
		}

		return false;
	}
	
	public boolean isValiDecrypt(){
		if (!this.isValiSign()) {
			return false;
		} else {
			if (context.getHttpMethod().equals("GET") || context.getHttpMethod().equals("DELETE")) {

			} else {
				try {
					if(context.getOriginalStream() == null){
						return true;
					}
					
					String encryptReqStr = getStrToStream(context.getOriginalStream());
					String digest = signatureUtil.digest(encryptReqStr, "MD5");

					if (StringUtils.equals(digest, context.getAuthParam().get("lol"))) {
						String secret = context.getSecret();

						String decryptReqStr = aes.decrypt(encryptReqStr, secret);

						log.info(decryptReqStr);

						byte[] bytes = decryptReqStr.getBytes();
						InputStream bais = new ByteArrayInputStream(bytes);

						context.setDecryptStream(bais);
						
						return true;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return false;
	}

	@Override
	public boolean buildDecrypt() {
		try {
			if(context.getOriginalStream() == null){
				return true;
			}

			String encryptReqStr = getStrToStream(context.getOriginalStream());
			String digest = signatureUtil.digest(encryptReqStr, "MD5");

			if (StringUtils.equals(digest, context.getAuthParam().get("lol"))) {
				String secret = context.getSecret();

				String decryptReqStr = aes.decrypt(encryptReqStr, secret);

				log.info(decryptReqStr);

				byte[] bytes = decryptReqStr.getBytes();
				InputStream bais = new ByteArrayInputStream(bytes);

				context.setDecryptStream(bais);

				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean isValiAuthTimeout() {
		if(!isValiAuth()){
			return false;
		}
		
		return true;
	}
	
	public boolean isValiTokenTimeout() {
		if(!isValiToken()){
			return false;
		}
		
		return true;
	}

	public boolean isValiToken() {
		/*if(!isValiAuthParam()){
			return false;
		}*/
		if(context.getAppToken() != null){
			if(TokenManager.validateToken(context.getAppToken())){
				return TokenManager.getAccessToken(context.getAppToken()) != null ? true : false;
			} else {
				return false;
			}
		}
		
		return false;
	}
	
	public boolean isValiAuth() {
		String appid = context.getAuthParam().get("appid");
		if(appid.equals("cbt_kuaigou_appid")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isValiAuthParam(){
		Map<String, String> map = context.getAuthParam();
		
		for (Map.Entry<String, String> obj : map.entrySet()) {
			if(StringUtils.isEmpty(obj.getValue())){
				return false;
			}
		}
		
		return true;
	}

	/***
	 * 得到流字符串 适用于客户端以流的形式传递的数据
	 * 
	 * @return
	 */
	private String getStrToStream(InputStream is) {
		try {
			BufferedInputStream br = new BufferedInputStream(is);

			StringBuffer sb = new StringBuffer();
			byte[] buffer = new byte[1024];
			int iRead;
			while ((iRead = br.read(buffer)) != -1) {
				sb.append(new String(buffer, 0, iRead, "UTF-8"));
			}

			String str = sb.toString();

			return str;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public PtPermissionData getNowRouteData() {
		AccessToken at = TokenManager.getAccessToken(context.getAppToken());
		if(at != null){
			Integer userId = Integer.valueOf(at.getExtra("userId").toString());
			return PtPermission.dao.getDataAuth(userId,context.getUrl(),context.getHttpMethod());
		}
		return null;
	}

	@Override
	public Boolean isAuth(String Mname){
		AccessToken at = TokenManager.getAccessToken(context.getAppToken());
		if(at != null){
			Integer userId = Integer.valueOf(at.getExtra("userId").toString());
			return PtPermission.dao.isAuth(userId,context.getUrl(),context.getHttpMethod(),Mname);
		}else{
			return PtPermission.dao.isVerify(context.getUrl(),context.getHttpMethod(),Mname);
		}
	}
}
