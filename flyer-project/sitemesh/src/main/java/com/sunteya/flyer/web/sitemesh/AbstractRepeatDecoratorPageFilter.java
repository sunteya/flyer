/**
 * Created on 2007-2-26
 * Created by Sunteya
 */
package com.sunteya.flyer.web.sitemesh;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.module.sitemesh.Decorator;
import com.opensymphony.module.sitemesh.Factory;
import com.opensymphony.module.sitemesh.Page;
import com.opensymphony.module.sitemesh.filter.PageResponseWrapper;
import com.opensymphony.module.sitemesh.util.Container;

/**
 * @author Sunteya
 *
 */
public abstract class AbstractRepeatDecoratorPageFilter extends UnicodePageFilter {

	@Override
	protected void applyDecorator(Page paramPage, Decorator paramDecorator, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			Page previousPage = paramPage;
			Decorator currentDecorator = paramDecorator;

			while(true) {
				Page currentPage = renderPage(previousPage, currentDecorator, request, response);
				Decorator nextDecorator = getNextDecorator(currentPage, currentDecorator, request);

				if(nextDecorator != null) {
					previousPage = currentPage;
					currentDecorator = nextDecorator;
					continue;
				}

				writeOriginal(request, response, currentPage);
				return;
			}

		}catch (Exception e) {
			throw new ServletException(e);
		}
	}

	private Decorator getNextDecorator(Page page, Decorator decorator, HttpServletRequest request) {
		final String requestURI = decorator.getPage();

		if(getFactory().isPathExcluded(requestURI)) {
			return null;
		}

		HttpServletRequest requestWrapper = new HttpServletRequestWrapper(request) {
			@Override
			public String getServletPath() {
				return requestURI;
			}
		};

		Decorator nextDecorator = getDecorator(page, requestWrapper);
		if(decorator.getName().equals(nextDecorator.getName())) {
			return null; //Nest skip
		}

		return nextDecorator;
	}

	private Decorator getDecorator(Page page, HttpServletRequest request) {
		Factory factory = getFactory();
		Decorator nextDecorator = factory.getDecoratorMapper().getDecorator(request, page);
		return nextDecorator;
	}

	private Page renderPage(Page page, Decorator decorator, HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {
        try {
            PageResponseWrapper pageResponse = new PageResponseWrapper(response, getFactory());
            pageResponse.setContentType(response.getContentType());

			doRander(page, decorator, request, pageResponse);
	        request.setAttribute(USING_STREAM, new Boolean(pageResponse.isUsingStream()));

	        return pageResponse.getPage();
        }catch (IllegalStateException e) {
	        // weblogic throws an IllegalStateException when an error page is served.
	        // it's ok to ignore this, however for all other containers it should be thrown
	        // properly.
	        if (Container.get() != Container.WEBLOGIC) throw e;
	        return null;
	    }
	}

	protected abstract void doRander(Page page, Decorator decorator, HttpServletRequest request, HttpServletResponse response) throws Exception;

}
