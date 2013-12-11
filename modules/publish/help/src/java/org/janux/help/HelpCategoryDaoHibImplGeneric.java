package org.janux.help;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.janux.bus.persistence.GenericDaoWithFacetsHibImpl;
import org.janux.bus.search.SearchCriteria;
import org.janux.util.Chronometer;
import org.springframework.dao.DataAccessException;


public class HelpCategoryDaoHibImplGeneric extends GenericDaoWithFacetsHibImpl<HelpCategoryImpl, Integer, SearchCriteria, HelpCategoryFacet> implements HelpCategoryDaoGeneric<HelpCategoryImpl>
{
	public HelpCategoryImpl load(HelpCategoryImpl helpCategory, List<HelpCategoryFacet> facetSet) throws DataAccessException{
		return null;
	}
	
	public void initialize(HelpCategoryImpl helpCategory, HelpCategoryFacet facet) {
		throw new UnsupportedOperationException();
	}


	public HelpCategory newHelpCategory(String title)
	{
		return new HelpCategoryImpl(title);
	}
	
	public HelpCategory findByTitle(String title)
	{
		Chronometer timer = new Chronometer(true);
		final Criteria criteria = this.getSession().createCriteria(this.getPersistentClass());
		criteria.add( Restrictions.eq("title", title).ignoreCase() );
		List matchList = criteria.list();
		HelpCategory entry = (matchList.size() > 0) ? (HelpCategory) matchList.get(0) : null;

		if (entry != null) {
			log.debug("Help Category: " + entry.getTitle());
			if (log.isInfoEnabled()) log.info("successfully retrieved HelpCategory '" + title + "' in " + timer.printElapsedTime());
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
		Chronometer timer = new Chronometer(true);
		if (log.isDebugEnabled()) log.debug("attempting to load all help categories...");

		List list = getHibernateTemplate().loadAll(this.getPersistentClass());

		if (log.isInfoEnabled()) log.info("successfully retrieved all '" + list.size() + "' HelpCategories in " + timer.printElapsedTime());

		return list;
	}

}
