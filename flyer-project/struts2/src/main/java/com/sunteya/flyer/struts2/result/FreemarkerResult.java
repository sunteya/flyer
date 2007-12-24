/**
 * Created on 2008-1-3
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.result;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.util.Locale;

import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.apache.struts2.views.util.ResourceUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.ValueStack;

import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;

/**
 * @author Sunteya
 *
 */
public class FreemarkerResult extends AbstractResult implements ResultExistable {

	private static final long serialVersionUID = 5280665396369304045L;
	private FreemarkerManager freemarkerManager;
	private String contentType = "text/html";
	private String encoding = "UTF-8";
	
	
	protected String getFinalLocation() {
		String target = getLocation();
		
		if (!target.startsWith("/")) {
	        target = ResourceUtil.getResourceBase(getRequest()) + "/" + target;
	    }
		
		return target;
	}
	
	public boolean exists(ActionContext actionContext) {
		prepare(actionContext.getActionInvocation());
		
		try {
			getTemplate(getFinalLocation());
		} catch (FileNotFoundException e) {
			return false;
		} catch (Exception e) {
			return true;
		}
		return true;
	}
	
	public void execute() throws TemplateModelException, TemplateException, IOException {
        preTemplateProcess();
    	getTemplate(getFinalLocation()).process(createModel(), getWriter());
    }
	
    protected void preTemplateProcess() {
    	getResponse().setCharacterEncoding(getEncoding());
    	getResponse().setContentType(getContentType());
    }
	
	protected SimpleHash createModel() throws TemplateException {
		return freemarkerManager.buildTemplateModel(getStack(), getAction(), getServletContext(), getRequest(), getResponse(), getConfiguration().getObjectWrapper());
	}

	protected ValueStack getStack() {
		return getActionInvocation().getStack();
	}

	protected Writer getWriter() throws IOException {
		return getResponse().getWriter();
	}

	protected Template getTemplate(String location) throws IOException, TemplateException {
				return getConfiguration().getTemplate(location, deduceLocale());
			}

	protected Configuration getConfiguration() throws TemplateException {
		return freemarkerManager.getConfiguration(getServletContext());
	}

	protected Locale deduceLocale() throws TemplateException {
		Object action = getAction();
	    return action instanceof LocaleProvider ? 
	    			((LocaleProvider) action).getLocale() : getConfiguration().getLocale();
	}

	private Object getAction() {
		return getActionInvocation().getAction();
	}

	public FreemarkerManager getFreemarkerManager() {
		return freemarkerManager;
	}

	@Inject
	public void setFreemarkerManager(FreemarkerManager freemarkerManager) {
		this.freemarkerManager = freemarkerManager;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
}