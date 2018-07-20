package cn.diffpi.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Locale;

import com.alibaba.fastjson.JSONObject;

import cn.diffpi.response.errmodel.MainError;
import cn.diffpi.response.errmodel.MainErrorType;
import cn.diffpi.response.errmodel.MainErrors;
import cn.diffpi.response.errmodel.SubError;
import cn.diffpi.response.errmodel.SubErrorType;
import cn.diffpi.response.errmodel.SubErrors;
import cn.diffpi.response.errresp.ErrorResponse;
import cn.diffpi.response.errresp.NotExistErrorResponse;
import cn.diffpi.response.errresp.RejectedServiceResponse;
import cn.diffpi.response.errresp.ServiceUnavailableErrorResponse;
import cn.diffpi.response.errresp.TimeoutErrorResponse;
import cn.dreampie.common.http.exception.HttpException;
import cn.dreampie.common.http.result.HttpStatus;

public class ErrModelException extends HttpException {
	private static final long serialVersionUID = 1L;
	private ErrorResponse errorResponse;
	private boolean isMethod;
	private SubErrorType subErrorType;
	private Locale locale;
	private Object[] params;
	private String autoMessage;

	public ErrModelException(HttpStatus status, ErrorResponse errorResponse , String message) {
		super(status , message);
		this.errorResponse = errorResponse;
	}
	
	public ErrModelException(SubErrorType subErrorType , Locale locale , HttpStatus status , String message , String autoMessage , Object... params) {
		super(status , message);
		this.subErrorType = subErrorType;
		this.locale = locale;
		this.params = params;
		this.isMethod = true;
		this.autoMessage = autoMessage;
	}
	
	public static ErrModelException errorRespByMethod(SubErrorType subErrorType , String autoMessage ,Locale locale, Object... params) {
		LinkedList<Object> list = new LinkedList<Object>(Arrays.asList(params));
		list.addFirst("method");
		String message = JSONObject.toJSONString(ErrModelException.errorResponse(subErrorType , locale , autoMessage , list.toArray()).getErrorResponse()).toString();
		
		return new ErrModelException(subErrorType, locale, HttpStatus.INTERNAL_SERVER_ERROR , message , autoMessage , params);
	}

	public static ErrModelException errorResponse(SubErrorType subErrorType , Locale locale , String autoMessage , Object... params) {
		MainError mainError = SubErrors.getMainError(subErrorType, locale , params);
		String subErrorCode = SubErrors.getSubErrorCode(subErrorType , params);

		SubError subError = SubErrors.getSubError(subErrorCode, subErrorType.value(), autoMessage , locale, params);
		ArrayList<SubError> subErrors = new ArrayList<SubError>();
		subErrors.add(subError);
		
		mainError.addSubError(subError);
		
		ErrorResponse response = new ErrorResponse(mainError);

		return build(response);
	}
	
	public static ErrModelException errorResponse(MainErrorType mainErrorType , Locale locale, Object... params) {
		MainError mainError = MainErrors.getError(mainErrorType, locale , params);
		
		ErrorResponse response = new ErrorResponse(mainError);

		return build(response);
	}
	
	public static ErrModelException errorResponse(ErrorResponse errorResponse) {
		ErrorResponse response = errorResponse;

		return build(response);
	}

	public static ErrModelException notExistError(String objectName, String queryFieldName, Object queryFieldValue, String autoMessage , Locale locale) {
		ErrorResponse response = new NotExistErrorResponse(objectName, queryFieldName, queryFieldValue, autoMessage , locale);

		return build(response);
	}

	public static ErrModelException businessServiceError() {

		return null;
	}

	public static ErrModelException Common(Boolean bool) {
		return null;
	}

	public static ErrModelException rejectedService(String method, String version, Locale locale) {
		ErrorResponse response = new RejectedServiceResponse(method, version, locale);

		return build(response);
	}

	public static ErrModelException serviceUnavailableError(String method, String autoMessage , Locale locale) {
		ErrorResponse response = new ServiceUnavailableErrorResponse(method, autoMessage , locale);

		return build(response);
	}

	public static ErrModelException serviceUnavailableError(String method, String autoMessage , Locale locale, Throwable throwable) {
		ErrorResponse response = new ServiceUnavailableErrorResponse(method, autoMessage , locale, throwable);

		return build(response);
	}
	
	public static ErrModelException timeoutError(String method, String autoMessage , Locale locale, int timeout) {
		ErrorResponse response = new TimeoutErrorResponse(method, autoMessage , locale , timeout);

		return build(response);
	}
	
	
	private static ErrModelException build(ErrorResponse response){
		return new ErrModelException(HttpStatus.INTERNAL_SERVER_ERROR, response , JSONObject.toJSONString(response).toString());
	}
	
	public ErrModelException build(String method){
		if(isMethod){
			LinkedList<Object> list = new LinkedList<Object>(Arrays.asList(params));
			list.addFirst(method);
			return ErrModelException.errorResponse(subErrorType , locale , autoMessage , list.toArray());
		}
		return this;
	}

	public ErrorResponse getErrorResponse() {
		return errorResponse;
	}

	public void setErrorResponse(ErrorResponse errorResponse) {
		this.errorResponse = errorResponse;
	}

}
