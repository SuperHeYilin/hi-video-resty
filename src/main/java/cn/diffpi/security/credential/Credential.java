package cn.diffpi.security.credential;

/***
 * 凭证基础类
 * Created by one__l on 2016年4月25日
 */
public class Credential {
	private MethodType methodType; //请求方式（restful）
	private String antPath; //资源路径
	private CredentialType type; //验证方式
	
	public Credential(){
		
	}
	
	public Credential(MethodType methodType, String antPath, CredentialType type) {
		super();
		this.methodType = methodType;
		this.antPath = antPath;
		this.type = type;
	}

	public MethodType getMethodType() {
		return methodType;
	}
	public void setMethodType(MethodType methodType) {
		this.methodType = methodType;
	}
	public String getAntPath() {
		return antPath;
	}
	public void setAntPath(String antPath) {
		this.antPath = antPath;
	}
	public CredentialType getType() {
		return type;
	}
	public void setType(CredentialType type) {
		this.type = type;
	}
	
	public enum MethodType {
		ALL,
		GET,
		POST,
		PUT,
		DELETE,
		PATCH,
	}
}
