/**
 * Created on 2007-6-27
 * Created by Sunteya
 */
package com.sunteya.flyer.validation;


/**
 * @author Sunteya
 *
 */
public interface Validator {

	void validate(ValidationContext context);

	String getPath();

	boolean isReady(ValidationContext context);

	boolean isRequired(ValidatorProvider provider);

	String[] getRelativePaths();
}