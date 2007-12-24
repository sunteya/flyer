/**
 * Created on 2007-7-20
 * Created by Sunteya
 */
package com.sunteya.flyer.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author Sunteya
 *
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface DomainModel {
	String property() default "";
}
