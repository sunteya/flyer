/**
 * Created on 2007-1-11
 * Created by Sunteya
 */
package com.sunteya.flyer.support.beans;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;

/**
 * @author Sunteya
 *
 */
public class EnumPropertyEditor extends PropertyEditorSupport {

	public void setAsText(String text) throws IllegalArgumentException {
		setValue(textToValue(text));
	}

	public String getAsText() {
		return valueToText(getValue());
	}

	@SuppressWarnings("unchecked")
	public Object textToValue(String text) {
		if (StringUtils.isBlank(text)) {
			return null;
		}

		String className = StringUtils.substringBeforeLast(text, ".");
		String name = StringUtils.substringAfterLast(text, ".");

		try {
			Class clazz = Class.forName(className);
			return Enum.valueOf(clazz, name);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Enum class not found", e);
		}
	}

	public String valueToText(Object value) {
		if (value == null) {
			return null;
		}

		Enum<?> ele = (Enum<?>) value;
		String className = ele.getDeclaringClass().getName();
		return className + "." + ele.name();
	}

}
