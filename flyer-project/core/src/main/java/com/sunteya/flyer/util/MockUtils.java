/**
 * Created on 2007-9-5
 * Created by Sunteya
 */
package com.sunteya.flyer.util;

import java.util.List;

import com.sunteya.commons.util.DefaultClassInstantiator;

import net.sf.cglib.core.CollectionUtils;
import net.sf.cglib.core.VisibilityPredicate;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;

/**
 * @author Sunteya
 *
 */
public abstract class MockUtils {

	@SuppressWarnings("unchecked")
	public static <T> T mock(Class<? extends T> toMock, Callback callback) {
		Enhancer enhancer = new Enhancer() {
			protected void filterConstructors(Class sc, List constructors) {
	            CollectionUtils.filter(constructors, new VisibilityPredicate(sc, true));
	        }
	    };

	    enhancer.setSuperclass(toMock);
	    enhancer.setCallbackType(callback.getClass());
	    enhancer.setUseCache(false);

	    Class<?> mock = enhancer.createClass();
	    Enhancer.registerCallbacks(mock, new Callback[] { callback });
	    try {
	        return (T) new DefaultClassInstantiator().newInstance(mock);
	    } catch (InstantiationException e) {
	        throw new RuntimeException("Fail to instantiate mock for " + toMock);
	    }
	}

}
