/**
 * Created on 2006-11-20
 * Created by Sunteya
 */
package com.sunteya.flyer.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sunteya.flyer.web.holder.PageUrlHolder;

/**
 * @author Sunteya
 *
 */
public class PageUrlHolderFilter extends OncePerRequestFilter implements Filter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String pageUrl = request.getRequestURL().toString();
		String queryString = request.getQueryString();
		if (StringUtils.isNotBlank(queryString)) {
			pageUrl += "?" + queryString;
		}
		PageUrlHolder.set(pageUrl);

		filterChain.doFilter(request, response);

		PageUrlHolder.clear();
	}
}
