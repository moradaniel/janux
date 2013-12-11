package org.janux.bus.security;

import java.util.SortedSet;

import org.janux.bus.persistence.EntityNotFoundException;
import org.janux.bus.persistence.GenericDaoReadOnly;
import org.janux.bus.persistence.GenericDaoWrite;
import org.janux.bus.search.SearchCriteria;

/**
 * Used to create, save, retrieve, update and delete PermissionContext objects from
 * persistent storage
 *
 * @author  <a href="mailto:philippe@innpoints.com">Philippe Paravicini</a>
 * @since 0.4
 */

public interface PermissionContextDaoGeneric<T extends PermissionContext> 
	extends GenericDaoWrite<T, Integer>,
	GenericDaoReadOnly<T, Integer, SearchCriteria>
{
	/**  Loads all PermissionContexts defined in the system, sorted by sortOrder */
	public SortedSet<PermissionContext> loadAll();


	/**
	 * Returns a PermissionContext by names, or <code>null</code> if the PermissionContext is not found.
	 *
	 * @param name the PermissionContext name
	 */
	public PermissionContext findByName(String name);


	/**
	 * loads an PermissionContext object, or throws exception if PermissionContext with that name is not found
	 *
	 * @param name a name that uniquely identifies this PermissionContext
	 *
	 * @throws EntityNotFoundException if a PermissionContext object with that name is not found
	 */
	public PermissionContext loadByName(String name) throws EntityNotFoundException;


	/** returns a new PermissionContext instance */
	public PermissionContext newPermissionContext();
}
