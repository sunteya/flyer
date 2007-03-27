/**
 * Created on 2007-3-28
 * Created by Sunteya
 */
package com.sunteya.flyer.web.service.impl.spring;

import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.context.support.MessageSourceAccessor;

import com.sunteya.flyer.i18n.MessageCodesResolver;
import com.sunteya.flyer.web.service.MessageService;

/**
 * @author Sunteya
 *
 */
public class MessageServiceImpl implements MessageService, MessageSourceAware {

	private MessageSource messageSource;

	private MessageCodesResolver messageCodesResolver;

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public void setMessageCodesResolver(MessageCodesResolver messageCodesResolver) {
		this.messageCodesResolver = messageCodesResolver;
	}

	private MessageSourceAccessor getMessageSourceAccessor() {
		return new MessageSourceAccessor(messageSource);
	}

	public String getMessage(Object key) {
		return getMessage(key, new Object[0]);
	}

	public String getMessage(Object key, String defaultMessage) {
		return getMessage(key, new Object[0], defaultMessage);
	}

	public String getMessage(Object key, Object[] args) {
		return getMessage(key, args, null);
	}

	public String getMessage(Object key, Object[] args, String defaultMessage) {
		//TODO
		String[] codes = messageCodesResolver.resolve(key);
		return getMessageSourceAccessor().getMessage(new DefaultMessageSourceResolvable(codes, args, defaultMessage));
	}

	public String getMessage(Object key, Map<String, ?> params) {
		return getMessage(key, params, null);
	}

	public String getMessage(Object key, Map<String, ?> params, String defaultMessage) {
		// TODO
		String[] codes = messageCodesResolver.resolve(key);
		return getMessageSourceAccessor().getMessage(new DefaultMessageSourceResolvable(codes, defaultMessage));
	}
}
