/**
 * Created on 2007-4-5
 * Created by Sunteya
 */
package com.sunteya.flyer.web.binder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;

/**
 * @author Sunteya
 *
 */
public class RequestParameters {

	private HttpServletRequest request;
	private String contextName = "";

	public RequestParameters(HttpServletRequest request) {
		this.request = request;
	}

	public String getString(String name) throws ServletRequestBindingException {
		return ServletRequestUtils.getStringParameter(request, name);
	}

	public String getString(String name, String defaultVal) {
		return ServletRequestUtils
				.getStringParameter(request, name, defaultVal);
	}

	public boolean getBoolean(String name, boolean defaultVal) {
		return ServletRequestUtils.getBooleanParameter(request, name,
				defaultVal);
	}

	public String[] getStrings(String name) {
		return ServletRequestUtils.getStringParameters(request, name);
	}

	public long getRequiredLong(String name) throws ServletRequestBindingException {
		try {
			return ServletRequestUtils.getRequiredLongParameter(request, name);
		} catch (MissingServletRequestParameterException re) {
			throw re;
		} catch (ServletRequestBindingException e) {
			throw new TypeMismatchBindException(contextName, name, e);
		}
	}

	public int getRequiredInt(String name) throws ServletRequestBindingException {
		try {
			return ServletRequestUtils.getRequiredIntParameter(request, name);
		} catch (MissingServletRequestParameterException re) {
			throw re;
		} catch (ServletRequestBindingException e) {
			throw new TypeMismatchBindException(contextName, name, e);
		}
	}
}
