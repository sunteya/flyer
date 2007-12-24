/**
 * Created on 2006-7-25
 * Created by Sunteya
 */
package com.sunteya.flyer.support;



/**
 *
 * @author 诸南敏
 * @email Sunteya@gmail.com
 */
public class PaginationStrategy extends PaginationSupport {

	private static final long serialVersionUID = -5403761531524968051L;
	private int currentPageIndex;

	public PaginationStrategy() {
	}

	public PaginationStrategy(int currentPageIndex, int pageSize, int itemAmount) {
		super(pageSize, itemAmount);
		this.currentPageIndex = currentPageIndex;
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
}
