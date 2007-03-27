/**
 * Created on 2007-3-16
 * Created by Sunteya
 */
package com.sunteya.flyer.test.easymock;

/**
 * @author Sunteya
 *
 */
public class AbsrtactRecorder {

	private Object mock;

	public Object getMock() {
		return mock;
	}

	public AbsrtactRecorder(Object mock) {
		this.mock = mock;
	}
}
