/**
 * Created on 2007-7-23
 * Created by Sunteya
 */
package com.sunteya.flyer.validation.validator;

import java.util.ArrayList;
import java.util.List;

import com.sunteya.flyer.validation.Validator;
import com.sunteya.flyer.validation.ValidationContext;
import com.sunteya.flyer.validation.ValidatorProvider;
import com.sunteya.flyer.validation.impl.NestedValidationContext;
import com.sunteya.flyer.validation.impl.NestedValidatorProvider;

/**
 * @author Sunteya
 *
 */
public class NestedFieldValidator implements Validator {

	private Validator fieldValidator;
	private String prefix;

	public NestedFieldValidator(Validator fieldValidator, String prefix) {
		this.fieldValidator = fieldValidator;
		this.prefix = prefix;
	}

	public String fullFieldName(String path) {
		return prefix + path;
	}

	public String getPath() {
		return fullFieldName(fieldValidator.getPath());
	}

	public String[] getRelativePaths() {
		List<String> answer = new ArrayList<String>();
		for (String relative : fieldValidator.getRelativePaths()) {
			answer.add(fullFieldName(relative));
		}

		return answer.toArray(new String[0]);
	}

	public boolean isReady(ValidationContext context) {
		return fieldValidator.isReady(new NestedValidationContext(context, prefix));
	}

	public boolean isRequired(ValidatorProvider provider) {
		return fieldValidator.isRequired(new NestedValidatorProvider(provider, prefix));
	}

	public void validate(ValidationContext context) {
		fieldValidator.validate(new NestedValidationContext(context, prefix));
	}
}
