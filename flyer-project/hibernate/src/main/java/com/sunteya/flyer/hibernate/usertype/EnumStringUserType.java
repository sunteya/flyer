/**
 * Created on 2006-12-21
 * Created by Sunteya
 */
package com.sunteya.flyer.hibernate.usertype;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.usertype.ParameterizedType;

/**
 * @author Sunteya
 *
 */
public class EnumStringUserType extends AbstractEnumUserType implements
		ParameterizedType {

	public int[] sqlTypes() {
		return new int[] { Types.VARCHAR };
	}

	@SuppressWarnings("unchecked")
	public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner)
			throws HibernateException, SQLException {
		String name = resultSet.getString(names[0]);
		return resultSet.wasNull() ? null : Enum.valueOf(getEnumClass(), name);
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index)
			throws HibernateException, SQLException {
		if (value == null) {
			st.setNull(index, Types.VARCHAR);
		} else {
			st.setString(index, ((Enum<?>) value).name());
		}
	}

	@SuppressWarnings("unchecked")
	public Object fromXMLString(String xmlValue) {
		return Enum.valueOf(getEnumClass(), xmlValue);
	}

	public String objectToSQLString(Object value) {
		return '\'' + ((Enum<?>) value).name() + '\'';
	}

	public String toXMLString(Object value) {
		return ((Enum<?>) value).name();
	}
}
