/**
 * Created on Mar 5, 2007
 * Created by Lewisou
 */
package com.sunteya.flyer.springmvc.flash;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

/**
 * @author Lewisou
 *
 */
public class FlashModelInteceptor extends HandlerInterceptorAdapter {

	public static String FLASH_KEY = "_flash";

	@SuppressWarnings("unchecked")
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler, ModelAndView mv) throws Exception {
		if(mv == null || StringUtils.isBlank(mv.getViewName())) {
			return ;
		}

		String viewName = mv.getViewName();
		if (viewName.startsWith(UrlBasedViewResolver.REDIRECT_URL_PREFIX)) {
			Map<String, Object> flashModel = new HashMap<String, Object>(mv.getModel());
			flashModel.remove(FLASH_KEY);

			FlashModelUtils.hook(request, flashModel);
		} else {
			Map<String, Object> flashModel = FlashModelUtils.unhook(request);
			if(flashModel != null) {
				mv.addObject(FLASH_KEY, flashModel);
			}
		}
	}
}
