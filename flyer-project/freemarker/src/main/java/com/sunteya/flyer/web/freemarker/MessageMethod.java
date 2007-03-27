/**
 * Created on 2006-11-20
 * Created by Sunteya
 */
package com.sunteya.flyer.web.freemarker;

import java.util.List;

import com.sunteya.flyer.web.service.MessageService;

import freemarker.core.Environment;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

/**
 * @author Sunteya
 *
 */
public class MessageMethod implements TemplateMethodModelEx {

	private MessageService messageService;

	@SuppressWarnings("unchecked")
	public Object exec(List arguments) throws TemplateModelException {
		Environment environment = Environment.getCurrentEnvironment();
		BeansWrapper wrapper = (BeansWrapper) environment.getObjectWrapper();
		Object key = wrapper.unwrap((TemplateModel) arguments.get(0));
		return messageService.getMessage(key);
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
}
