package cn.diffpi.response.errresp;

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
public class TimeoutErrorResponse extends ErrorResponse {

	private static final String ISP = "isp.";

	private static final String SERVICE_TIMEOUT = "-service-timeout";

	public TimeoutErrorResponse() {
	}

	public TimeoutErrorResponse(String method, String autoMessage , Locale locale, int timeout) {
		MainError mainError = SubErrors.getMainError(SubErrorType.ISP_SERVICE_TIMEOUT, locale);

		ArrayList<SubError> subErrors = new ArrayList<SubError>();

		String errorCodeKey = ISP + transform(method) + SERVICE_TIMEOUT;
		SubError subError = SubErrors.getSubError(errorCodeKey, SubErrorType.ISP_SERVICE_TIMEOUT.value(), autoMessage , locale, method, timeout);
		subErrors.add(subError);

		setSubErrors(subErrors);
		setMainError(mainError);
	}

}
