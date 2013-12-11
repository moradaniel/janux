package org.janux.help;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.janux.bus.persistence.DataAccessHibImplAbstract;
import org.janux.bus.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;
/**
 *  * @deprecated use {@link HelpCategoryDaoHibImplGeneric} 
 */

public class HelpEntryDaoHibImpl extends DataAccessHibImplAbstract implements HelpEntryDao
{
	
	public HelpEntry load(Integer id) throws EntityNotFoundException
	{
		long begin = System.currentTimeMillis();

		if (log.isDebugEnabled()) log.debug("attempting to load HelpEntry with id '" + id + "'");

		HelpEntry helpEntry;

		try
		{
			helpEntry = (HelpEntry) getHibernateTemplate().load(HelpEntryImpl.class, id);
		} 
		catch (DataAccessException e)
		{
			String msg = "Unable to load HelpEntry with id " + id;
			log.error(msg, e);
			throw new EntityNotFoundException(msg,e);
		}

		if (log.isInfoEnabled()) recordTime("successfully retrieved: " + helpEntry, begin); 

		return helpEntry;
	}

	
	public HelpEntry findByCode(String code)
	{
		long begin = System.currentTimeMillis();

		if (log.isDebugEnabled()) log.debug("attempting to find HelpEntry with code '" + code + "'");

		List list = getHibernateTemplate().find("from HelpEntryImpl where code=?", code);

		HelpEntry entry = (list.size() > 0) ? (HelpEntry) list.get(0) : null;

		if (entry == null) {
			log.warn("Unable to find HelpEntry with code: '" + code + "'");
			return null;
		}

		if (log.isInfoEnabled()) log.info("successfully retrieved HelpEntry with code: '" + code + "' in " + (System.currentTimeMillis() - begin) + " ms" ); 

		return entry;
	}

	public HelpEntry loadByCode(String code) throws EntityNotFoundException
	{
		HelpEntry entry = this.findByCode(code);

		if (entry == null) 
			throw new EntityNotFoundException("Unable to retrieve HelpEntry with code: '" + code + "'");

		return entry;
	}

	
	public HelpEntry findByLabel(String label)
	{
		long begin = System.currentTimeMillis();
		final Criteria criteria = this.getSession().createCriteria(HelpEntry.class);
		criteria.add( Restrictions.eq("label", label).ignoreCase() );
		List matchList = criteria.list();
		HelpEntry entry = (matchList.size() > 0) ? (HelpEntry) matchList.get(0) : null;

		if (entry != null) {
			log.debug("Help Entry: " + entry.getLabel());
			if (log.isInfoEnabled()) recordTime("successfully retrieved: " + entry, begin);
		}
		else
			log.warn("unable to find Help Entry with label: '" + label + "'");

		return entry;
	}

	public void delete(HelpEntry entry)
	{
		getHibernateTemplate().delete(entry);
	}
	
	public HelpEntry newHelpEntry(String code,String label,HelpCategory category)
	{
		return new HelpEntryImpl(code,label,category);
	}


	@SuppressWarnings("unchecked")
	public List<HelpEntry> loadAll()
	{
		long begin = System.currentTimeMillis();
		if (log.isDebugEnabled()) log.debug("attempting to load all help entries...");

		List<HelpEntry> list = getHibernateTemplate().loadAll(HelpEntry.class);

		if (log.isInfoEnabled()) recordTime("successfully retrieved all " + list.size() + " help entries", begin);

		return list;
	}

}
