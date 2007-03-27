/**
 * Created on 2007-3-28
 * Created by Sunteya
 */
package com.sunteya.flyer.i18n.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sunteya
 *
 */
public class DefaultObjectMessageCodesResolver extends GenericMessageCodesResolver<Object> {

	@Override
	protected String[] resolveObject(Object obj) {
		if(obj instanceof String) {
			return new String[] { obj.toString() };
		}

		List<String> answer = new ArrayList<String>();
		answer.add(obj.getClass().getName());
		answer.add(obj.getClass().getSimpleName());
		return answer.toArray(new String[0]);
	}
}
