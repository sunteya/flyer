/**
 * Created on 2007-3-16
 * Created by Sunteya
 */
package com.sunteya.flyer.test.easymock;

import static org.easymock.EasyMock.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.sunteya.flyer.dao.GenericDao;
import com.sunteya.flyer.domain.Entity;

/**
 * @author Sunteya
 *
 */
public class DaoStubRecorder extends StubRecorder {

	private StubRecorder parent;

	public DaoStubRecorder(Object mock) {
		super(mock);
		parent = new StubRecorder(mock);
	}

	public DaoStubRecorder recordGet(Entity entity) {
		GenericDao<?> dao = (GenericDao<?>) getMock();
		expect(dao.get(entity.getIdentityCode())).andStubReturn(entity);

		return this;
	}


	public DaoStubRecorder recordBean(String methodName, Object bean, String... props) {
		try {
			List<Object> params = new ArrayList<Object>();
			for (String prop : props) {
				prop = prop.substring(0, 1).toLowerCase() + prop.substring(1);
				Object param = PropertyUtils.getProperty(bean, prop);
				params.add(param);
			}

			parent.record(methodName, bean, params.toArray());
			return this;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public DaoStubRecorder record(String methodName, Object answer, Object... params) {
		parent.record(methodName, answer, params);
		return this;
	}

	public DaoStubRecorder replay() {
		parent.replay();
		return this;
	}

	public DaoStubRecorder reset() {
		parent.reset();
		return this;
	}
}
