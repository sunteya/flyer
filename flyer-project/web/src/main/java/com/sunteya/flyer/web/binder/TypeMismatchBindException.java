/**
 * Created on 2007-4-5
 * Created by Sunteya
 */
package com.sunteya.flyer.web.binder;

import org.springframework.web.bind.ServletRequestBindingException;

/**
 * @author Sunteya
 *
 */
public class TypeMismatchBindException extends ServletRequestBindingException {

	private static final long serialVersionUID = -3955345361059923867L;

	public TypeMismatchBindException(String contextName, String propertyName, Throwable cause) {
		super("", cause);
	}
}
