/**
 * Created on 2006-11-27
 * Created by Sunteya
 */
package com.sunteya.flyer.support;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author 诸南敏
 * @email zhunm@dep5.com
 */
public class Pagination<T> implements Serializable {

	private static final long serialVersionUID = -8094055046166452547L;
	private PaginationStrategy strategy;
	private List<T> elements;

	public Pagination() {
		super();
	}

	public Pagination(PaginationStrategy strategy, List<T> elements) {
		this.strategy = strategy;
		this.elements = elements;
	}

	// =====================================================
	// delegates
	// -----------------------------------------------------
	public int getAllPreviousPageItemsCount() {
		return strategy.getAllPreviousPageItemsCount();
	}

	public int getCurrentPageIndex() {
		return strategy.getCurrentPageIndex();
	}

	public int getCurrentPageItemCount() {
		return strategy.getCurrentPageItemCount();
	}

	public int getFirstPageIndex() {
		return strategy.getFirstPageIndex();
	}

	public int getItemAmount() {
		return strategy.getItemAmount();
	}

	public int getLastPageIndex() {
		return strategy.getLastPageIndex();
	}

	public int getNextPageIndex() {
		return strategy.getNextPageIndex();
	}

	public int getPageCount() {
		return strategy.getPageCount();
	}

	public int getPageSize() {
		return strategy.getPageSize();
	}

	public int getPreviousPageIndex() {
		return strategy.getPreviousPageIndex();
	}

	public boolean isEmptyItemAmount() {
		return strategy.isEmptyItemAmount();
	}

	public boolean isExistNextPage() {
		return strategy.isExistNextPage();
	}

	public boolean isExistPreviousPage() {
		return strategy.isExistPreviousPage();
	}

	public boolean isFirstPage() {
		return strategy.isFirstPage();
	}

	public boolean isLastPage() {
		return strategy.isLastPage();
	}

	public boolean isNotEmptyItemAmount() {
		return strategy.isNotEmptyItemAmount();
	}

	// =====================================================
	// Gettings And Settings
	// -----------------------------------------------------
	public List<T> getElements() {
		return elements;
	}

	public void setElements(List<T> elements) {
		this.elements = elements;
	}

	public PaginationStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(PaginationStrategy pagination) {
		this.strategy = pagination;
	}
}
