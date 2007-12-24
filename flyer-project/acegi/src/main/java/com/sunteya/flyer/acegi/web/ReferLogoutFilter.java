/**
 * Created on 2007-12-25
 * Created by Sunteya
 */
package com.sunteya.flyer.acegi.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.Authentication;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.ui.logout.LogoutHandler;

/**
 * @author Sunteya
 *
 */
public class ReferLogoutFilter implements Filter {

    private String filterProcessesUrl = "/j_acegi_logout";
    private String referKey = "okUrl";
    private String defaultLogoutSuccessUrl;
    private LogoutHandler[] handlers = new LogoutHandler[0];

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        if (requiresLogout(request, response)) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();

            for (LogoutHandler handler : getHandlers()) {
            	handler.logout(request, response, auth);
			}

            sendRedirect(request, response, obtainSuccessUrl(request));
            return;
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig arg0) throws ServletException {}

    public void destroy() {}

    protected boolean requiresLogout(HttpServletRequest request, HttpServletResponse response) {
        String uri = request.getRequestURI();
        int pathParamIndex = uri.indexOf(';');

        if (pathParamIndex > 0) {
            uri = uri.substring(0, pathParamIndex);
        }

        if ("".equals(request.getContextPath())) {
        	return uri.endsWith(filterProcessesUrl);
        }

        return uri.endsWith(request.getContextPath() + filterProcessesUrl);
    }

    protected void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url)
        throws IOException {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = request.getContextPath() + url;
        }

        response.sendRedirect(response.encodeRedirectURL(url));
    }

    protected String obtainSuccessUrl(HttpServletRequest request) {
    	String url = request.getParameter(getReferKey());
    	if(url == null) {
    		return getDefaultLogoutSuccessUrl();
    	}

        return url;
    }

    // =====================================================
	// Gettings And Settings
	// -----------------------------------------------------
    public void setFilterProcessesUrl(String filterProcessesUrl) {
        this.filterProcessesUrl = filterProcessesUrl;
    }

	public String getDefaultLogoutSuccessUrl() {
		return defaultLogoutSuccessUrl;
	}

	public void setDefaultLogoutSuccessUrl(String defaultLogoutSuccessUrl) {
		this.defaultLogoutSuccessUrl = defaultLogoutSuccessUrl;
	}

	public String getFilterProcessesUrl() {
		return filterProcessesUrl;
	}

	public LogoutHandler[] getHandlers() {
		return handlers;
	}

	public String getReferKey() {
		return referKey;
	}

	public void setReferKey(String referKey) {
		this.referKey = referKey;
	}

	public void setHandlers(LogoutHandler[] handlers) {
		this.handlers = handlers;
	}
}
