package cn.diffpi.response.errresp;

import java.util.Locale;

import cn.diffpi.response.errmodel.MainError;
import cn.diffpi.response.errmodel.MainErrorType;
import cn.diffpi.response.errmodel.MainErrors;

/**
 * <pre>
 *   当服务平台资源耗尽（超过最大线程数且列队排满后）
 * </pre>
 */
public class RejectedServiceResponse extends ErrorResponse {

	public RejectedServiceResponse() {
	}

	public RejectedServiceResponse(String method, String version, Locale locale) {
		MainError mainError = MainErrors.getError(MainErrorType.FORBIDDEN_REQUEST, locale, method, version);
		setMainError(mainError);
	}
}

