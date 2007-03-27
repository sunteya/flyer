/**
 * Created on 2007-3-7
 * Created by Sunteya
 */
package com.sunteya.flyer.springmvc.view;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.mvc.multiaction.MethodNameResolver;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

/**
 * @author Sunteya
 *
 */
public class NiceParameterMethodNameResolver implements MethodNameResolver {

	private static final String PARAM_DEFAULT_KEY = "change";

	private String paramName = PARAM_DEFAULT_KEY;

	public String getHandlerMethodName(HttpServletRequest request) throws NoSuchRequestHandlingMethodException {
		try {
			String name = ServletRequestUtils.getRequiredStringParameter(request, paramName);
			return paramName + StringUtils.capitalize(name);
		} catch (ServletRequestBindingException e) {
			throw new NoSuchRequestHandlingMethodException(request);
		}
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
}
