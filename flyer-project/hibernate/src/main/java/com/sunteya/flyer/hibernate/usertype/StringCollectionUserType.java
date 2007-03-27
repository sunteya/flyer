/**
 * Created on 2007-4-16
 * Created by Sunteya
 */
package com.sunteya.flyer.hibernate.usertype;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 * @author Sunteya
 *
 */
public abstract class StringCollectionUserType implements UserType {

	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws SQLException {
		return textToObject(rs.getString(names[0]));
	}

	@SuppressWarnings("unchecked")
	public void nullSafeSet(PreparedStatement st, Object value, int index) throws SQLException {
		String text = objectToText((Collection) value);
		st.setString(index, text);
	}

	public int[] sqlTypes() {
		return new int[] { Types.VARCHAR };
	}

	public boolean isMutable() {
		return true;
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

		String[] elements = StringUtils.split(string, ", ");
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

		return StringUtils.join(name.iterator(), ", ");
	}

	protected abstract Collection<String> newCollection();
}