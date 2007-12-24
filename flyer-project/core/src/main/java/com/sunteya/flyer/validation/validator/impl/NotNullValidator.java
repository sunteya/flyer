/**
 * Created on 2007-6-26
 * Created by Sunteya
 */
package com.sunteya.flyer.validation.validator.impl;

import java.util.ArrayList;
import java.util.List;

import com.sunteya.flyer.validation.validator.RequiredValistor;



/**
 * @author Sunteya
 *
 */
public class NotNullValidator extends RequiredValistor  {

	public NotNullValidator() {
	}

	public NotNullValidator(String path) {
		setPath(path);
	}

	@Override
	public boolean isVaild(Object value) {
		return value != null;
	}

	public static List<NotNullValidator> forFields(String...fieldNames) {
		List<NotNullValidator> answer = new ArrayList<NotNullValidator>();
		for (String fieldName : fieldNames) {
			answer.add(new NotNullValidator(fieldName));
		}

		return answer;
	}
}
