/**
 * Created on 2007-4-16
 * Created by Sunteya
 */
package com.sunteya.flyer.hibernate.usertype;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.type.NullableType;
import org.hibernate.type.StringType;

import com.sunteya.flyer.util.CsvUtils;

/**
 * @author Sunteya
 *
 */
public abstract class CsvCollectionUserType extends NullableTypeSupport  {

	public CsvCollectionUserType() {
		type = new StringType();
	}

	@SuppressWarnings("unchecked")
	public void setParameterValues(Properties parameters) {
		if(parameters == null) {
			return;
		}

		if(parameters.containsKey("type")) {
			String typeClass = parameters.getProperty("type");
			try {
				type = (NullableType) Class.forName(typeClass).newInstance();
			} catch (Exception cnfe) {
				throw new HibernateException("type class not found", cnfe);
			}
		}
	}

	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws SQLException {
		return textToObject((String) type.get(rs, names[0]));
	}

	@SuppressWarnings("unchecked")
	public void nullSafeSet(PreparedStatement st, Object value, int index) throws SQLException {
		String text = objectToText((Collection) value);
		type.set(st, text, index);
	}

	public Object replace(Object original, Object target, Object owner) {
		return original;
	}

	public Object assemble(Serializable cached, Object owner) {
		return cached;
	}

	@SuppressWarnings("unchecked")
	public Object deepCopy(Object value) {
		Collection<String> answer = newCollection();
		answer.addAll((Collection<String>) value);
		return answer;
	}

	public Serializable disassemble(Object value) {
		return (Serializable) value;
	}

	@SuppressWarnings("unchecked")
	public boolean equals(Object x, Object y) throws HibernateException {
		String a = objectToText((Collection<String>) x);
		String b = objectToText((Collection<String>) y);
		return ObjectUtils.equals(a, b);
	}

	@SuppressWarnings("unchecked")
	public int hashCode(Object x) throws HibernateException {
		return ObjectUtils.hashCode(objectToText((Collection<String>) x));
	}

	protected Collection<String> textToObject(String string) {
		if (StringUtils.isBlank(string)) {
			return newCollection();
		}

		String[] elements = CsvUtils.decodeLine(string);
		Collection<String> answer = newCollection();
		for (String element : elements) {
			answer.add(element);
		}

		return answer;
	}

	protected String objectToText(Collection<String> name) {
		if (CollectionUtils.isEmpty(name)) {
			return null;
		}

		return CsvUtils.encodeLine(name);
	}

	protected abstract Collection<String> newCollection();
}