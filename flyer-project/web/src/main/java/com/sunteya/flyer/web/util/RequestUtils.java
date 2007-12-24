/**
 * Created on 2007-4-17
 * Created by Sunteya
 */
package com.sunteya.flyer.web.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author Sunteya
 *
 */
public abstract class RequestUtils {

    public static String getServletPath(HttpServletRequest request) {
        String servletPath = request.getServletPath();

        if (null != servletPath && !"".equals(servletPath)) {
            return servletPath;
        }

        String requestUri = request.getRequestURI();
        int startIndex = request.getContextPath().equals("") ? 0 : request.getContextPath().length();
        int endIndex = request.getPathInfo() == null ? requestUri.length() : requestUri.lastIndexOf(request.getPathInfo());

        if (startIndex > endIndex) { // this should not happen
            endIndex = startIndex;
        }

        return requestUri.substring(startIndex, endIndex);
    }

	@SuppressWarnings("unchecked")
	public static Map<String, String[]> cloneParameterMap(HttpServletRequest request) {
		Map<String, String[]> answer = new HashMap<String, String[]>();

		Map<String, String[]> parameterMap = request.getParameterMap();
		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			answer.put(entry.getKey(), (String[]) ArrayUtils.clone(entry.getValue()));
		}

		return answer;
	}

	public static String absolutePath(HttpServletRequest request, String path) {
		if(StringUtils.contains(path, "://" )) {
			return path;
		}

		String answer = path;
		if(!path.startsWith("/")) {
			String servletPath = request.getServletPath();
			answer = StringUtils.substringBeforeLast(servletPath, "/") + "/" + path;
		}

		return answer;
	}

	public static String appendContextPathIfNeed(HttpServletRequest request, String url) {
		if(StringUtils.isBlank(url)) {
			return "";
		}

		if(StringUtils.contains(url, "://" )) {
			return url;
		}

		String contextPath = request.getContextPath();
		if(Arrays.asList("", "/").contains(contextPath)) {
			return url;
		} else {
			return contextPath + url;
		}
	}
}
