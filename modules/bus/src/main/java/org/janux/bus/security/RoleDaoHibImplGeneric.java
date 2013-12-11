package org.janux.bus.security;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.janux.bus.persistence.EntityNotFoundException;
import org.janux.bus.persistence.GenericDaoHibImpl;
import org.janux.bus.search.SearchCriteria;
import org.janux.util.Chronometer;
import org.janux.util.SortOrderComparator;
import org.springframework.dao.DataAccessException;

/**
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @author  <a href="mailto:daniel.mora@janux.org">Daniel Mora</a>
 * @see RoleDaoGeneric
 * @since 0.4
 */

public class RoleDaoHibImplGeneric 
	extends GenericDaoHibImpl<RoleImpl, Integer, SearchCriteria>
	implements RoleDaoGeneric<RoleImpl>
{
	public SortedSet<Role> loadAll()
	{
		Chronometer timer = new Chronometer(true);
		if (log.isDebugEnabled()) log.debug("attempting to load all roles...");

		@SuppressWarnings("unchecked")
		List<RoleImpl> list = getHibernateTemplate().loadAll(this.getPersistentClass());

		SortedSet<Role> set = new TreeSet<Role>(new SortOrderComparator());

		for (Role role : list) {
			this.initializeRoles(role);
			set.add(role);
		}

		if (log.isInfoEnabled()) log.info("successfully retrieved all " + set.size() + " roles in " + timer.printElapsedTime());

		return set;
	}


	public Role findByName(String name)
	{
		Chronometer timer = new Chronometer(true);

		if (log.isDebugEnabled()) log.debug("attempting to find Role with name '" + name + "'");

		List list = getHibernateTemplate().find("from RoleImpl where name=?", name);

		Role role = (list.size() > 0) ? (Role)list.get(0) : null;

		if (role == null) {
			log.warn("Unable to find Role with name: '" + name + "'");
			return null;
		}
		this.initializeRoles(role);

		if (log.isInfoEnabled()) log.info("successfully retrieved role with name: '" + name + "' in " + timer.printElapsedTime());
		if (log.isDebugEnabled()) log.debug(role);

		return role;
	}


	public Role loadByName(String name) throws EntityNotFoundException
	{
		Role role = this.findByName(name);

		if (role == null) 
			throw new EntityNotFoundException("Unable to retrieve role with name: '" + name + "'");

		return role;
	}


	public Role newRole()
	{
		return new RoleImpl();
	}


	/** 
	 * recursively iterates and retrieves all sub-roles of this role, 
	 * and has mechanism to prevent a looping condition
	 */
	private void initializeRoles(Role role) 
	{
		if (role == null) {throw new IllegalArgumentException("Attempting to initialize null Role!");}
		Chronometer timer = new Chronometer(true);
		this.initializeRoles(role, new HashSet<Role>());
		if (log.isDebugEnabled()) log.debug("initialized roles aggregated by Role " + role.getName() + " in " + timer.printElapsedTime());
	}


	/** 
	 * Recursively iterates and retrieves all sub-roles of this role, and has a mechanism to prevent a
	 * looping condition. This method also removes any aggregated roles that are null as a result of a
	 * non-sequential sortOrder index: Hibernate returns nulls when a List index has a non-sequential
	 * index, for example as a result of removing a list record from the database manually without
	 * adjusting the index of subsequent list members.
	 */
	private void initializeRoles(Role role, Set<Role> foundSet)
	{
		if (role == null) {throw new IllegalArgumentException("Attempting to initialize null Role!");}
		if (log.isDebugEnabled()) log.debug("initializing roles aggregated by Role " + role.getName());
		foundSet = (foundSet != null) ? foundSet : new HashSet<Role>();

		// add the current role to the found set to prevent a loop in the event
		// that we encounter this role in one of the recursive calls
		foundSet.add(role);

		int i=0;
		for (Iterator roles = role.getRoles().iterator(); roles.hasNext(); i++)
		{
			Role aggrRole = (Role)roles.next();

			// remove any null roles from the list; this can happen when using hibernate 
			// and there are 'gaps' in the index that keys the List, for example when manually removing
			// records from the table without adjusting the sortOrder index
			if (aggrRole == null) 
			{
				log.warn("Found Null Role aggregated in Role '" + role.getName() + "' at position: " + i + " - removing...");
				roles.remove();
				continue;
			}

			if (foundSet.contains(aggrRole)) {
				if (log.isDebugEnabled()) log.debug("aggrRole " + aggrRole.getName() + " has already been initialized - skipping");
				continue;
			}
			else {
				this.initializeRoles(aggrRole, foundSet);
			}
		}
	}

}
