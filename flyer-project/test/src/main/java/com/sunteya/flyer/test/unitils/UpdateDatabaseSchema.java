/**
 * Created on 2007-3-23
 * Created by Sunteya
 */
package com.sunteya.flyer.test.unitils;

import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author Sunteya
 *
 */
@Target(ElementType.TYPE)
@Retention(RUNTIME)
@Inherited
public @interface UpdateDatabaseSchema {
}
