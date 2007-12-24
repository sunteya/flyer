/**
 * Created on 2007-6-27
 * Created by Sunteya
 */
package com.sunteya.flyer.validation.validator.impl;

import com.sunteya.flyer.validation.ErrorCodesResolver;


/**
 * @author Sunteya
 *
 */
public class SimpleNameErrorCodesResolver implements ErrorCodesResolver {

	public String resolveErrorCode(Object cause) {
		return cause.getClass().getSimpleName();
	}

}
