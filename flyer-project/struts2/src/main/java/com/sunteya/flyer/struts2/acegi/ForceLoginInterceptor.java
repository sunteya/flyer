/**
 * Created on 2007-8-2
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.acegi;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.AccessDeniedException;
import org.acegisecurity.AcegiSecurityException;
import org.acegisecurity.AuthenticationException;
import org.acegisecurity.AuthenticationTrustResolver;
import org.acegisecurity.AuthenticationTrustResolverImpl;
import org.acegisecurity.InsufficientAuthenticationException;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.ui.AbstractProcessingFilter;
import org.acegisecurity.ui.AuthenticationEntryPoint;
import org.acegisecurity.ui.savedrequest.SavedRequest;
import org.acegisecurity.util.PortResolver;
import org.acegisecurity.util.PortResolverImpl;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @author Sunteya
 *
 */
public class ForceLoginInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -1260541828894630232L;

    private AuthenticationTrustResolver authenticationTrustResolver = new AuthenticationTrustResolverImpl();
    private AuthenticationEntryPoint authenticationEntryPoint;
    private PortResolver portResolver = new PortResolverImpl();

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext().get(StrutsStatics.HTTP_REQUEST);
		HttpServletResponse response = (HttpServletResponse) invocation.getInvocationContext().get(StrutsStatics.HTTP_RESPONSE);
		try {
			return invocation.invoke();
	    } catch (AuthenticationException ex) {
	    	return handleException(request, response, ex);
	    } catch (AccessDeniedException ex) {
	    	return handleException(request, response, ex);
	    } catch (ServletException ex) {
	    	if (ex.getRootCause() instanceof AuthenticationException
	    			|| ex.getRootCause() instanceof AccessDeniedException) {
	    		return handleException(request, response, (AcegiSecurityException) ex.getRootCause());
	    	} else {
	    		throw ex;
	    	}
	    } catch (IOException ex) {
	    	throw ex;
	    }
	}

    protected String handleException(HttpServletRequest request, HttpServletResponse response, AcegiSecurityException exception) throws IOException, ServletException {
        if (exception instanceof AuthenticationException) {
            return sendStartAuthentication(request, response, (AuthenticationException) exception);
        }
        if (exception instanceof AccessDeniedException) {
            if (authenticationTrustResolver.isAnonymous(SecurityContextHolder.getContext().getAuthentication())) {
                return sendStartAuthentication(request, response, new InsufficientAuthenticationException("Full authentication is required to access this resource"));
            }
        }
        throw exception;
    }

    protected String sendStartAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException reason) throws ServletException, IOException {

        SavedRequest savedRequest = new SavedRequest(request, portResolver);
        request.getSession().setAttribute(AbstractProcessingFilter.ACEGI_SAVED_REQUEST_KEY, savedRequest);
        SecurityContextHolder.getContext().setAuthentication(null);

        authenticationEntryPoint.commence(request, response, reason);
        return Action.NONE;
    }

    // =====================================================
	// Gettings And Settings
	// -----------------------------------------------------
    public void setAuthenticationTrustResolver(AuthenticationTrustResolver authenticationTrustResolver) {
        this.authenticationTrustResolver = authenticationTrustResolver;
    }

    public void setPortResolver(PortResolver portResolver) {
        this.portResolver = portResolver;
    }

	public AuthenticationEntryPoint getAuthenticationEntryPoint() {
		return authenticationEntryPoint;
	}

	public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

    public AuthenticationTrustResolver getAuthenticationTrustResolver() {
        return authenticationTrustResolver;
    }

    public PortResolver getPortResolver() {
        return portResolver;
    }
}
