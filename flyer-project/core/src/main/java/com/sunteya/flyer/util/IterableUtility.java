/**
 * Created on 2007-9-5
 * Created by Sunteya
 */
package com.sunteya.flyer.util;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Iterator;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.apache.commons.collections.iterators.ArrayIterator;
import org.apache.commons.collections.iterators.EnumerationIterator;

/**
 * @author Sunteya
 *
 */
public class IterableUtility {

	public static <T> T each(Iterable<T> elements, Class<T> elementClass) {
		return each(elements.iterator(), elementClass);
	}

	@SuppressWarnings("unchecked")
	public static <T> T each(Enumeration<T> enums, Class<T> elementClass) {
		return each((Iterator<T>) new EnumerationIterator(enums), elementClass);
	}

	@SuppressWarnings("unchecked")
	public static <T> T each(T[] elements, Class<T> elementClass) {
		return each((Iterator<T>) new ArrayIterator(elements), elementClass);
	}

	public static <T> T each(final Iterator<T> iter, Class<T> elementClass) {
		return MockUtils.mock(elementClass, new MethodInterceptor() {
			public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
				if(iter == null) {
					return null;
				}

				while (iter.hasNext()) {
					T element = (T) iter.next();
					proxy.invoke(element, args);
				}

				return null;
			}
		});
	}
}
