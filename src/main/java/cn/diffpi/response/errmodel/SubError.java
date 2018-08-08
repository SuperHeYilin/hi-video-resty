package cn.diffpi.response.errmodel;

/**
 * <pre>
 * 功能说明：
 * </pre>
 */
public class SubError {

    private String code;

    private String message;

    private String autoMessage;

    public SubError() {
    }

    public SubError(String code, String message, String autoMessage) {
        this.code = code;
        this.message = message;
        this.autoMessage = autoMessage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAutoMessage() {
        return autoMessage;
    }

    public void setAutoMessage(String autoMessage) {
        this.autoMessage = autoMessage;
    }
}
