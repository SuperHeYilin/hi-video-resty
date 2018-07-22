package cn.diffpi.kit.video;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author super
 * @description:
 * @date 2018/3/21 15:53
 */
public class CorsFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(CorsFilter.class);


	private String allowOrigin;
	private String allowMethods;
	private String allowCredentials;
	private String allowHeaders;
	private String exposeHeaders;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		allowOrigin = filterConfig.getInitParameter("allowOrigin");
		allowMethods = filterConfig.getInitParameter("allowMethods");
		allowCredentials = filterConfig.getInitParameter("allowCredentials");
		allowHeaders = filterConfig.getInitParameter("allowHeaders");
		exposeHeaders = filterConfig.getInitParameter("exposeHeaders");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		String currentOrigin = request.getHeader("Origin");
		response.setHeader("Access-Control-Allow-Origin", currentOrigin);

        /*
				 * Access-Control-Allow-Origin：允许访问的客户端域名，例如：http://web.xxx.com，若为*，则表示从任意域都能访问，即不做任何限制。
         * Access-Control-Allow-Methods：允许访问的方法名，多个方法名用逗号分割，例如：GET,POST,PUT,DELETE,OPTIONS。
         * Access-Control-Allow-Credentials：是否允许请求带有验证信息，若要获取客户端域下的cookie时，需要将其设置为true。
         * Access-Control-Allow-Headers：允许服务端访问的客户端请求头，多个请求头用逗号分割，例如：Content-Type。
         * Access-Control-Expose-Headers：允许客户端访问的服务端响应头，多个响应头用逗号分割。
         */
		response.setHeader("Access-Control-Allow-Methods", allowMethods);
		response.setHeader("Access-Control-Allow-Credentials", allowCredentials);
		response.setHeader("Access-Control-Allow-Headers", allowHeaders);
		response.setHeader("Access-Control-Expose-Headers", exposeHeaders);
		filterChain.doFilter(servletRequest, servletResponse);
	}


	@Override
	public void destroy() {

	}
}
