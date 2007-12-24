/**
 * Created on 2006-12-13
 * Created by Sunteya
 */
package com.sunteya.flyer.web.ie;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.sunteya.flyer.web.util.AgentUtils;

/**
 * @author Sunteya
 *
 */
public class IENoCacheFilter extends OncePerRequestFilter implements Filter {

	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		if (AgentUtils.isInternetExplorer(request)) {
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Expires", "-1");
		}

		filterChain.doFilter(request, response);
	}
}
