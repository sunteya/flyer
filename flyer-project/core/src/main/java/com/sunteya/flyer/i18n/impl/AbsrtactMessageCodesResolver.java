/**
 * Created on 2007-4-5
 * Created by Sunteya
 */
package com.sunteya.flyer.i18n.impl;

import com.sunteya.flyer.i18n.MessageCodesResolver;

/**
 * @author Sunteya
 *
 */
public abstract class AbsrtactMessageCodesResolver implements MessageCodesResolver {

	public String[] resolve(Object obj) {
		if(!support(obj)) {
			return null;
		}

		return doResolve(obj);
	}

	protected abstract String[] doResolve(Object obj);

	protected boolean support(Object obj) {
		if(obj == null) {
			return false;
		}

		return supportClass(obj.getClass());
	}

	protected boolean supportClass(Class<?> clazz) {
		return getSupportClass().isAssignableFrom(clazz);
	}

	protected Class<?> getSupportClass() {
		return Object.class;
	}




}
