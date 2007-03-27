/**
 * Created on 2006-7-25
 * Created by Sunteya
 */
package com.sunteya.flyer.support;

import java.io.Serializable;


/**
 *
 * @author 诸南敏
 * @email Sunteya@gmail.com
 */
public class PaginationStrategy implements Serializable {

	private static final long serialVersionUID = -5403761531524968051L;
	private int itemAmount;
	private int currentPageIndex;
	private int pageSize;


	public PaginationStrategy() {
	}

	public PaginationStrategy(int currentPageIndex, int pageSize, int itemAmount) {
		super();
		this.itemAmount = itemAmount;
		this.currentPageIndex = currentPageIndex;
		this.pageSize = pageSize;
	}

	public boolean isLastPage() {
		return getLastPageIndex() == getCurrentPageIndex();
	}

	public boolean isFirstPage() {
		return getFirstPageIndex() == getCurrentPageIndex();
	}

	public boolean isExistPreviousPage() {
		return !isFirstPage();
	}

	public boolean isExistNextPage() {
		return !isLastPage();
	}

	public int getPreviousPageIndex() {
		if (isExistPreviousPage()) {
			return getCurrentPageIndex() - 1;
		}

		return getCurrentPageIndex();
	}

	public int getNextPageIndex() {
		if (isExistNextPage()) {
			return getCurrentPageIndex() + 1;
		}

		return getCurrentPageIndex();
	}

	public int getCurrentPageItemCount() {
		if (isLastPage()) {
			return getItemAmount() - getAllPreviousPageItemsCount();
		} else {
			return getPageSize();
		}
	}

	public int getAllPreviousPageItemsCount() {
		int previousPageItemCount = (getCurrentPageIndex() - getFirstPageIndex())
				* getPageSize();
		return previousPageItemCount;
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

	public boolean isEmptyItemAmount() {
		return getItemAmount() == 0;
	}

	public int getFirstPageIndex() {
		return 1;
	}

	public int getLastPageIndex() {
		return getFirstPageIndex() + (getPageCount() - 1);
	}

	public int getCurrentPageIndex() {
		if (currentPageIndex < getFirstPageIndex()) {
			return getFirstPageIndex();
		}
		if (currentPageIndex > getLastPageIndex()) {
			return getLastPageIndex();
		}
		return currentPageIndex;
	}

	public void setCurrentPageIndex(int currentPageIndex) {
		this.currentPageIndex = currentPageIndex;
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

	public boolean isNotEmptyItemAmount() {
		return !isEmptyItemAmount();
	}
}
