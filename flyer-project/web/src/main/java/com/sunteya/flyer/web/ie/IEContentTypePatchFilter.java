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
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sunteya.flyer.web.util.AgentUtils;

/**
 * @author Sunteya
 *
 */
public class IEContentTypePatchFilter extends OncePerRequestFilter implements Filter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if (AgentUtils.isInternetExplorer(request)) {
			HttpServletResponse httpResponse = new IEContenTypePatchHttpResponse(response);
			filterChain.doFilter(request, httpResponse);
		} else {
			filterChain.doFilter(request, response);
		}
	}

	class IEContenTypePatchHttpResponse extends HttpServletResponseWrapper {
		public IEContenTypePatchHttpResponse(HttpServletResponse response) {
			super(response);
		}

		@Override
		public void setContentType(String type) {
			String newType = StringUtils.replace(type, "application/xhtml+xml", "text/html");
			super.setContentType(newType);
		}
	}
}
