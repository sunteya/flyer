/**
 * Created on Feb 14, 2007
 * Created by Lewisou
 */
package com.sunteya.flyer.support.beans;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang.StringUtils;

/**
 * @author Lewisou
 *
 */
public class CustomEnumEditor extends PropertyEditorSupport {

	@SuppressWarnings("unchecked")
	private Class enumClass;

	public CustomEnumEditor(Class<?> enumClass) {
		this.enumClass = enumClass;
	}

	public void setAsText(String text) {
		setValue(textToEnum(text));
	}

	@SuppressWarnings("unchecked")
	private Object textToEnum(String text) {
		if (StringUtils.isBlank(text)) {
			return null;
		}

		return Enum.valueOf(enumClass, text);
	}

	public String getAsText() {
		Object value = getValue();
		if (value == null) {
			return "";
		}

		return value.toString();
	}
}
