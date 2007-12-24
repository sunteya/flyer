/**
 * Created on 2007-8-16
 * Created by Sunteya
 */
package com.sunteya.flyer.hibernate.usertype;

import org.hibernate.type.NullableType;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

/**
 * @author Sunteya
 *
 */
public abstract class NullableTypeSupport implements UserType, ParameterizedType {

	protected NullableType type;

	public int[] sqlTypes() {
		return new int[] { type.sqlType() };
	}

	public boolean isMutable() {
		return type.isMutable();
	}

}