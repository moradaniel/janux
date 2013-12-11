package org.janux.util;

import java.util.Comparator;

/** 
 * sorts classes that implement the Sorteable interface, that is, those which
 * have a getSortOrder method; sorteable objects with a null sortOrder field
 * are sorted last
 *
 * Note that this comparator may impose an ordering that is not consistent with
 * .equals() as in most cases this comparator is used to order objects in an
 * enumeration or visual display, and may not be related to the business
 * equality of the class
 *
 * @see Comparator
 */
public class SortOrderComparator implements Comparator<Sorteable>
{
	/** 
	 * Generally returns s1.getSortOrder() - s2.getSortOrder; the values are
	 * converted to long at the time of calculation, and the result is
	 * constrained to be between Integer.MIN_VALUE and Integer.MAX_VALUE
	 */
	public int compare(Sorteable s1, Sorteable s2)
	{
		long so1 = (long) ( (s1.getSortOrder() != null) ? s1.getSortOrder() : Integer.MIN_VALUE );
		long so2 = (long) ( (s2.getSortOrder() != null) ? s2.getSortOrder() : Integer.MIN_VALUE );

		if (so1 - so2 > 0)
			return (int) Math.min(so1-so2, Integer.MAX_VALUE);
		else
			return (int) Math.max(so1-so2, Integer.MIN_VALUE);
	}
}
