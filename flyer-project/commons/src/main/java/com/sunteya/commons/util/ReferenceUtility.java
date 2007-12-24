/**
 * Created on 2007-7-23
 * Created by Sunteya
 */
package com.sunteya.commons.util;

import java.lang.reflect.Field;

import org.apache.commons.lang.exception.NestableRuntimeException;

/**
 * @author Sunteya
 *
 */
public class ReferenceUtility {
	public static Object getValue(Object obj, Field field) {
		try {
			field.setAccessible(true);
			return field.get(obj);
		} catch (Exception e) {
			throw new NestableRuntimeException(e);
		}
	}
}
