/**
 * Created on 2007-3-18
 * Created by Sunteya
 */
package com.sunteya.flyer.test.unitils;

import java.lang.reflect.Method;

import org.unitils.core.TestListener;

/**
 * @author Sunteya
 *
 */
public class TestListenerWrapper extends TestListener {

	private TestListener testListener;

	public TestListenerWrapper(TestListener testListener) {
		this.testListener = testListener;
	}

	public void afterAll() {
		testListener.afterAll();
	}

	public void afterTestClass(Class<?> testClass) {
		testListener.afterTestClass(testClass);
	}

	public void afterTestMethod(Object testObject, Method testMethod) {
		testListener.afterTestMethod(testObject, testMethod);
	}

	public void afterTestTearDown(Object testObject) {
		testListener.afterTestTearDown(testObject);
	}

	public void beforeAll() {
		testListener.beforeAll();
	}

	public void beforeTestClass(Class<?> testClass) {
		testListener.beforeTestClass(testClass);
	}

	public void beforeTestMethod(Object testObject, Method testMethod) {
		testListener.beforeTestMethod(testObject, testMethod);
	}

	public void beforeTestSetUp(Object testObject) {
		testListener.beforeTestSetUp(testObject);
	}

}
