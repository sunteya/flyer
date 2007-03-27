/**
 * Created on 2007-4-5
 * Created by Sunteya
 */
package com.sunteya.flyer.test;

import java.util.List;

import net.sf.cglib.core.CollectionUtils;
import net.sf.cglib.core.VisibilityPredicate;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;

import org.easymock.classextension.internal.ClassInstantiatorFactory;

/**
 * @author Sunteya
 *
 */
public class Assert {

	@SuppressWarnings("unchecked")
	public static <T> T assertException(Class<? extends Exception> expected, T actual) throws SecurityException, NoSuchMethodException {
		AssertExceptionInterceptor interceptor = new AssertExceptionInterceptor(expected, actual);

        Enhancer enhancer = new Enhancer() {
            protected void filterConstructors(Class sc, List constructors) {
                CollectionUtils.filter(constructors, new VisibilityPredicate(
                        sc, true));
            }
        };

        Class toMock = actual.getClass();
        enhancer.setSuperclass(toMock);
        enhancer.setCallbackType(interceptor.getClass());
        enhancer.setUseCache(false);

        Class mock = enhancer.createClass();
        Enhancer.registerCallbacks(mock, new Callback[] { interceptor });
        try {
            return (T) ClassInstantiatorFactory.getInstantiator().newInstance(
                    mock);
        } catch (InstantiationException e) {
            throw new RuntimeException("Fail to instantiate mock for " + toMock
                    + " on " + ClassInstantiatorFactory.getJVM() + " JVM");
        }
	}
}
