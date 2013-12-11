package org.janux.bus.security;

import java.util.List;
import org.janux.util.Sorteable;

/**
 ***************************************************************************************************
 * A PermissionContext represents a set of individual Permissions defined for a specific business
 * context; for example, a PermissionContext named 'PERSON' may define five Permissions with names
 * 'READ', 'UPDATE', 'CREATE', 'DISABLE', 'PURGE', that define the kind of operations on Persons
 * that may be restricted by the security system.
 * <p>
 * The PermissionBit interface, in turn, foresees an implementation that relies on bitmasks as a way to
 * assign Permissions to a Role. In the example above, the five Permissions above would be assigned
 * values 1, 2, 4, 8, 16 respectively, which would correspond to bit positions 0,1,2,3 and 4.  In
 * base-2 the five permissions would read 00001, 00010, 00100, 01000, 10000, respectively.
 * </p><p>
 * In order to grant READ/UPDATE permissions to a Role, a Role would be assigned the value 3=1+2
 * (in base-2: 00011) in the context of PermissionContext PERSON; a Role with a value of PERSON value of 
 * 31 = 1 + 2 + 4 + 8 + 16 would have all Permissions, or 11111 in base-2.
 * </p>	
 *
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.1
 ***************************************************************************************************
 */
public interface PermissionContext extends Sorteable
{
	/** A unique name for this PermissionContext, in the context of the Application */
	String getName();
	public void setName(String name);

	/**	   
	 * The set of permissions that this PermissionBit Set defines; note that this represents meta
	 * information of what sort of Permissions are available to be assigned within the context of a
	 * Busines Context and a Role, but that a PermissionBit Set does not confer any of these Permissions
	 * per-se to any entity.
	 */
	List<PermissionBit> getPermissionBits();

	/** Returns a PermissionBit by its unique name within the PermissionContext */
	PermissionBit getPermissionBit(String name);

	/** 
	 * Adds a PermissionBit to this PermissionContext - the implementation
	 * should make sure that there are no two PermissionBits with the same name,
	 * and that the value of PermissionBit.getPosition() is sequential and without gaps
	 */
	public void addPermissionBit(PermissionBit permissionBit); 

	/** 
	 * convenience method that returns the sum of values of a set of PermissionBits specified by name;
	 * for example, in the example above getValue({READ, UPDATE}) would return 3
	 */
	long getValue(String[] permNames);

	/** 
	 * returns the maximum value that the permission bitmask can take, should be equal to
	 * (2 to the power of getPermissionBits().size()) - 1
	 */
	long getMaxValue();

	/** Human readable description of this PermissionBit Set */
	String getDescription();
	public void setDescription(String description);

	/** The order in which this PermissionContext should be displayed */
	Integer getSortOrder();
	public void setSortOrder(Integer sortOrder);

	/** Determines whether or not this PermissionContext is being used */
	boolean isEnabled();
	void setEnabled(boolean visible);
}
