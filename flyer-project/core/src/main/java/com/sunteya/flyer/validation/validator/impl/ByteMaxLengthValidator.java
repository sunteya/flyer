package com.sunteya.flyer.validation.validator.impl;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.exception.NestableRuntimeException;

import com.sunteya.flyer.validation.validator.OptionalValidator;

public class ByteMaxLengthValidator extends OptionalValidator {

	private String charset;
	private int maxLength;

	public boolean isVaild(String value, int maxLength, String charsetName) {
		try {
			return value.getBytes(charsetName).length <= maxLength;
		} catch (UnsupportedEncodingException e) {
			throw new NestableRuntimeException(e);
		}
	}

	@Override
	protected boolean isVaildValue(Object value) {
		return isVaild(value.toString(), getMaxLength(), getCharset());
	}

	// =====================================================
	// Gettings And Settings
	// -----------------------------------------------------
	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
}
