/**
 * Created on 2006-12-21
 * Created by Sunteya
 */
package com.sunteya.flyer.hibernate.usertype;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.usertype.EnhancedUserType;
import org.hibernate.usertype.ParameterizedType;

/**
 * @author Sunteya
 *
 */
public abstract class AbstractEnumUserType implements EnhancedUserType, ParameterizedType {

	@SuppressWarnings("unchecked")
	private Class<? extends Enum> enumClass;

	public void setEnumClass(Class<? extends Enum<?>> enumClass) {
		this.enumClass = enumClass;
	}

	@SuppressWarnings("unchecked")
	public Class<? extends Enum> getEnumClass() {
		return enumClass;
	}

	@SuppressWarnings("unchecked")
	public void setParameterValues(Properties parameters) {
		String enumClassName = parameters.getProperty("enumClassName").trim();
		try {
			Class enumClass = Class.forName(enumClassName);
			setEnumClass(enumClass);
		} catch (ClassNotFoundException cnfe) {
			throw new HibernateException("Enum class not found", cnfe);
		}
	}

	public boolean isMutable() {
		return false;
	}

	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		return original;
	}

	@SuppressWarnings("unchecked")
	public Class returnedClass() {
		return getEnumClass();
	}

	public Object assemble(Serializable cached, Object owner)
			throws HibernateException {
		return cached;
	}

	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	public Serializable disassemble(Object value) throws HibernateException {
		return (Enum<?>) value;
	}

	public boolean equals(Object x, Object y) throws HibernateException {
		return x == y;
	}

	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}
}