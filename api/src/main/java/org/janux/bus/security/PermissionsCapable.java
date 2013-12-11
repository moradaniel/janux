package org.janux.bus.security;

import java.util.Map;

/**
 *************************************************************************************************
 * This interface defines classes that may have permissions granted to them; as of this
 * writing, it is intended to be a super interface for the Account and Role interfaces, both of
 * which may be assigned Permissions directly.
 *
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.1
 *************************************************************************************************
 */
public interface PermissionsCapable
{

	/** 
	 * Returns a map of all the PermissionContexts in which this PermissionsCapable Entity has been
	 * granted a Permission, whether directly or via a Sub-Role; the map is keyed by the
	 * PermissionContext's Name
	 */
	Map<String, PermissionContext> getPermissionContexts();


	/** 
	 * Given a PermissionContext, this method returns the permissions, represented as Strings, that
	 * this PermissionsCapable Entity has in that permission context, or an empty array if the
	 * PermissionsCapable Entity has no permissions in that PermissionContext
	 */
	String[] getPermissions(String permissionContext);


	/** 
	 * Given a permission context, and the names of permissions available in that context, 
	 * this method returns true if this role has all the permissions named
	 */
	boolean hasPermissions(String permissionContext, String[] permissionNames);

	/** 
	 * Given a permission context, and the names of a permission available in that context, 
	 * this method returns true if this role has the permission named
	 */
	boolean hasPermissions(String permissionContext, String permissionName);


	/** 
	 * Given a permission context, and a list of names of permissions available in that context, this
	 * method sets the permissions indicated to this PermissionsCapable Entity; if permissions had
	 * already been set for that PermissionContext, this method will override them; if you want to add
	 * Permissions without overriding existing permissions, use {@link #addPermissions()} instead
	 *
	 * @param permissiontContext the name of a PermissionContext
	 * @param permissionNames 
	 * 	the name of permissions available in the named PermissionContext, 
	 * 	that are to be granted to this PermissionsCapable Entity
	 */
	//void setPermissions(String permissionContext, String[] permissionNames);


	/** 
	 * Given a permission context, and a list of names of permissions available in that context, 
	 * this method adds the permissions indicated to this PermissionsCapable Entity; this method does
	 * not override any existing Permissions, but simply adds to them
	 *
	 * @param permissiontContext the name of a PermissionContext
	 * @param permissionNames 
	 * 	the name of permissions available in the named PermissionContext, 
	 * 	that are to be granted to this PermissionsCapable Entity
	 */
	//void grantPermissions(String permissionContext, String[] permissionNames);


	/** 
	 * Given a permission context, and a list of names of permissions available in that context, 
	 * this method substracts the permissions indicated to this PermissionsCapable Entity; this method
	 * is meant to be used in the case where it is desireable to remove a set of permissions that are
	 * inherited from a Role; if you are not composing Permissions from a Role, it would be best to
	 * just set the Permissions to what they should be
	 *
	 * @param permissiontContext the name of a PermissionContext
	 * @param permissionNames 
	 * 	the name of permissions available in the named PermissionContext, 
	 * 	that are to be subtracted to this PermissionsCapable Entity
	 */
	//void revokePermissions(String permissionContext, String[] permissionNames);


	/** 
	 * In the case of an implementation that uses bitmasks to store permissions, and given a
	 * PermissionContext, this method returns the permissions that this PermissionsCapable Entity has
	 * in that permission context, represented as a long value
	 */
	long getPermissionsValue(String permissionContext);


	/** 
	 * In the case of an implementation that uses bitmasks to store permissions, and given a
	 * permission context and a long value representing multiple permissions available in that
	 * context, this method returns true if this role has all the permissions indicated
	 */
	boolean hasPermissions(String permissionContext, long permissionsValue);


	/** 
	 * In the case of an implementation that uses bitmasks to store permissions, and given a
	 * permission context and a long value representing multiple permissions available in that
	 * context, this method grants the permissions indicated to this PermissionsCapable Entity.
	 * <p>
	 * The permissions granted by this method are added to any permissions that this entity may
	 * inherit from its Roles.  If you would like to remove all Permissions granted directly to this
	 * entity within a Permission Context, set the bitmask to 0.
	 * </p><p>
	 * Note that setting the Permissions bitmask to 0 will not work to revoke permissions that this
	 * entity may have inherited from its Roles; in such case you should call denyPermissions to
	 * explicitly deny the permissions in question.  This will create an 'isDeny' bitmask that will
	 * block inherited permissions.
	 * </p>
	 *
	 * @see #denyPermissions
	 *
	 * @param permissionContext a valid PermissionContext
	 * @param permissionsValue
	 * 	a long value representing permissions that are to be granted to this PermissionsCapable Entity; 
	 * 	the permissions must be available in the named PermissionContext
	 */
	void grantPermissions(PermissionContext permissionContext, long permissionsValue);

	/**
	 * Explicitly denies a set of Permissions within a PermissionContext; this method should be used
	 * only to deny permissions that are inherited from Roles associated to this PermissionsCapable
	 * entity; this method is not meant to be used as the opposite action to method 
	 * {@link #grantPermissions(PermissionContext, long)}, although it could be abused that way,
	 * <p>
	 * For example, assuming a Role 'PRODUCT ADMIN' that has the Permissions READ, UPDATE, CREATE, DISABLE,
	 * PURGE in the PRODUCT Permission Context (plus possibly other Permissions in other Permission
	 * Contexts), it may desireable to create a 'PRODUCT MANAGER' Role that has 'PRODUCT ADMIN' as its
	 * sub-role, but denies the Permissions to CREATE and PURGE.
	 * </p><p>
	 * <p>
	 * On the other hand, assume that we only have the 'PRODUCT ADMIN' Role and that we want to revoke
	 * its 'PURGE' Permission, in the Permission Context 'PRODUCT'.  We could call denyPermissions to
	 * do so, but this will create an 'isDeny' bitmask in addition to the existing 'allow' bitmask one
	 * through which the 'PURGE' Permission was originally granted. Instead, it would be simpler to call
	 * {@link #grantPermissions(PermissionContext,long)} again with the proper 'allow' bitmask that 
	 * no longer enables the 'PURGE' permission.  
	 * </p>
	 */
	void denyPermissions(PermissionContext permissionContext, long permissionsValue);

	// do we need these to explicitly act upon the permissions granted to the entity directly ?
	//void setPermissionsGranted(PermissionContext permissionContext, boolean isDeny, long permissionsValue);
	//void Long getPermissionsGranted(PermissionContext permissionContext, boolean isDeny, long permissionsValue);

}
