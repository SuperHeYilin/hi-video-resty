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
public class NotExistErrorResponse extends ErrorResponse {

	public static final String ISV = "isv.";
	public static final String NOT_EXIST_INVALID = "-not-exist:invalid-";

	// 注意，这个不能删除，否则无法进行流化
	public NotExistErrorResponse() {
	}

	/**
	 * 对象不存在的错误对象。当根据<code>queryFieldName</code>查询<code>obj
	 * ectName</code>时，查不到记录，则返回该错误对象。
	 * 
	 * @param objectName
	 *            对象的名称
	 * @param queryFieldName
	 *            查询字段的名称
	 * @param locale
	 *            本地化对象
	 * @param params
	 *            错误信息的参数，如错误消息的值为:use '{0}'({1})can't find '{2}' object
	 *            ，则传入的参数为001时，错误消息格式化为： can't find user by 001
	 */
	public NotExistErrorResponse(String objectName, String queryFieldName, Object queryFieldValue, String autoMessage , Locale locale) {
		MainError mainError = SubErrors.getMainError(SubErrorType.ISV_NOT_EXIST, locale, objectName, queryFieldName);
		String subErrorCode = SubErrors.getSubErrorCode(SubErrorType.ISV_NOT_EXIST, objectName, queryFieldName);

		SubError subError = SubErrors.getSubError(subErrorCode, SubErrorType.ISV_NOT_EXIST.value(), autoMessage , locale, queryFieldName, queryFieldValue, objectName);
		ArrayList<SubError> subErrors = new ArrayList<SubError>();
		subErrors.add(subError);

		setMainError(mainError);
		setSubErrors(subErrors);
	}
}
