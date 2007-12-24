/**
 * Created on 2007-4-5
 * Created by Sunteya
 */
package com.sunteya.flyer.i18n.impl;

import com.sunteya.commons.util.GenericUtils;
import com.sunteya.flyer.i18n.MessageCode;
import com.sunteya.flyer.i18n.MessageCodeResolver;

/**
 * @author Sunteya
 *
 */

public abstract class GenericMessageCodeResolver<T> implements MessageCodeResolver {

	private Class<?> supportClass;

	public GenericMessageCodeResolver() {
		setSupportClass(GenericUtils.getSuperClassTypeArgument(this));
	}

	public GenericMessageCodeResolver(Class<?> supportClass) {
		setSupportClass(supportClass);
	}

	protected boolean supportClass(Class<?> clazz) {
		return getSupportClass().isAssignableFrom(clazz);
	}


	@SuppressWarnings("unchecked")
	public MessageCode resolveMessageCode(Object obj) {
		if(obj == null || !supportClass(obj.getClass())) {
			return null;
		}

		return resolveObject((T) obj);
	}

	protected abstract MessageCode resolveObject(T obj);

	// =====================================================
	// Gettings And Settings
	// -----------------------------------------------------
	protected Class<?> getSupportClass() {
		return supportClass;
	}

	public void setSupportClass(Class<?> supportClass) {
		this.supportClass = supportClass;
	}
}
