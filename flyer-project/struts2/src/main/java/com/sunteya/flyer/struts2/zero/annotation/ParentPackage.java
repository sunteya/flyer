/**
 * Created on 2007-9-21
 * Created by Sunteya
 */
package com.sunteya.flyer.struts2.zero.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Sunteya
 *
 */
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ParentPackage {
    String value();
}
