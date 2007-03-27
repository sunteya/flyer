/**
 * Created on 2007-4-5
 * Created by Sunteya
 */
package com.sunteya.flyer.test;

import java.lang.reflect.Method;
import static junit.framework.Assert.*;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * @author Sunteya
 *
 */
public class AssertExceptionInterceptor implements MethodInterceptor {

	private Class<? extends Exception> expected;
	private Object actual;

	public AssertExceptionInterceptor(Class<? extends Exception> expected, Object actual) {
		this.expected = expected;
		this.actual = actual;
	}

	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		try {
			proxy.invoke(actual, args);
			fail();
		} catch (Exception e) {
			if (expected.isAssignableFrom(e.getClass())) {
				return null;
			}
			throw e;
		}

		return null;
	}
}
