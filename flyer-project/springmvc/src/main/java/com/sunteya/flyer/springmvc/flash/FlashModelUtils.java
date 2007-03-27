/**
 * Created on Mar 5, 2007
 * Created by Lewisou
 */
package com.sunteya.flyer.springmvc.flash;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author Lewisou
 *
 */
public class FlashModelUtils {

	private static String FLASH_SESSION_KEY = FlashModelUtils.class.getName() + ".SESSION";

	@SuppressWarnings("unchecked")
	public static Map<String, Object> unhook(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object model = session.getAttribute(FLASH_SESSION_KEY);
		session.removeAttribute(FLASH_SESSION_KEY);

		return (Map<String, Object>) model;
	}

	public static void hook(HttpServletRequest request, Map<String, Object> model) {
		request.getSession().setAttribute(FLASH_SESSION_KEY, model);
	}
}
