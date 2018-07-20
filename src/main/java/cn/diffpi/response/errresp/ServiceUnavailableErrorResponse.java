package cn.diffpi.response.errresp;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Locale;

import cn.diffpi.response.errmodel.MainError;
import cn.diffpi.response.errmodel.SubError;
import cn.diffpi.response.errmodel.SubErrorType;
import cn.diffpi.response.errmodel.SubErrors;

/**
 * <pre>
 * 功能说明：
 * </pre>
 */
public class ServiceUnavailableErrorResponse extends ErrorResponse {

	private static final String ISP = "isp.";

	private static final String SERVICE_UNAVAILABLE = "-service-unavailable";

	// 注意，这个不能删除，否则无法进行流化
	public ServiceUnavailableErrorResponse() {
	}

	public ServiceUnavailableErrorResponse(String method, String autoMessage ,  Locale locale) {
		MainError mainError = SubErrors.getMainError(SubErrorType.ISP_SERVICE_UNAVAILABLE, locale);
		String errorCodeKey = ISP + transform(method) + SERVICE_UNAVAILABLE;
		SubError subError = SubErrors.getSubError(errorCodeKey, SubErrorType.ISP_SERVICE_UNAVAILABLE.value(), autoMessage , locale, method, "NONE", "NONE");
		ArrayList<SubError> subErrors = new ArrayList<SubError>();
		subErrors.add(subError);

		setMainError(mainError);
		setSubErrors(subErrors);
	}

	public ServiceUnavailableErrorResponse(String method, String autoMessage , Locale locale, Throwable throwable) {
		MainError mainError = SubErrors.getMainError(SubErrorType.ISP_SERVICE_UNAVAILABLE, locale);

		ArrayList<SubError> subErrors = new ArrayList<SubError>();

		String errorCodeKey = ISP + transform(method) + SERVICE_UNAVAILABLE;
		Throwable srcThrowable = throwable;
		if (throwable.getCause() != null) {
			srcThrowable = throwable.getCause();
		}
		SubError subError = SubErrors.getSubError(errorCodeKey, SubErrorType.ISP_SERVICE_UNAVAILABLE.value() , autoMessage , locale, method, srcThrowable.getClass().getName(), getThrowableInfo(throwable));
		subErrors.add(subError);

		setSubErrors(subErrors);
		setMainError(mainError);
	}

	private String getThrowableInfo(Throwable throwable) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
		PrintStream printStream = new PrintStream(outputStream);
		throwable.printStackTrace(printStream);
		return outputStream.toString();
	}
}
