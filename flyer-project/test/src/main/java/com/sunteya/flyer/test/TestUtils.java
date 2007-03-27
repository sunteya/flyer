/**
 * Created on 2006-11-13
 * Created by Sunteya
 */
package com.sunteya.flyer.test;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * @author Sunteya
 *
 */
public class TestUtils {

	public static Date date(String date) {
		try {
			return DateUtils.parseDate(date, new String[] { "yyyy-MM-dd" });
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static Date timestamp(String date) throws Exception {
		return DateUtils
				.parseDate(date, new String[] { "yyyy-MM-dd HH:mm:ss" });
	}

	public static Date time(String date) throws Exception {
		return DateUtils.parseDate(date, new String[] { "hh:mm:ss" });
	}

	public static void loopCall(Object target, String methodName,
			Object... loopParams) throws Exception {

		for (int i = 0; i < loopParams.length; i++) {
			Object[] params = null;

			Object aLoopParams = loopParams[i];
			if (aLoopParams.getClass().isArray()) {
				int paramLen = Array.getLength(aLoopParams);
				params = new Object[paramLen];
				for (int paramIndex = 0; paramIndex < paramLen; paramIndex++) {
					params[paramIndex] = Array.get(aLoopParams, paramIndex);
				}
			} else {
				params = new Object[] { aLoopParams };
			}

			Class<?>[] parameterTypes = new Class[params.length];
			for (int j = 0; j < parameterTypes.length; j++) {
				parameterTypes[j] = params[j].getClass();
			}

			MethodUtils.invokeMethod(target, methodName, params);
		}
	}
}
