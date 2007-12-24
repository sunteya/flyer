/**
 * Created on 2007-11-28
 * Created by Sunteya
 */
package com.sunteya.flyer.validation.validator.impl;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.sunteya.flyer.validation.validator.RequiredValistor;

/**
 * @author Sunteya
 *
 */
public class NotBlankValidator extends RequiredValistor {

	public NotBlankValidator() {
	}

	public NotBlankValidator(String path) {
		setPath(path);
	}

	@Override
	public boolean isVaild(Object value) {
		return StringUtils.isNotBlank(ObjectUtils.toString(value));
	}
}
