/**
 * Created on 2007-1-24
 * Created by Sunteya
 */
package com.sunteya.flyer.springmvc.view.freemarker;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import freemarker.template.Configuration;

/**
 * @author Sunteya
 * 
 */
public class FileTypeFreeMarkerViewResolver extends FreeMarkerViewResolver {

	private Configuration configuration;
	private String extension;

	@Override
	protected AbstractUrlBasedView buildView(String source) throws Exception {
		String viewName = source;
		if (!source.endsWith("." + getExtension())) {
			viewName = viewName + "." + extension;
		}

		FreeMarkerView view = (FreeMarkerView) super.buildView(viewName);

		if (isExistTemplate(view.getUrl())) {
			view.setConfiguration(getConfiguration());
			return view;
		}

		return null;
	}

	protected boolean isExistTemplate(String url) {
		try {
			getConfiguration().getTemplate(url);
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
		}

		return true;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
}
