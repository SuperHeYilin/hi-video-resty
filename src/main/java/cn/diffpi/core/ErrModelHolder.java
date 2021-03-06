package cn.diffpi.core;

import cn.diffpi.response.ErrModelException;
import cn.dreampie.common.Constant;
import cn.dreampie.common.Render;
import cn.dreampie.common.http.HttpRequest;
import cn.dreampie.common.http.HttpResponse;
import cn.dreampie.common.http.exception.HttpException;
import cn.dreampie.common.http.result.HttpStatus;
import cn.dreampie.common.util.json.Jsoner;
import cn.dreampie.log.Logger;
import cn.dreampie.route.holder.DefaultExceptionHolder;
import cn.dreampie.route.holder.ExceptionHolder;
import cn.dreampie.route.render.RenderFactory;

/**
 * Created by one__l on 2016年3月18日
 */
public class ErrModelHolder extends ExceptionHolder {

    private final static Logger logger = Logger.getLogger(DefaultExceptionHolder.class);

    public void hold(HttpRequest request, HttpResponse response, Exception exception, boolean[] isHandled) {
        String restPath = request.getRestPath();
        Render render = RenderFactory.getByUrl(restPath);
        String message;

        if (exception instanceof ErrModelException) {
            ErrModelException errModelException = ((ErrModelException) exception).build(request.getRestPath());

            response.setStatus(errModelException.getStatus());
            render.render(request, response, errModelException.getErrorResponse());

            return;
        }

        if (exception instanceof HttpException) {
            HttpException webException = (HttpException) exception;
            //api访问 所有的异常 以httpStatus返回
            if (Constant.apiPrefix == null || restPath.startsWith(Constant.apiPrefix)) {
                message = Jsoner.toJSON(webException.getContent());
                if (logger.isWarnEnabled()) {
                    logger.warn("Request \"" + request.getHttpMethod() + " " + request.getRestPath() + "\" error : " + webException.getStatus().getCode() + " " + message);
                }
                response.setStatus(webException.getStatus());
                render.render(request, response, message);
            } else {
                //其他访问  跳转到 指定页面
                go(response, webException.getStatus(), isHandled);
            }
        } else {
            message = exception.getMessage();
            if (message == null) {
                Throwable throwable = exception.getCause();
                if (throwable != null) {
                    message = throwable.getMessage();
                }
            }
            if (logger.isErrorEnabled()) {
                logger.warn("Request \"" + request.getHttpMethod() + " " + request.getRestPath() + "\" error : " + HttpStatus.BAD_REQUEST.getCode() + " " + message, exception);
            }
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            render.render(request, response, message);
        }
    }
}
