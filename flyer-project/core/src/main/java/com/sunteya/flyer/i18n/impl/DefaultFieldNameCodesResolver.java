/**
 * Created on 2007-6-28
 * Created by Sunteya
 */
package com.sunteya.flyer.i18n.impl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.sunteya.flyer.i18n.FieldPathResolvable;
import com.sunteya.flyer.i18n.MessageCode;

/**
 * @author Sunteya
 *
 */

public class DefaultFieldNameCodesResolver extends GenericMessageCodeResolver<FieldPathResolvable> {

	public static final String PATH_DELIM = ".";

	private String defaultFieldPathPrefix = "Field.";

	public DefaultFieldNameCodesResolver() {
	}

	public DefaultFieldNameCodesResolver(String defaultFieldPathPrefix) {
		setDefaultFieldPathPrefix(defaultFieldPathPrefix);
	}

	@Override
	protected MessageCode resolveObject(FieldPathResolvable obj) {
		String[] codes = resolveFieldNameCodes(obj.getRoot(), obj.getPath());
		return new MessageCode(Arrays.asList(codes));
	}

	public String[] resolveFieldNameCodes(Object root, String path) {
		List<String> answer = new ArrayList<String>();

		String[] properties = StringUtils.split(path, PATH_DELIM);
		Class<?>[] propertyClasses = analyzePropertyClasses(root, properties);

		for (int i = 0; i < properties.length; i++) {
			Class<?> clazz = propertyClasses[i];
			String[] propertyPath = (String[]) ArrayUtils.subarray(properties, i, properties.length);
			answer.addAll(getPropertyCodes(clazz, propertyPath));
		}

		answer.add(getDefaultFieldPathPrefix() + path);
		return answer.toArray(new String[0]);
	}

	private List<String> getPropertyCodes(Class<?> clazz, String[] propertyPath) {
		List<String> answer = new ArrayList<String>();
		String path = StringUtils.join(propertyPath, PATH_DELIM);

		Class<?>[] targetClasses = findHavePropertyClasses(clazz, propertyPath[0]);
		for (Class<?> targetClass : targetClasses) {
			answer.add(targetClass.getSimpleName() + PATH_DELIM + path);
		}

		return answer;
	}

	private Class<?>[] findHavePropertyClasses(Class<?> clazz, String property) {
		List<Class<?>> answer = new ArrayList<Class<?>>();

		Class<?> targetClass = clazz;
		while(hasProperty(targetClass, property)) {
			answer.add(targetClass);
			targetClass = targetClass.getSuperclass();
		}

		for(Class<?> interfaceClass : clazz.getInterfaces()) {
			if(hasProperty(interfaceClass, property)) {
				answer.add(interfaceClass);
			}
		}

		return answer.toArray(new Class<?>[0]);
	}

	private boolean hasProperty(Class<?> clazz, String property) {
		for(PropertyDescriptor pd : PropertyUtils.getPropertyDescriptors(clazz)) {
			if(pd.getName().equals(property) && pd.getReadMethod() != null) {
				return true;
			}
		}

		return false;
	}

	private Class<?>[] analyzePropertyClasses(Object root, String[] properties) {
		List<Class<?>> answer = new ArrayList<Class<?>>();
		answer.add(root.getClass());

		for (int i = 1; i < properties.length; i++) {
			String[] path = (String[]) ArrayUtils.subarray(properties, 0, i);
			answer.add(analyzePathClass(root, path));
		}

		return answer.toArray(new Class<?>[0]);
	}

	private Class<?> analyzePathClass(Object root, String[] path) {
		Object parent = root;
		for (int i = 0; i < path.length; i++) {
			String property = path[i];
			if(parent instanceof Class) {
				parent = getPropertyClass((Class<?>) parent, property);
			} else {
				parent = getProperty(parent, property);
			}
		}

		if(parent instanceof Class) {
			return (Class<?>) parent;
		} else {
			return parent.getClass();
		}
	}

	private Object getProperty(Object parent, String property) {
		try {
			Object value = PropertyUtils.getProperty(parent, property);
			if(value == null) {
				return getPropertyClass(parent.getClass(), property);
			}

			return value;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	private Class<?> getPropertyClass(Class<?> parent, String property) {
		for(PropertyDescriptor pd : PropertyUtils.getPropertyDescriptors(parent)) {
			if(pd.getName().equals(property)) {
				return pd.getPropertyType();
			}
		}

		throw new IllegalArgumentException();
	}

	// =====================================================
	// Gettings And Settings
	// -----------------------------------------------------
	public String getDefaultFieldPathPrefix() {
		return defaultFieldPathPrefix;
	}

	public void setDefaultFieldPathPrefix(String defaultFieldPathPrefix) {
		this.defaultFieldPathPrefix = defaultFieldPathPrefix;
	}
}
