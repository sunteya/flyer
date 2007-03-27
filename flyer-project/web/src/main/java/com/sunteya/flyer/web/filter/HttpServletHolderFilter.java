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

import org.springframework.web.filter.OncePerRequestFilter;

import com.sunteya.flyer.web.holder.HttpServletHolder;

/**
 * @author Sunteya
 *
 */
public class HttpServletHolderFilter extends OncePerRequestFilter implements Filter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		HttpServletHolder.setRequest(request);
		HttpServletHolder.setResponse(response);

		filterChain.doFilter(request, response);

		HttpServletHolder.removeRequest();
		HttpServletHolder.removeResponse();
	}
}
