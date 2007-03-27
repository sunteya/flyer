/**
 * Created on 2006-9-21
 * Created by Sunteya
 */
package com.sunteya.flyer.web.holder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author 诸南敏
 * @email zhunm@dep5.com
 */
public class HttpServletHolder {

	private static ThreadLocal<HttpServletRequest> requestThreadLocal = new InheritableThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> responseThreadLocal = new InheritableThreadLocal<HttpServletResponse>();

	public static HttpServletRequest getRequest() {
		return requestThreadLocal.get();
	}

	public static void setRequest(HttpServletRequest request) {
		requestThreadLocal.set(request);
	}

	public static void removeRequest() {
		requestThreadLocal.remove();
	}

	public static HttpServletResponse getResponse() {
		return responseThreadLocal.get();
	}

	public static void setResponse(HttpServletResponse response) {
		responseThreadLocal.set(response);
	}

	public static void removeResponse() {
		responseThreadLocal.remove();
	}


}
