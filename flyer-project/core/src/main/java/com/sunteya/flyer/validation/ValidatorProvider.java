/**
 * Created on 2007-7-23
 * Created by Sunteya
 */
package com.sunteya.flyer.validation;

import java.util.List;

/**
 * @author Sunteya
 *
 */
public interface ValidatorProvider {
	List<Validator> findAllByPath(String fieldPath);
	boolean hasRequiredValidator(String path);
}