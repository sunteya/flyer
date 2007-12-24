/**
 * Created on 2007-7-20
 * Created by Sunteya
 */
package com.sunteya.flyer.acegi.web;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author Sunteya
 *
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface MappingTo {
	String pkg() default "";
	String controller() default "";
	String action() default "";
	String format() default "";
}
