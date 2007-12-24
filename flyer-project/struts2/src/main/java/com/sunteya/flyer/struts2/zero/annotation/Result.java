/**
 * Created on 2007-9-21
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.zero.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.opensymphony.xwork2.Action;

/**
 * @author Sunteya
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Result {

    String name() default Action.SUCCESS;

    String location();

    String type() default "";

    String method() default "execute";

    String[] params() default {};

    String action() default "";
}
