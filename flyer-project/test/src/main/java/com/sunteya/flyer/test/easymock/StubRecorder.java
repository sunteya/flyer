/**
 * Created on 2007-3-16
 * Created by Sunteya
 */
package com.sunteya.flyer.test.easymock;

import static org.easymock.EasyMock.*;

import org.apache.commons.beanutils.MethodUtils;
import org.easymock.EasyMock;


/**
 * @author Sunteya
 *
 */
public class StubRecorder extends AbsrtactRecorder {

	public StubRecorder(Object mock) {
		super(mock);
	}

	public StubRecorder record(String methodName, Object answer, Object... params) {
		try {
			MethodUtils.invokeMethod(getMock(), methodName, params);
			expectLastCall().andStubReturn(answer);
			return this;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public StubRecorder reset() {
		EasyMock.reset(getMock());
		return this;
	}

	public StubRecorder replay() {
		EasyMock.replay(getMock());
		return this;
	}
}
