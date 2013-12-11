package org.janux.bus.security;

import java.util.SortedSet;

import org.janux.bus.persistence.EntityNotFoundException;
import org.janux.bus.persistence.GenericDaoReadOnly;
import org.janux.bus.persistence.GenericDaoWrite;
import org.janux.bus.search.SearchCriteria;

/**
 * Used to create, save, retrieve, update and delete Role objects from
 * persistent storage
 *
 * @author  <a href="mailto:philippe@innpoints.com">Philippe Paravicini</a>
 * @since 0.4
 */
public interface RoleDaoGeneric<T extends Role> 
	extends GenericDaoWrite<T, Integer>,
	GenericDaoReadOnly<T, Integer, SearchCriteria>

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
	 * @param name a name that uniquely identifies this role
	 *
	 * @throws EntityNotFoundException if a Role object with that name is not found
	 */
	Role loadByName(String name) throws EntityNotFoundException;


	/** returns a new Role instance */
	PermissionsCapable newRole();
}
