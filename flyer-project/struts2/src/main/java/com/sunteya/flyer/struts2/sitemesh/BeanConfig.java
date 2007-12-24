/**
 * Created on 2008-1-3
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.sitemesh;

import java.util.Enumeration;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

import com.opensymphony.module.sitemesh.Config;

/**
 * @author Sunteya
 *
 */
public class BeanConfig extends Config implements ServletContextAware {

	private String configFile;
	private ServletContext servletContext;

	public BeanConfig() {
		super(new FilterConfig() {
			public String getFilterName() {
				return null;
			}

			public String getInitParameter(String name) {
				return null;
			}

			@SuppressWarnings("unchecked")
			public Enumeration getInitParameterNames() {
				return null;
			}

			public ServletContext getServletContext() {
				return null;
			}
		});
	}

	@Override
	public String getConfigFile() {
		return this.configFile;
	}
	
	@Override
	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}
