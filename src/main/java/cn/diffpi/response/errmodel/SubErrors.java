package cn.diffpi.response.errmodel;

import java.util.EnumMap;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.NoSuchMessageException;
import org.springframework.util.Assert;

import cn.diffpi.i18n.MessageSourceAccessorUtil;

/**
 * <pre>
 * 功能说明：
 * </pre>
 * 
 * @version 1.0
 */
public class SubErrors {

	protected static Logger logger = LoggerFactory.getLogger(SubErrors.class);

	// 子错误和主错误对应Map,key为子错误代码，值为主错误代码
	private static final EnumMap<SubErrorType, MainErrorType> SUBERROR_MAINERROR_MAPPINGS = new EnumMap<SubErrorType, MainErrorType>(SubErrorType.class);

	static {
		SUBERROR_MAINERROR_MAPPINGS.put(SubErrorType.ISP_SERVICE_UNAVAILABLE, MainErrorType.SERVICE_CURRENTLY_UNAVAILABLE);
		SUBERROR_MAINERROR_MAPPINGS.put(SubErrorType.ISP_SERVICE_TIMEOUT, MainErrorType.SERVICE_CURRENTLY_UNAVAILABLE);
		SUBERROR_MAINERROR_MAPPINGS.put(SubErrorType.ISV_MISSING_PARAMETER, MainErrorType.MISSING_REQUIRED_ARGUMENTS);
		SUBERROR_MAINERROR_MAPPINGS.put(SubErrorType.ISV_PARAMETERS_MISMATCH, MainErrorType.INVALID_ARGUMENTS);
		SUBERROR_MAINERROR_MAPPINGS.put(SubErrorType.ISV_INVALID_PARAMETE, MainErrorType.INVALID_ARGUMENTS);
		SUBERROR_MAINERROR_MAPPINGS.put(SubErrorType.ISV_NOT_EXIST, MainErrorType.INVALID_ARGUMENTS);
		SUBERROR_MAINERROR_MAPPINGS.put(SubErrorType.ISV_INVALID_PERMISSION, MainErrorType.INSUFFICIENT_ISV_PERMISSIONS);
	}

	private static final String PARAM_1 = "xxx";
	private static final String PARAM_2 = "yyy";

	/**
	 * 获取对应子错误的主错误
	 * 
	 * @param subErrorType
	 * @param locale
	 * @return
	 */
	public static MainError getMainError(SubErrorType subErrorType, Locale locale, Object... params) {
		return MainErrors.getError(SUBERROR_MAINERROR_MAPPINGS.get(subErrorType), locale, params);
	}

	/**
	 * @param subErrorCode
	 *            子错误代码
	 * @param subErrorKey
	 *            子错误信息键
	 * @param locale
	 *            本地化
	 * @param params
	 *            本地化消息参数
	 * @return
	 */
	public static SubError getSubError(String subErrorCode, String subErrorKey, String autoMessage , Locale locale, Object... params) {
		try {
			Assert.notNull(MessageSourceAccessorUtil.messageSourceAccessor, "请先设置错误消息的国际化资源");
			String parsedSubErrorMessage = MessageSourceAccessorUtil.messageSourceAccessor.getMessage(subErrorKey, params, locale);
			return new SubError(subErrorCode, parsedSubErrorMessage , autoMessage);
		} catch (NoSuchMessageException e) {
			logger.error("不存在对应的错误键：{}，请检查是否正确配置了应用的错误资源，" + "默认位置：i18n/err", subErrorCode);
			throw e;
		}
	}
	

	public static String getSubErrorCode(SubErrorType subErrorType, Object... params) {
		String subErrorCode = subErrorType.value();
		if (params.length > 0) {
			if (params.length == 1) {
				subErrorCode = subErrorCode.replace(PARAM_1, (String) params[0]);
			} else {
				subErrorCode = subErrorCode.replace(PARAM_1, (String) params[0]);
				if (params[1] != null) {
					subErrorCode = subErrorCode.replace(PARAM_2, (String) params[1]);
				}
			}
		}
		return subErrorCode;
	}
}
