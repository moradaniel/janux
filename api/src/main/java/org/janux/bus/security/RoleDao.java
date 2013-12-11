package org.janux.bus.security;

import java.util.SortedSet;

import org.janux.bus.persistence.DataAccessObject;
import org.janux.bus.persistence.EntityNotFoundException;

/**
 * Used to create, save, retrieve, update and delete Role objects from
 * persistent storage
 *
 * @author  <a href="mailto:philippe@innpoints.com">Philippe Paravicini</a>
 * 
 * @deprecated use {@link RoleDaoGeneric}
 */
public interface RoleDao extends DataAccessObject
{
	/** 
	 * Loads all Roles defined in the system, along with their Permissions and SubRoles;
	 * a SubRole may appear individually, as well as part of the children of the 
	 * Super Roles that may contain it
	 */
	SortedSet<Role> loadAll();


	/**
	 * Returns an Role by names, or <code>null</code> if the Role is not found.
	 *
	 * @param name the Role name
	 */
	Role findByName(String name);


	/**
	 * loads an Role object, or throws exception if Role with that name is not found
	 *
	 * @param name a name that uniquely identifies this Role
	 *
	 * @throws EntityNotFoundException if a Role object with that name is not found
	 */
	Role loadByName(String name) throws EntityNotFoundException;


	/** returns a new Role instance */
	PermissionsCapable newRole();
}
