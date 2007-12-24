/**
 * Created on 2006-12-27
 * Created by Sunteya
 */
package com.sunteya.flyer.ibatis.type;

import java.io.Serializable;
import java.sql.SQLException;

import org.apache.commons.lang.SerializationUtils;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

/**
 *
 * @author Sunteya
 * @email zhunm@dep5.com
 */
public class SerializableHandler implements TypeHandlerCallback {

	public Object getResult(ResultGetter getter) throws SQLException {
		return SerializationUtils.deserialize(getter.getBytes());
	}

	public void setParameter(ParameterSetter setter, Object parameter) throws SQLException {
		Serializable data = (Serializable) parameter;
		setter.setBytes(SerializationUtils.serialize(data));
	}

	public Object valueOf(String s) {
		return s;
	}
}
