/**
 * Created on 2008-1-29
 * Created by Sunteya
 */
package com.sunteya.flyer.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

/**
 * @author Sunteya
 *
 */
public abstract class HttpFilterSupport extends GenericFilterBean {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		doFilterInternal((HttpServletRequest)request, (HttpServletResponse)response, chain);
	}

	protected abstract void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException;
}