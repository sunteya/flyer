/**
 * Created on 2007-4-5
 * Created by Sunteya
 */
package com.sunteya.flyer.test;

import com.sunteya.flyer.util.MockUtils;

/**
 * @author Sunteya
 *
 */
public class Assert {

	@SuppressWarnings("unchecked")
	public static <T> T assertException(Class<? extends Exception> expected, T actual) throws SecurityException, NoSuchMethodException {
		AssertExceptionInterceptor interceptor = new AssertExceptionInterceptor(expected, actual);
		Object answer = MockUtils.mock(actual.getClass(), interceptor);
		return (T) answer;
	}
}
