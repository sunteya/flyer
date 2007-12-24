/**
 * Created on 2007-6-28
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.interceptor;

import ognl.TypeConverter;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.XWorkConverter;

/**
 * @author Sunteya
 *
 */
public abstract class AbstractGlobalTypeConverterInterceptor extends AbstractInterceptor {

	private static boolean init = false;

	private ObjectFactory objectFactory;
	private XWorkConverter xworkConverter;

	@Override
	public synchronized String intercept(ActionInvocation invocation) throws Exception {
		if(init  == false) {
			registerGlobalTypeConverters();
			init = true;
		}

		return invocation.invoke();
	}

	protected void registerConverter(Class<?> clazz, Class<? extends TypeConverter> converterClass) throws Exception {
		TypeConverter converter = (TypeConverter) objectFactory.buildBean(converterClass, null);
		registerConverter(clazz, converter);
	}

	protected void registerConverter(Class<?> clazz, TypeConverter converter) {
		xworkConverter.registerConverter(clazz.getName(), converter);
	}

	protected abstract void registerGlobalTypeConverters() throws Exception;

	// =====================================================
	// Gettings And Settings
	// -----------------------------------------------------
	@Inject
	public void setObjectFactory(ObjectFactory objectFactory) {
		this.objectFactory = objectFactory;
	}

	@Inject
	public void setXworkConverter(XWorkConverter xworkConverter) {
		this.xworkConverter = xworkConverter;
	}
}
