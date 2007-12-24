/**
 *
 */
package com.sunteya.flyer.support;

import com.sunteya.flyer.support.RequestPage;

/**
 * @author Sunteya
 *
 */
public class BeanRequestPage implements RequestPage {

	private static final long serialVersionUID = -3483889773149422203L;

	private int requestPageIndex;
	private int requestPageSize;

	public BeanRequestPage() {
	}

	public BeanRequestPage(int requestPageIndex, int requestPageSize) {
		this.requestPageIndex = requestPageIndex;
		this.requestPageSize = requestPageSize;
	}

	public int getRequestPageIndex() {
		return this.requestPageIndex;
	}

	public int getRequestPageSize() {
		return this.requestPageSize;
	}

	public void setRequestPageIndex(int requestPageIndex) {
		this.requestPageIndex = requestPageIndex;
	}

	public void setRequestPageSize(int requestPageSize) {
		this.requestPageSize = requestPageSize;
	}

}
