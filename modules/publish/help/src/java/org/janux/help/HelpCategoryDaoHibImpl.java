package org.janux.help;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.janux.bus.persistence.DataAccessHibImplAbstract;
import org.janux.bus.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;

/**
 * 
 * @deprecated use {@link HelpCategoryDaoHibImplGeneric}
 *
 */
public class HelpCategoryDaoHibImpl extends DataAccessHibImplAbstract implements HelpCategoryDao
{
	public HelpCategoryDaoHibImpl()
	{
		super();
	}
	
	public HelpCategory load(Integer id) throws EntityNotFoundException
	{
		long begin = System.currentTimeMillis();

		if (log.isDebugEnabled()) log.debug("attempting to load HelpCategory with id '" + id + "'");

		HelpCategory helpCategory;

		try
		{
			helpCategory = (HelpCategory) getHibernateTemplate().load(HelpCategoryImpl.class, id);
		} 
		catch (DataAccessException e)
		{
			String msg = "Unable to load HelpCategory with id " + id;
			log.error(msg, e);
			throw new EntityNotFoundException(msg,e);
		}

		if (log.isInfoEnabled()) recordTime("successfully retrieved: " + helpCategory, begin); 

		return helpCategory;
	}

	public HelpCategory newHelpCategory(String title)
	{
		return new HelpCategoryImpl(title);
	}
	
	public HelpCategory findByTitle(String title)
	{
		long begin = System.currentTimeMillis();
		final Criteria criteria = this.getSession().createCriteria(HelpCategory.class);
		criteria.add( Restrictions.eq("title", title).ignoreCase() );
		List matchList = criteria.list();
		HelpCategory entry = (matchList.size() > 0) ? (HelpCategory) matchList.get(0) : null;

		if (entry != null) {
			log.debug("Help Category: " + entry.getTitle());
			if (log.isInfoEnabled()) recordTime("successfully retrieved: " + entry, begin);
		}
		else
			log.warn("unable to find Help Category with title: '" + title + "'");

		return entry;
	}

	public void delete(HelpCategory entry)
	{
		getHibernateTemplate().delete(entry);
	}


	@SuppressWarnings("unchecked")
	public List<HelpCategory> loadAll()
	{
		long begin = System.currentTimeMillis();
		if (log.isDebugEnabled()) log.debug("attempting to load all help categories...");

		List<HelpCategory> list = getHibernateTemplate().loadAll(HelpCategory.class);

		if (log.isInfoEnabled()) recordTime("successfully retrieved all " + list.size() + " help categories", begin);

		return list;
	}

}
