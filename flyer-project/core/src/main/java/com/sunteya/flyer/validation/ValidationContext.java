/**
 * Created on 2007-6-27
 * Created by Sunteya
 */
package com.sunteya.flyer.validation;

import com.sunteya.flyer.i18n.MessageCode;
import com.sunteya.flyer.i18n.MessageProvider;


/**
 * @author Sunteya
 *
 */
public interface ValidationContext extends  FieldValidationAware, MessageProvider, PathMessageCodeResolver {
	Object getValue(String path);
	String getErrorCode(Object cause);
	MessageCode resolveMessageCode(Object obj);
}