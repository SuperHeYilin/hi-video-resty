package cn.diffpi.response.errresp;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.diffpi.response.errmodel.MainError;
import cn.diffpi.response.errmodel.MainErrorType;
import cn.diffpi.response.errmodel.MainErrors;
import cn.diffpi.response.errmodel.SubError;

/**
 * <pre>
 * 功能说明：
 * </pre>
 * @version 1.0
 */
public class ErrorResponse{

    protected String errorToken = "";//CommonConstant.ERROR_TOKEN;

    protected String code;

    protected String message;

    protected String solution;

    protected List<SubError> subErrors;

    public ErrorResponse() {
    }

    public ErrorResponse(MainError mainError) {
        this.code = mainError.getCode();
        this.message = mainError.getMessage();
        this.solution = mainError.getSolution();
        if (mainError.getSubErrors() != null && mainError.getSubErrors().size() > 0) {
            this.subErrors = mainError.getSubErrors();
        }
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

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public List<SubError> getSubErrors() {
        return subErrors;
    }

    public void setSubErrors(List<SubError> subErrors) {
        this.subErrors = subErrors;
    }

    public void addSubError(SubError subError){
        if (subErrors == null) {
            subErrors = new ArrayList<SubError>();
        }
        subErrors.add(subError);
    }

    protected MainError getInvalidArgumentsError(Locale locale) {
        return MainErrors.getError(MainErrorType.INVALID_ARGUMENTS, locale);
    }

    protected void setMainError(MainError mainError) {
        setCode(mainError.getCode());
        setMessage(mainError.getMessage());
        setSolution(mainError.getSolution());
    }

    public String getErrorToken() {
        return errorToken;
    }

    /**
     * 对服务名进行标准化处理：如book.upload转换为book-upload，
     *
     * @param method
     * @return
     */
    protected String transform(String method) {
        if(method != null){
            method = method.replace(".", "-");
            return method;
        }else{
            return "LACK_METHOD";
        }
    }

}

