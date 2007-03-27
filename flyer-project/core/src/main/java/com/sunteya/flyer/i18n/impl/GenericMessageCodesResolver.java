/**
 * Created on 2007-4-5
 * Created by Sunteya
 */
package com.sunteya.flyer.i18n.impl;

import com.sunteya.flyer.util.GenericUtils;

/**
 * @author Sunteya
 *
 */
public abstract class GenericMessageCodesResolver<T> extends AbsrtactMessageCodesResolver {

	private Class<?> supportClass;

	public GenericMessageCodesResolver() {
		supportClass = GenericUtils.getSuperClassTypeArgument(this);
	}

	public GenericMessageCodesResolver(Class<?> supportClass) {
		this.supportClass = supportClass;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected String[] doResolve(Object obj) {
		return resolveObject((T) obj);
	}

	protected abstract String[] resolveObject(T obj);

	@Override
	protected Class<?> getSupportClass() {
		return supportClass;
	}
}
