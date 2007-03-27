/**
 * Created on 2006-12-31
 * Created by Sunteya
 */
package com.sunteya.flyer.springmvc.interceptor;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ognl.Ognl;
import ognl.OgnlException;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import com.sunteya.flyer.web.util.RequestUtils;

/**
 * @author Sunteya
 *
 */
public class RedirectExpressInterceptor extends HandlerInterceptorAdapter {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(RedirectExpressInterceptor.class);

	public static String REDIRECT_URL_PREFIX = UrlBasedViewResolver.REDIRECT_URL_PREFIX;

	@SuppressWarnings("unchecked")
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		if(modelAndView == null) {
			return;
		}

		String viewName = StringUtils.trimToEmpty(modelAndView.getViewName());

		if (!viewName.startsWith(REDIRECT_URL_PREFIX)) {
			return;
		}
		String redirectViewName = viewName.substring(REDIRECT_URL_PREFIX.length());

		Pattern p = Pattern.compile("#\\{([^#{}]+)\\}");
		Matcher m = p.matcher(redirectViewName);

		String[] expresses = new String[0];
		for (int i = 0; m.find(i); i = m.end()) {
			expresses = (String[]) ArrayUtils.add(expresses, m.group(1));
		}

		Map<String, Object> model = modelAndView.getModel();
		String url = redirectViewName;
		for (String express : expresses) {

			String value = getExpressValue(model, express);
			url = StringUtils.replace(url, "#{" + express + "}", value);
		}

		url = RequestUtils.absolutePath(request, url);
		modelAndView.getModel().clear();
		modelAndView.setViewName(REDIRECT_URL_PREFIX + url);
	}

	private String getExpressValue(Map<String, Object> model, String express) {
		try {
			Object value = Ognl.getValue(express, model);
			return value.toString();
		} catch (OgnlException e) {
			logger.error("OgnlException , express=" + express + ", model=" + model, e); //$NON-NLS-1$
			return "";
		}
	}
}
