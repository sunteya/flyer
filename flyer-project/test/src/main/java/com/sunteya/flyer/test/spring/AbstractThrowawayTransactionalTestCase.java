/**
 * Created on 2006-11-29
 * Created by Sunteya
 */
package com.sunteya.flyer.test.spring;

import org.springframework.test.AbstractTransactionalSpringContextTests;

/**
 * @author Sunteya
 * 
 */
public abstract class AbstractThrowawayTransactionalTestCase extends
		AbstractTransactionalSpringContextTests {

	protected void commitAndStartNewTransaction() {
		setComplete();
		endTransaction();
		startNewTransaction();
	}

	private boolean dirty = false;

	protected void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	protected void reuse() {
		setDirty(false);
	}

	protected void throwawayOnTestEnd() {
		setDirty(true);
	}

	@Override
	protected final void onSetUpBeforeTransaction() throws Exception {
		startNewTransaction();

		onBeforeTestMethod();

		setComplete();
		endTransaction();
	}
	

	@Override
	protected final void onTearDownAfterTransaction() throws Exception {
		try {
			startNewTransaction();
			if (!dirty) {
				onAfterTestMethodIfNotDirty();
			}
			onAfterTestMethod();

			setComplete();
			endTransaction();
		} catch (Exception e) {
			throwawayOnTestEnd();
		}

		if (dirty) {
			super.setDirty();
		}
	}

	protected void onBeforeTestMethod() throws Exception {

	}

	protected void onAfterTestMethod() throws Exception {

	}

	protected void onAfterTestMethodIfNotDirty() throws Exception {

	}
}