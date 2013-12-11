package org.janux.help;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.janux.bus.persistence.EntityNotFoundException;
import org.janux.bus.persistence.GenericDaoWithFacetsHibImpl;
import org.janux.bus.search.SearchCriteria;
import org.janux.util.Chronometer;
import org.springframework.dao.DataAccessException;



public class HelpEntryDaoHibImplGeneric extends GenericDaoWithFacetsHibImpl<HelpEntryImpl, Integer, SearchCriteria, HelpEntryFacet> implements HelpEntryDaoGeneric<HelpEntryImpl>
{
	
	public void initialize(HelpEntryImpl helpEntry, HelpEntryFacet facet) {
		throw new UnsupportedOperationException();
	}

	public HelpEntryImpl load(HelpEntryImpl helpEntry, List<HelpEntryFacet> facetSet) throws DataAccessException{
		return null;
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
		Chronometer timer = new Chronometer(true);
		final Criteria criteria = this.getSession().createCriteria(this.getPersistentClass());
		criteria.add( Restrictions.eq("label", label).ignoreCase() );
		List matchList = criteria.list();
		HelpEntry entry = (matchList.size() > 0) ? (HelpEntry) matchList.get(0) : null;

		if (entry != null) {
			log.debug("Help Entry: " + entry.getLabel());
			if (log.isInfoEnabled()) log.info("successfully retrieved HelpEntry '" + label + "' in " + timer.printElapsedTime());
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
		Chronometer timer = new Chronometer(true);
		if (log.isDebugEnabled()) log.debug("attempting to load all help entries...");

		List list = getHibernateTemplate().loadAll(this.getPersistentClass());

		if (log.isInfoEnabled()) log.info("successfully retrieved all '" + list.size() + "' HelpEntries in " + timer.printElapsedTime());

		return list;
	}

}
