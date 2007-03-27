/**
 * Created on 2007-3-1
 * Created by Sunteya
 */
package com.sunteya.flyer.springmvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

/**
 * @author Sunteya
 *
 */
public class ParameterRedirectInterceptor extends HandlerInterceptorAdapter {

	public static String DEFAULT_REDIRECT_KEY = "redirect";

	private String redirectKey = DEFAULT_REDIRECT_KEY;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		String url = getRedirectUrl(request);
		if(StringUtils.isBlank(url)) {
			return;
		}

		if(modelAndView != null) {
			modelAndView.setViewName(UrlBasedViewResolver.REDIRECT_URL_PREFIX + url);
			modelAndView.clear();
		} else {
			response.sendRedirect(url);
		}
	}

	private String getRedirectUrl(HttpServletRequest request) {
		return request.getParameter(getRedirectKey());
	}

	public void setRedirectKey(String redirectKey) {
		this.redirectKey = redirectKey;
	}

	public String getRedirectKey() {
		return redirectKey;
	}
}
