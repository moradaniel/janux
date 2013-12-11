package biz.janux.geography;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.janux.bus.persistence.EntityNotFoundException;
import org.janux.bus.persistence.GenericDaoHibImpl;
import org.janux.bus.search.SearchCriteria;
import org.janux.util.Chronometer;
import org.springframework.dao.DataAccessException;


/**
 * Used to create, save, retrieve, update and delete Country objects from
 * persistent storage
 *
 * @author  <a href="mailto:philippe.paravicini@janux.biz">Philippe Paravicini</a>
 * @since 0.4
 */
public class CountryDaoHibImplGeneric 
	extends GenericDaoHibImpl<CountryImpl, Integer, SearchCriteria>
	implements CountryDaoGeneric<CountryImpl>
{
	public Map<String, Country> loadAll() throws EntityNotFoundException
	{
		Chronometer timer = new Chronometer(true);

		if (log.isDebugEnabled()) log.debug("attempting to load all Countries...");

		List l =  getHibernateTemplate().find("FROM biz.janux.geography.Country WHERE visible = true ORDER BY sortOrder");

		Map<String, Country> countries = new LinkedHashMap<String, Country>();

		for (Iterator i = l.iterator(); i.hasNext();)
		{
			Country c = (Country)i.next();
			log.debug(c);
			log.debug(c.getCode());
			countries.put(c.getCode(), c);
			log.debug(countries.keySet());
		} // end for

		if (log.isInfoEnabled()) log.info("successfully retrieved account '" + countries.keySet().size() + " countries" + "' in " + timer.printElapsedTime());

		return countries;
	}


	/* 
	 * retrieves a Country using a standard ISO code, or returns
	 * <code>null</code> if a Country with that code is not found 
	 */
	public Country findByCode(String code)
	{
		Chronometer timer = new Chronometer(true);

		if (log.isDebugEnabled()) log.debug("attempting to retrieve Country with code '" + code + "'");

		List l = getHibernateTemplate().find("FROM biz.janux.geography.Country as c WHERE c.code=?", code);

		Country country = (l != null && l.size()>0) ? (Country)l.get(0) : null;

		if (country != null)
			if (log.isInfoEnabled()) log.info("successfully retrieved '" + country + "' in " + timer.printElapsedTime() + " - " + country);
		else
			log.warn("unable to find Country with code: '" + code + "'");

		return country;
	}


	public Country findByName(String name)
	{
		Chronometer timer = new Chronometer(true);

		if (log.isDebugEnabled()) log.debug("attempting to retrieve Country with name '" + name + "'");

		
		// do a case-insensitive lookup
		List l = this.getSession().createCriteria(Country.class).add( Restrictions.eq("name", name).ignoreCase() ).list();
	//	List l = getHibernateTemplate().find("FROM biz.janux.geography.Country as c WHERE c.name=?", name);
		
		Country country = (l != null && l.size()>0) ? (Country)l.get(0) : null;

		if (country != null)
			if (log.isInfoEnabled()) log.info("successfully retrieved '" + country + "' in " + timer.printElapsedTime() + " - " + country);
		else
			log.warn("unable to find Country with name: '" + name + "'");

		return country;
	}
	
}
