/**
 * Created on 2008-1-3
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.sitemesh;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.opensymphony.module.sitemesh.Decorator;
import com.opensymphony.module.sitemesh.Page;
import com.opensymphony.module.sitemesh.filter.Buffer;

import freemarker.template.TemplateException;

/**
 * @author Sunteya
 *
 */
public class NestFreemarkerResult extends SitemeshFreemakerResult {

	private static final long serialVersionUID = 2051486305144913753L;
	
	Decorator getNextDecorator(Page page, Decorator decorator) {
		final String requestURI = decorator.getPage();

		if(getFactory().isPathExcluded(requestURI)) {
			return null;
		}

		HttpServletRequest requestWrapper = new HttpServletRequestWrapper(getRequest()) {
			@Override
			public String getServletPath() {
				return requestURI;
			}
		};

		Decorator nextDecorator = getDecorator(requestWrapper, page);
		if(decorator.getName().equals(nextDecorator.getName())) {
			return null; //Nest skip
		}

		return nextDecorator;
	}
	
	protected void applyDecorator(Page paramPage, Decorator paramDecorator) throws IOException, TemplateException {
		Page previousPage = paramPage;
		Decorator currentDecorator = paramDecorator;
	
		while(true) {
			Page currentPage = parsePage(previousPage, currentDecorator);
			Decorator nextDecorator = getNextDecorator(currentPage, currentDecorator);
	
			if(nextDecorator != null) {
				previousPage = currentPage;
				currentDecorator = nextDecorator;
				continue;
			}
	
			currentPage.writePage(getWriter());
			return;
		}
	}
	
	Page parsePage(Page page, Decorator decorator) throws IOException, TemplateException {
		Buffer buffer = new Buffer(getPageParser(), getEncoding());
		doRander(page, decorator, buffer.getWriter());
		return buffer.parse();
	}

}
