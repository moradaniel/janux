package org.janux.bus.security;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 ***************************************************************************************************
 * Implementation artifact used to store a bit mask of permissions granted to a Role in the context of a
 * PermissionContext, and a sort order in which to display the PermissionContext in the context of the Role;
 * this class is only used to facilitate the hibernate mapping and is not exposed via any of the
 * interfaces
 *
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version $Revision: 1.5 $ - $Date: 2006-12-07 01:52:56 $
 *
 * @deprecated
 ***************************************************************************************************
 */
class PermissionGranted implements java.io.Serializable
{
	private static final long serialVersionUID = 9189392629885034693L;
	private long permissions;
	private boolean deny = false;
	private int  sortOrder;
	private PermissionContext permissionContext;
	private PermissionsCapable owner;

	/** plain vanilla constructor */
	PermissionGranted() {}

	PermissionGranted(PermissionContext permContext) {
		this.setPermissionContext(permContext);
	}

	/**	   
	 * Permissions granted to the parent Role, in the context of a PermissionContext, and represented as a bit mask
	 */
	long getPermissions() {
		return this.permissions;
	}
	
	void setPermissions(long permissions) {
		this.permissions = permissions;
	}


	public PermissionContext getPermissionContext() {
		return this.permissionContext;
	}
	
	public void setPermissionContext(PermissionContext permissionContext) {
		this.permissionContext = permissionContext;
	}


	public PermissionsCapable getOwner() {
		return this.owner;
	}
	
	public void setOwner(PermissionsCapable owner) {
		this.owner = owner;
	}


	public boolean isDeny() {
		return this.deny;
	}
	
	public void setDeny(boolean deny) {
		this.deny = deny;
	}


	/**	   
	 * Used to sort PermissionSets in the context of a Role assignment; for example this would be used
	 * to determine the sort order of PermissionBit Sets that exist under a Role
	 */
	int getSortOrder() {
		return this.sortOrder;
	}
	
	void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String toString() 
	{
		ToStringBuilder sb = new ToStringBuilder(this);

		sb.append("context"    , getPermissionContext().getName())
			.append("permissions", getPermissions());

		if (isDeny())
			sb.append("isDeny", isDeny());

		sb.append("sortOrder", getSortOrder());

		return sb.toString();
	}


	/** 
	 * Two PermissionGranted objects are equal if they refer to the same PermissionContext, Role and
	 * have the same value for the isDeny flag - in other words, only one PermissionGranted object may
	 * exist for each Role/PermissionContext/isDeny combination
	 */
	public boolean equals(Object other)
	{
		if ( (this == other ) ) return true;
		if ( !(other instanceof PermissionGranted) ) return false;
		PermissionGranted castOther = (PermissionGranted)other; 

		return new EqualsBuilder()
			.append(this.getOwner(), castOther.getOwner())
			.append(this.getPermissionContext(), castOther.getPermissionContext())
			.append(this.isDeny(), castOther.isDeny())
			.isEquals();
	}


	public int hashCode() 
	{
		return new HashCodeBuilder()
		.append(this.getOwner())
		.append(this.getPermissionContext())
		.append(this.isDeny())
		.toHashCode();
	}   

} // end class PermissionGranted
