/**
 * Created on 2007-1-15
 * Created by Sunteya
 */
package com.sunteya.flyer.i18n.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * @author Sunteya
 *
 */
public class DefaultEnumMessageCodesResolver extends GenericMessageCodesResolver<Enum<?>> {

	@Override
	protected String[] resolveObject(Enum<?> enu) {
		List<String> answer = new ArrayList<String>();

		String name = enu.name();
		Class<?> declaringClass = enu.getDeclaringClass();
		String fullClassName = declaringClass.getName();
		answer.add(fullClassName + "." + name);

		if (isNestClass(fullClassName)) {
			answer.add(getSimpleNestClassName(fullClassName) + "." + name);
		}

		String simpleName = declaringClass.getSimpleName();
		answer.add(simpleName + "." + name);

		return answer.toArray(new String[0]);
	}

	private String getSimpleNestClassName(String fullClassName) {
		return StringUtils.substringAfterLast(fullClassName, ".");
	}

	private boolean isNestClass(String fullClassName) {
		return StringUtils.contains(fullClassName, "$");
	}


}
