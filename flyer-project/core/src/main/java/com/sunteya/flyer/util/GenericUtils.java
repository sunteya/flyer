/**
 * Created on 2006-9-8
 * Created by Sunteya
 */
package com.sunteya.flyer.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 *
 * @author 诸南敏
 * @email zhunm@dep5.com
 */
public abstract class GenericUtils {

	public static <T> Class<T> getSuperClassTypeArgument(Object target) {
		return getSuperClassTypeArgument(target, 0);
	}

	public static <T> Class<T> getSuperClassTypeArgument(Object target, int index) {
		return getSuperClassTypeArgument(target.getClass(), index);
	}

	public static <T> Class<T> getSuperClassTypeArgument(Class<?> clazz) {
		return getSuperClassTypeArgument(clazz, 0);

	}

	public static <T> Class<T> getSuperClassTypeArgument(Class<?> clazz, int index) {

		Class<?> genericClazz = findHaveGenericSuperclass(clazz);
		if(genericClazz == null) {
			return null;
		}

		ParameterizedType parameterizedType = (ParameterizedType) genericClazz.getGenericSuperclass();
		return getActualTypeArguments(parameterizedType, index);
	}

	private static Class<?> findHaveGenericSuperclass(Class<?> clazz) {
		Class<?> currentClass = clazz;

		while (currentClass != null) {
			if (currentClass.getGenericSuperclass() instanceof ParameterizedType) {
				return currentClass;
			}

			currentClass = currentClass.getSuperclass();
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	private static <T> Class<T> getActualTypeArguments(ParameterizedType parameterizedType, int index) {
		Type type = parameterizedType.getActualTypeArguments()[index];
		if(type instanceof ParameterizedType) {
			type = ((ParameterizedType) type).getRawType();
		}

		return (Class<T>) type;
	}
}
