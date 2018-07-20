package cn.diffpi.response.succresp;

import cn.diffpi.response.succmodel.MainSuccess;

/**
 * <pre>
 * 功能说明：
 * </pre>
 * @version 1.0
 */
public class SuccessResponse{

    protected String code;

    protected Object data;

    protected String message;

    public SuccessResponse() {
    }

    public SuccessResponse(MainSuccess mainSuccess) {
        this.code = mainSuccess.getCode();
        this.data = mainSuccess.getData();
        this.message = mainSuccess.getMessage();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
}

