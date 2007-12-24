/**
 * Created on 2007-9-21
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.zero.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Sunteya
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Routing {
	String action() default "";
	String controller() default "";
	boolean combine() default true;
}