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

/**
 * @author Sunteya
 *
 */
public class EnumIntegerUserType extends AbstractEnumUserType {

	private final int ORDINAL_FOR_NULL = -1;

	public int[] sqlTypes() {
		return new int[] { Types.INTEGER };
	}

	public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner)
			throws HibernateException, SQLException {
		int ordinal = resultSet.getInt(names[0]);
		if (resultSet.wasNull() || (ordinal == ORDINAL_FOR_NULL)) {
			return null;
		} else {
			return getEnumClass().getEnumConstants()[ordinal];
		}
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index)
			throws HibernateException, SQLException {
		if (value == null) {
			st.setInt(index, ORDINAL_FOR_NULL);
		} else {
			st.setInt(index, ((Enum<?>) value).ordinal());
		}
	}

	public Object fromXMLString(String xmlValue) {
		int ordinal = Integer.parseInt(xmlValue);
		return getEnumClass().getEnumConstants()[ordinal];
	}

	public String objectToSQLString(Object value) {
		return String.valueOf(((Enum<?>) value).ordinal());
	}

	public String toXMLString(Object value) {
		return String.valueOf(((Enum<?>) value).ordinal());
	}
}
