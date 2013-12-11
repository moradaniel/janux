package biz.janux.geography;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.janux.bus.persistence.DataAccessHibImplAbstract;
import org.janux.bus.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;


/**
 * 
 * Used to create, save, retrieve, update and delete Country objects from
 * persistent storage
 *
 * @author  <a href="mailto:philippe.paravicini@janux.biz">Philippe Paravicini</a>
 * @version $Id: CountryDaoHibImpl.java,v 1.5 2006-07-17 05:18:23 dfairchild Exp $
 * @since 2006-02-17
 * 
 * @deprecated use {@link CountryDaoHibImplGeneric}
 *  
 */
public class CountryDaoHibImpl extends DataAccessHibImplAbstract implements CountryDao
{
	/* 
	 * loads a Country object from persistence using its id
	 */
	public Country load(Integer id) throws EntityNotFoundException
	{
		long begin = System.currentTimeMillis();

		if (log.isDebugEnabled()) log.debug("attempting to load Country with id '" + id + "'");

		Country country;

		try {
			country = (Country)getHibernateTemplate().load(CountryImpl.class, id);
		} 
		catch (DataAccessException e) {
			String msg = "Unable to load Country with id " + id;
			log.error(msg, e);
			throw new EntityNotFoundException(msg,e);
		}

		if (log.isInfoEnabled()) recordTime("successfully retrieved: " + country, begin); 

		return country;
	}


	public Map<String, Country> loadAll() throws EntityNotFoundException
	{
		long begin = System.currentTimeMillis();

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

		if (log.isInfoEnabled()) recordTime("successfully retrieved: " + countries.keySet().size() + " countries", begin); 

		return countries;
	}


	/* 
	 * retrieves a Country using a standard ISO code, or returns
	 * <code>null</code> if a Country with that code is not found 
	 */
	public Country findByCode(String code)
	{
		long begin = System.currentTimeMillis();

		if (log.isDebugEnabled()) log.debug("attempting to retrieve Country with code '" + code + "'");

		List l = getHibernateTemplate().find("FROM biz.janux.geography.Country as c WHERE c.code=?", code);

		Country country = (l != null && l.size()>0) ? (Country)l.get(0) : null;

		if (country != null)
			if (log.isInfoEnabled()) recordTime("successfully retrieved: " + country, begin);
		else
			log.warn("unable to find Country with code: '" + code + "'");

		return country;
	}


	public Country findByName(String name)
	{
		long begin = System.currentTimeMillis();

		if (log.isDebugEnabled()) log.debug("attempting to retrieve Country with name '" + name + "'");

		
		// do a case-insensitive lookup
		List l = this.getSession().createCriteria(Country.class).add( Restrictions.eq("name", name).ignoreCase() ).list();
	//	List l = getHibernateTemplate().find("FROM biz.janux.geography.Country as c WHERE c.name=?", name);
		
		Country country = (l != null && l.size()>0) ? (Country)l.get(0) : null;

		if (country != null)
			if (log.isInfoEnabled()) recordTime("successfully retrieved: " + country, begin);
		else
			log.warn("unable to find Country with name: '" + name + "'");

		return country;
	}
	
}
