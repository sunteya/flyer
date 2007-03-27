/**
 * Created on 2007-4-5
 * Created by Sunteya
 */
package com.sunteya.flyer.i18n.impl;

import org.apache.commons.lang.ArrayUtils;

import com.sunteya.flyer.i18n.MessageCodesResolver;

/**
 * @author Sunteya
 *
 */
public class GroupMessageCodesResolver implements MessageCodesResolver {

	private MessageCodesResolver[] resolvers = new MessageCodesResolver[0];

	public String[] resolve(Object obj) {
		for (MessageCodesResolver resolver : getResolvers()) {
			String[] codes = resolver.resolve(obj);
			if(!ArrayUtils.isEmpty(codes)) {
				return codes;
			}
		}

		return null;
	}

	public MessageCodesResolver[] getResolvers() {
		return resolvers;
	}

	public void setResolvers(MessageCodesResolver[] resolvers) {
		this.resolvers = resolvers;
	}
}
