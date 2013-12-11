package org.janux.bus.security;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.janux.bus.persistence.DataAccessHibImplAbstract;
import org.janux.bus.persistence.EntityNotFoundException;
import org.janux.util.Chronometer;
import org.janux.util.SortOrderComparator;

/**
 * @author  <a href="mailto:philippe.paravicini@janux.biz">Philippe Paravicini</a>
 * @see PermissionContextDao
 * 
 * @deprecated use {@link PermissionContextDaoGeneric} 
 */
public class PermissionContextDaoHibImpl extends DataAccessHibImplAbstract implements PermissionContextDao
{
	public SortedSet<PermissionContext> loadAll()
	{
		Chronometer timer = new Chronometer(true);
		if (log.isDebugEnabled()) log.debug("attempting to load all permission contexts...");

		@SuppressWarnings("unchecked")
		List<PermissionContext> list = getHibernateTemplate().loadAll(PermissionContext.class);

		SortedSet<PermissionContext> set = new TreeSet<PermissionContext>(new SortOrderComparator());

		for (PermissionContext context : list) {
			if (log.isDebugEnabled()) log.debug(context.getName() + ": " + context);
			set.add(context);
		}

		if (log.isInfoEnabled()) log.info("successfully retrieved all " + set.size() + " permission contexts in " + timer.printElapsedTime());

		return set;
	}


	public PermissionContext findByName(String name)
	{
		Chronometer timer = new Chronometer(true);

		if (log.isDebugEnabled()) log.debug("attempting to find PermissionContext with name '" + name + "'");

		List list = getHibernateTemplate().find("from PermissionContextImpl where name=?", name);

		PermissionContext context = (list.size() > 0) ? (PermissionContext)list.get(0) : null;

		if (context == null) {
			log.warn("Unable to find PermissionContext with name: '" + name + "'");
			return null;
		}

		if (log.isInfoEnabled()) log.info("successfully retrieved context with name: '" + name + "' in " + timer.printElapsedTime());
		if (log.isDebugEnabled()) log.debug(context);

		return context;
	}


	public PermissionContext loadByName(String name) throws EntityNotFoundException
	{
		PermissionContext context = this.findByName(name);

		if (context == null) 
			throw new EntityNotFoundException("Unable to retrieve context with name: '" + name + "'");

		return context;
	}


	public PermissionContext newPermissionContext()
	{
		return new PermissionContextImpl();
	}
}
