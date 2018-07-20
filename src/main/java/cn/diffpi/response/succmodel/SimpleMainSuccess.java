package cn.diffpi.response.succmodel;

/***
 * 获取数据成功返回对象
 *  Created by one__l on 2016年4月24日
 */
public class SimpleMainSuccess implements MainSuccess{
	private String code;

	private Object data;
	
	private String message;
	
	public SimpleMainSuccess(String code , String message){
		this.code = code;
		this.data = new Object();
		this.message = message;
	}
	
	public SimpleMainSuccess(String code , Object data , String message){
		this.code = code;
		this.data = data;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public Object getData() {
		return data;
	}

	public String getMessage() {
		return message;
	}

}
