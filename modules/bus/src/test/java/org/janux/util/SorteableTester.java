package org.janux.util;

/** class used to test the SortOrderComparator class */
public class SorteableTester implements Sorteable
{
	private Integer sortOrder;

	SorteableTester(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
}
