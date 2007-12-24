/**
 * Created on 2007-4-5
 * Created by Sunteya
 */
package com.sunteya.flyer.i18n.impl;

import com.sunteya.flyer.i18n.MessageCode;
import com.sunteya.flyer.i18n.MessageCodeResolver;

/**
 * @author Sunteya
 *
 */
public class GroupMessageCodeResolver implements MessageCodeResolver {

	private MessageCodeResolver[] resolvers = new MessageCodeResolver[0];

	public MessageCode resolveMessageCode(Object obj) {
		for (MessageCodeResolver resolver : getResolvers()) {
			MessageCode code = resolver.resolveMessageCode(obj);
			if(code != null) {
				return code;
			}
		}

		return null;
	}

	public MessageCodeResolver[] getResolvers() {
		return resolvers;
	}

	public void setResolvers(MessageCodeResolver[] resolvers) {
		this.resolvers = resolvers;
	}
}
