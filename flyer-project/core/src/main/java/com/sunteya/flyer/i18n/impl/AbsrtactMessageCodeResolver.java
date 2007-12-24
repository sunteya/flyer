/**
 * Created on 2007-4-5
 * Created by Sunteya
 */
package com.sunteya.flyer.i18n.impl;

import com.sunteya.flyer.i18n.MessageCode;
import com.sunteya.flyer.i18n.MessageCodeResolver;

/**
 * @author Sunteya
 *
 */
public abstract class AbsrtactMessageCodeResolver implements MessageCodeResolver {

	public MessageCode resolveMessageCode(Object obj) {
		if(!support(obj)) {
			return null;
		}

		return doResolve(obj);
	}

	protected abstract MessageCode doResolve(Object obj);

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
