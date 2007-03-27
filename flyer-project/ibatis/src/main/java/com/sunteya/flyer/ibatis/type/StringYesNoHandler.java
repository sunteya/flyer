/**
 * Created on 2007-05-08
 * Created by Sunteya
 */
package com.sunteya.flyer.ibatis.type;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.extensions.ParameterSetter;
import com.ibatis.sqlmap.client.extensions.ResultGetter;
import com.ibatis.sqlmap.client.extensions.TypeHandlerCallback;

/**
 * @author Sunteya
 *
 */
public class StringYesNoHandler implements TypeHandlerCallback {

	public Object getResult(ResultGetter getter) throws SQLException {
		String val = getter.getString();
		if("Y".equals(val)) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	public void setParameter(ParameterSetter setter, Object parameter)
			throws SQLException {
		Boolean val = (Boolean) parameter;
		if(val.booleanValue()) {
			setter.setString("Y");
		} else {
			setter.setString("N");
		}
	}

	public Object valueOf(String s) {
		return s;
	}

}
