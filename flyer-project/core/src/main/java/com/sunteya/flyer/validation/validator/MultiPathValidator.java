/**
 * Created on 2007-11-28
 * Created by Sunteya
 */
package com.sunteya.flyer.validation.validator;

import org.apache.commons.lang.ArrayUtils;

import com.sunteya.flyer.validation.ValidatorProvider;

/**
 * @author Sunteya
 *
 */
public abstract class MultiPathValidator extends AbstractValidator {

	private String[] otherPaths;

	public MultiPathValidator() {
	}

	public boolean isRequired(ValidatorProvider provider) {
		for (String otherPath : getOtherPaths()) {
			if(provider.hasRequiredValidator(otherPath)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public String[] getRelativePaths() {
		return (String[]) ArrayUtils.add(getOtherPaths(), getPath());
	}

	public String getOtherPath() {
		return ArrayUtils.isEmpty(getOtherPaths()) ? null : getOtherPaths()[0];
	}

	public void setOtherPath(String otherPath) {
		setOtherPaths(new String[] {otherPath});
	}

	public String[] getOtherPaths() {
		return otherPaths;
	}

	public void setOtherPaths(String[] otherPath) {
		this.otherPaths = otherPath;
	}
}