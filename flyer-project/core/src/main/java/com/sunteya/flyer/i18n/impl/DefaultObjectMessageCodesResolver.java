/**
 * Created on 2007-3-28
 * Created by Sunteya
 */
package com.sunteya.flyer.i18n.impl;

import java.util.ArrayList;
import java.util.List;

import com.sunteya.flyer.i18n.MessageCode;

/**
 * @author Sunteya
 *
 */
public class DefaultObjectMessageCodesResolver extends GenericMessageCodeResolver<Object> {

	@Override
	protected MessageCode resolveObject(Object obj) {
		List<String> codes = new ArrayList<String>();
		codes.add(obj.getClass().getName());
		codes.add(obj.getClass().getSimpleName());
		return new MessageCode(codes);
	}
}
