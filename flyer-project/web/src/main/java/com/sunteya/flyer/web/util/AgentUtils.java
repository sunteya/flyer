/**
 * Created on 2007-3-4
 * Created by Sunteya
 */
package com.sunteya.flyer.web.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * @author Sunteya
 *
 */
public abstract class AgentUtils {

	public static final String HEADER_USER_AGENT = "User-Agent";

	public static boolean isInternetExplorer(HttpServletRequest request) {
		String userAgent = request.getHeader(HEADER_USER_AGENT);
		return StringUtils.contains(userAgent, "MSIE");
	}
}
