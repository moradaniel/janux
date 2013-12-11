package org.janux.bus.security;


/**
 ***************************************************************************************************
 * Class representing an individual PermissionBit within a specific PermissionContext; a PermissionBit is
 * only meaningful in the context of the PermissionContext that defines it: for example, a PermissionContext
 * named 'PERSON' may define Permissions with names 'CREATE', 'READ', 'UPDATE', 'DISABLE',
 * 'PURGE', that define the kind of operations on Persons that may be restricted by the security
 * system; see the javadoc of PermissionContext for a more detailed discussion.
 * <p>
 * The PermissionBit interface provides for defining the bit position of the PermissionBit within a bit
 * mask (0, 1, 2, 3, etc...), and a convenience method for returning the long value of that bit
 * position (that is 2 taken to the power of the bitPosition, e.g. 1, 2, 4, 8...)
 * </p>
 *
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.1
 ***************************************************************************************************
 */
public interface PermissionBit
{
	/**	
	 * Short-hand name for this PermissionBit (e.g.: READ), 
	 * unique in the context of the containing PermissionContext 
	 */
	String getName();
	void setName(String name);

	/** 
	 * The position of the PermissionBit within the bit mask defined by the PermissionContext, should be a
	 * sequential integer relative to the sequence defined by the PermissionContext; so if a PermissionContext
	 * defines 5 permissions, this should be a number between 0 and 4 that is not used by any of the
	 * other Permissions in the PermissionContext
	 */
	short getPosition();
	void  setPosition(short pos);

	/** A convenience method that returns 2 to the power of the bitPosition */
	long getValue();
	
	/** the PermissionContext that contains/uses this PermissionBit */
	PermissionContext getPermissionContext();
	void setPermissionContext(PermissionContext permContext);
	
	/**	 Human readable description of this PermissionBit */
	String getDescription();
	void setDescription(String description);

	/** 
	 * used to display the sort order independently from the Bit's Position, defaults to the
	 * getPosition if not set explicitly 
	 */
	Integer getSortOrder();
	void setSortOrder(Integer i);
}
