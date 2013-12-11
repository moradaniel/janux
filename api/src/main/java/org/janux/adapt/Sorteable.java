package org.janux.adapt;

/**
 ***************************************************************************************************
 * Simple interface for entities that may be assigned a sort order; this field may be left null, in
 * which case the comparator is left to decide whether this is permissible, whether entities with a
 * null sort order should come first/last, and how entities with null sort order should be sorted
 * among themselves
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version $Revision: 1.1 $ - $Date: 2005-12-20 23:49:25 $
 ***************************************************************************************************
 */
public interface Sorteable
{
	Integer getSortOrder();
	void setSortOrder(Integer i);
}
