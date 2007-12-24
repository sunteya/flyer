/**
 * Created on 2007-11-27
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.action;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.util.TextParseUtil;
import com.opensymphony.xwork2.util.ValueStack;
import com.sunteya.flyer.i18n.MessageCode;
import com.sunteya.flyer.i18n.MessageCodeResolver;
import com.sunteya.flyer.i18n.MessageProvider;

/**
 * @author Sunteya
 *
 */
public class I18nActionSupport extends ActionSupport implements MessageProvider, LocaleProvider {

	private MessageSource messageSource;
	private MessageCodeResolver messageCodeResolver;

	public MessageCode resolveMessageCode(Object obj) {
		return messageCodeResolver.resolveMessageCode(obj);
	}

	public Locale getLocale() {
		return ActionContext.getContext().getLocale();
	}

	protected ValueStack getValueStack() {
		return ActionContext.getContext().getValueStack();
	}

	public String getMessage(MessageCode messageCode) {
		String[] codes = messageCode.getCodes().toArray(new String[0]);
		DefaultMessageSourceResolvable msr = new DefaultMessageSourceResolvable(codes, messageCode.getDefaultMessage());
		String messageExpress = getMessageSource().getMessage(msr, getLocale());

		ValueStack stack = getValueStack();
		stack.push(this);
		for (Object ctx : messageCode.getContexts()) {
			stack.push(ctx);
		}

		try {
			return TextParseUtil.translateVariables(messageExpress, stack);
		} finally {
			for (int i = 0; i < messageCode.getContexts().size(); i++) {
				stack.pop();
			}
			stack.pop();
		}
	}

	public MessageCodeResolver getMessageCodeResolver() {
		return messageCodeResolver;
	}

	public void setMessageCodeResolver(MessageCodeResolver messageCodeResolver) {
		this.messageCodeResolver = messageCodeResolver;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
