/**
 * Created on 2007-6-27
 * Created by Sunteya
 */
package com.sunteya.flyer.validation;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Sunteya
 *
 */
public class ValidatorConfiguration implements ValidatorAppendable, ValidatorProvider {

	private List<Validator> validators = new ArrayList<Validator>();
	private boolean runned;

	public void validateAllOnce(ValidationContext context) {
		if(!isRunned()) {
			validateAll(context, true);
			validateAll(context, false);
			setRunned(true);
		}
	}

	public void validateAll(ValidationContext context, boolean required) {
		for (String fieldName : getValidatorPaths()) {
			for (Validator validator : findAllByPathAndRequired(fieldName, required)) {
				if(validator.isReady(context)) {
					validator.validate(context);
				}
			}
		}
	}

	public List<String> getValidatorPaths() {
		List<String> answer = new ArrayList<String>();
		for (Validator validator : getValidators()) {
			if(!answer.contains(validator.getPath())) {
				answer.add(validator.getPath());
			}
		}

		return answer;
	}

	public void addValidator(Validator validator) {
		validators.add(validator);
	}

	public boolean hasRequiredValidator(String path) {
		return !findAllByPathAndRequired(path, true).isEmpty();
	}

	public List<Validator> findAllByPathAndRequired(String path, boolean required) {
		List<Validator> answer = new ArrayList<Validator>();
		for (Validator validator : findAllByPath(path)) {
			if(validator.isRequired(this) == required) {
				answer.add(validator);
			}
		}

		return answer;
	}

	public List<Validator> findAllByPath(String path) {
		List<Validator> answer = new ArrayList<Validator>();
		for (Validator validator : getValidators()) {
			if(validator.getPath().equals(path)) {
				answer.add(validator);
			}
		}

		return answer;
	}

	// =====================================================
	// Gettings And Settings
	// -----------------------------------------------------
	public List<Validator> getValidators() {
		return validators;
	}

	public void setValidators(List<Validator> validators) {
		this.validators = validators;
	}

	public boolean isRunned() {
		return runned;
	}

	public void setRunned(boolean runned) {
		this.runned = runned;
	}
}