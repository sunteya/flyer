/**
 * Created on 2007-4-17
 * Created by Sunteya
 */
package com.sunteya.flyer.web.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * @author Sunteya
 *
 */
public abstract class RequestUtils {

	public static String absolutePath(HttpServletRequest request, String path) {
		if(path.indexOf("://") > 0) {
			return path;
		}

		String answer = path;
		if(!path.startsWith("/")) {
			String servletPath = request.getServletPath();
			answer = StringUtils.substringBeforeLast(servletPath, "/") + "/" + path;
		}

		return answer;
	}
}
