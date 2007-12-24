/**
 * Created on 2007-6-15
 * Created by Sunteya
 */
package com.sunteya.commons.util;

import org.apache.commons.lang.StringUtils;

/**
 * @author Sunteya
 *
 */
public class StringUtility {
	public static String underscores(String camel) {
		return underscores(camel, "_");
	}

	public static String underscores(String source, String separator) {
		if(source == null) {
			return null;
		}

		StringBuilder target = new StringBuilder();
		for (int i = 0; i < source.length(); i++) {
			char c = source.charAt(i);
			if(i == 0) {
				target.append(c);
				continue;
			}

			if(Character.isUpperCase(c)) {
				target.append(separator);
				target.append(Character.toLowerCase(c));
			} else {
				target.append(c);
			}
		}

		return target.toString();
	}

	public static String camel(String underscores) {
		return camel(underscores, "_");
	}

	public static String camel(String source, String separator) {
		if(source == null) {
			return null;
		}

		StringBuilder target = new StringBuilder();
		String[] tokens = StringUtils.split(source, separator);
		for (int i = 0; i < tokens.length; i++) {
			String token = tokens[i];
			if(i == 0) {
				target.append(token);
			} else {
				target.append(StringUtils.capitalize(token));
			}
		}

		return target.toString();
	}
}
