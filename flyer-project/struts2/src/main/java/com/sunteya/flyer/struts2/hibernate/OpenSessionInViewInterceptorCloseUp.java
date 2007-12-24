package com.sunteya.flyer.struts2.hibernate;
import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Created on 2007-12-29
 * Created by Sunteya
 */

/**
 * @author Sunteya
 *
 */
public class OpenSessionInViewInterceptorCloseUp extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		try {
			filterChain.doFilter(request, response);
		} finally {
			lookUpInterceptor(request).closeSession();
		}
	}

	private OpenHibernateSessionInterceptor lookUpInterceptor(HttpServletRequest request) {
		ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		String name = applicationContext.getBeanNamesForType(OpenHibernateSessionInterceptor.class)[0];
		return (OpenHibernateSessionInterceptor) applicationContext.getBean(name);
	}
}
