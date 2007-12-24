/**
 * Created on 2008-1-29
 * Created by Sunteya
 */
package com.sunteya.flyer.web.route;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunteya.flyer.web.filter.HttpFilterSupport;
import com.sunteya.flyer.web.util.RequestUtils;


/**
 * @author Sunteya
 *
 */
public class ActionRoutingFilter extends HttpFilterSupport {

	@SuppressWarnings("unchecked")
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		String servletPath = RequestUtils.getServletPath(request);
		ActionRouting routing = new ActionRouting(servletPath);
		routing.setParams(new HashMap<String, String[]>(request.getParameterMap()));
		ActionRoutingHolder.addLastRouting(routing);
			
		try {
			chain.doFilter(request, response);
		} finally {
			ActionRoutingHolder.clearRoutingList();
		}
	}
}
