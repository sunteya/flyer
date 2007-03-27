/**
 * Created on Feb 8, 2007
 * Created by Lewisou
 */
package com.sunteya.flyer.springmvc.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


/**
 * @author Lewisou
 *
 */
public class ReqeustParametersInterceptor extends HandlerInterceptorAdapter {

	private String paramName = "params";

	private String paramSeparator = ",";

	@SuppressWarnings("unchecked")
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if(modelAndView == null) {
			return;
		}

		Map<String, String[]> params = request.getParameterMap();

		Map<String, Object> answer = new HashMap<String, Object>();
		for(String key : params.keySet()) {
			String[] values = params.get(key);
			String value = StringUtils.join(values, getParamSeparator());
			answer.put(key, value);
		}

		modelAndView.addObject(getParamName(), answer);
	}


	// =====================================================
	// Gettings And Settings
	// -----------------------------------------------------
	public String getParamSeparator() {
		return paramSeparator;
	}

	public void setParamSeparator(String paramSeparator) {
		this.paramSeparator = paramSeparator;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
}
