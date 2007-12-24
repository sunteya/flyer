/**
 * Created on 2007-8-8
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.acegi;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.sunteya.flyer.acegi.web.PathEntryHolder;
import com.sunteya.flyer.acegi.web.PathRoutingProvider;

/**
 * @author Sunteya
 *
 */
public class OncePerRequestPathRoutingProvider implements PathRoutingProvider {

	private static final String REQUEST_KEY = OncePerRequestPathRoutingProvider.class.getName() + ".key_";
	private PathRoutingProvider delegate;

	@SuppressWarnings("unchecked")
	public List<PathEntryHolder> getEntries() {
		HttpServletRequest request = ServletActionContext.getRequest();
		if(request == null) {
			return getDelegate().getEntries();
		}
		List<PathEntryHolder> answer = (List<PathEntryHolder>) request.getAttribute(REQUEST_KEY);
		if(answer == null) {
			answer = getDelegate().getEntries();
			request.setAttribute(REQUEST_KEY, answer);
		}

		return answer;
	}

	public PathRoutingProvider getDelegate() {
		return delegate;
	}

	public void setDelegate(PathRoutingProvider delegate) {
		this.delegate = delegate;
	}
}
