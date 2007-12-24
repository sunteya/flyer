/**
 * Created on 2007-1-15
 * Created by Sunteya
 */
package com.sunteya.flyer.i18n.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.sunteya.flyer.i18n.MessageCode;

/**
 * @author Sunteya
 *
 */
public class DefaultEnumMessageCodeResolver extends GenericMessageCodeResolver<Enum<?>> {

	@Override
	protected MessageCode resolveObject(Enum<?> enu) {
		List<String> codes = new ArrayList<String>();

		String name = enu.name();
		Class<?> declaringClass = enu.getDeclaringClass();

		String fullClassName = declaringClass.getName();
		codes.add(fullClassName + "." + name);

		if (isNestClass(fullClassName)) {
			codes.add(getSimpleNestClassName(fullClassName) + "." + name);
		}

		codes.add(declaringClass.getSimpleName() + "." + name);

		return new MessageCode(codes);
	}

	private String getSimpleNestClassName(String fullClassName) {
		return StringUtils.substringAfterLast(fullClassName, ".");
	}

	private boolean isNestClass(String fullClassName) {
		return StringUtils.contains(fullClassName, "$");
	}


}
