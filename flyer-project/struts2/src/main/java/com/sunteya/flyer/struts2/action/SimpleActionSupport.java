/**
 * Created on 2007-11-27
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.action;

import java.lang.reflect.Field;
import java.util.Formatter;

import org.apache.commons.lang.StringUtils;

import com.sunteya.commons.util.AnnotationUtility;
import com.sunteya.commons.util.ReferenceUtility;
import com.sunteya.flyer.annotation.DomainModel;
import com.sunteya.flyer.validation.builder.ValidatorBuilder;
import com.sunteya.flyer.validation.builder.ValidatorInitializable;
import com.sunteya.flyer.validation.impl.NestedValidatorAppender;


/**
 * @author Sunteya
 *
 */
public abstract class SimpleActionSupport extends ValidateActionSupport {

	public static final String FLASH_NOTICE = "notice";
	public static final String FLASH_WARNING = "warning";
	public static final String FLASH_ERROR = "warning";

	public void putFlash(String key, String format, Object...args) {
		Formatter formatter = new Formatter();
		formatter.format(format, args);
		flash.put(key, formatter.toString());
	}

	// =====================================================
	// InitBinderAndValidator
	// -----------------------------------------------------
	@Override
	public void initBinderAndValidator() {
		ValidatorBuilder builder = new ValidatorBuilder(validatorConfiguration);
		initDomainValidator(builder);
		doInitBinderAndValidator(builder);
	}

	private void initDomainValidator(ValidatorBuilder builder) {
		Field field = AnnotationUtility.getAnnotationField(this, DomainModel.class);
		if(field == null) {
			return;
		}

		String propertyName = field.getAnnotation(DomainModel.class).property();
		if(StringUtils.isEmpty(propertyName)) {
			propertyName = field.getName();
		}

		Object obj = ReferenceUtility.getValue(this, field);
		if(obj instanceof ValidatorInitializable) {
			ValidatorInitializable initializable = (ValidatorInitializable) obj;
			initializable.initValidator(new ValidatorBuilder(
				new NestedValidatorAppender(propertyName + ".", builder)
			));
		}
	}

	protected void doInitBinderAndValidator(ValidatorBuilder builder) {
	}

	// =====================================================
	// BindAndValidate
	// -----------------------------------------------------
	@Override
	public void bindAndValidate() throws Exception {
		doBindAndValidate();
		validateAllOnce();
	}

	protected void validateAllOnce() {
		validatorConfiguration.validateAllOnce(getValidationContext());
	}

	protected void doBindAndValidate() throws Exception {
	}
}