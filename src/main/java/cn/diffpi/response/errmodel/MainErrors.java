package cn.diffpi.response.errmodel;

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
 */
public class MainErrors {

    protected static Logger logger = LoggerFactory.getLogger(MainErrors.class);

    private static final String ERROR_CODE_PREFIX = "ERROR_";
    private static final String ERROR_SOLUTION_SUBFIX = "_SOLUTION";

    public static MainError getError(MainErrorType mainErrorType, Locale locale, Object... params) {
        String errorMessage = getErrorMessage(ERROR_CODE_PREFIX + mainErrorType.value(), locale, params);
        String errorSolution = getErrorSolution(ERROR_CODE_PREFIX + mainErrorType.value() + ERROR_SOLUTION_SUBFIX, locale);
        return new SimpleMainError(mainErrorType.value(), errorMessage, errorSolution);
    }

    private static String getErrorMessage(String code, Locale locale, Object... params) {
        try {
            Assert.notNull(MessageSourceAccessorUtil.messageSourceAccessor, "请先设置错误解决方案的国际化资源");
            return MessageSourceAccessorUtil.messageSourceAccessor.getMessage(code, params, locale);
        } catch (NoSuchMessageException e) {
            logger.error("不存在对应的错误键：{}，请检查是否在i18n/err的错误资源", code);
            throw e;
        }
    }

    private static String getErrorSolution(String code, Locale locale) {
        try {
            Assert.notNull(MessageSourceAccessorUtil.messageSourceAccessor, "请先设置错误解决方案的国际化资源");
            return MessageSourceAccessorUtil.messageSourceAccessor.getMessage(code, new Object[]{}, locale);
        } catch (NoSuchMessageException e) {
            logger.error("不存在对应的错误键：{}，请检查是否在i18n/err的错误资源", code);
            throw e;
        }
    }


}

