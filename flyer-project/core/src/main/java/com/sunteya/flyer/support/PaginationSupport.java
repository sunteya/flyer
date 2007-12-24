/**
 * Created on 2007-9-7
 * Created by Sunteya
 */
package com.sunteya.flyer.support;

import java.io.Serializable;

/**
 * @author Sunteya
 *
 */
public class PaginationSupport implements Serializable {

	private static final long serialVersionUID = 1884251819023313231L;

	private int itemAmount;
	private int pageSize;

	public PaginationSupport() {
	}

	public static PaginationSupport newInstance(int pageSize, int itemAmount) {
		return new PaginationSupport(pageSize, itemAmount);
	}

	public PaginationSupport(int pageSize, int itemAmount) {
		this.itemAmount = itemAmount;
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		if (isEmptyItemAmount()) {
			return 1;
		}

		if (getItemAmount() % getPageSize() == 0) {
			return getItemAmount() / getPageSize();
		}
		return getItemAmount() / getPageSize() + 1;
	}


	public boolean isNotEmptyItemAmount() {
		return !isEmptyItemAmount();
	}

	public boolean isEmptyItemAmount() {
		return getItemAmount() == 0;
	}

	public int getItemAmount() {
		return itemAmount > 0 ? itemAmount : 0;
	}

	public void setItemAmount(int itemAmount) {
		this.itemAmount = itemAmount;
	}

	public int getPageSize() {
		return pageSize > 0 ? pageSize : 1;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}