package biz.janux.geography;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.janux.bus.persistence.EntityNotFoundException;
import org.janux.bus.persistence.GenericDaoHibImpl;
import org.janux.bus.search.SearchCriteria;
import org.janux.util.Chronometer;
import org.springframework.dao.DataAccessException;

/**
 * Used to create, save, retrieve, update and delete StateProvince objects from
 * persistent storage
 *
 * @author  <a href="mailto:philippe.paravicini@janux.biz">Philippe Paravicini</a>
 * @since 0.4
 */
public class StateProvinceDaoHibImplGeneric 
	extends GenericDaoHibImpl<StateProvinceImpl, Integer, SearchCriteria> 
	implements StateProvinceDaoGeneric<StateProvinceImpl>
{
	public StateProvinceImpl load(Integer id) throws EntityNotFoundException
	{
		Chronometer timer = new Chronometer(true);

		if (log.isDebugEnabled()) log.debug("attempting to load StateProvince with id '" + id + "'");

		StateProvinceImpl state;

		try {
			state = (StateProvinceImpl)getHibernateTemplate().load(this.getPersistentClass(), id);
		} 
		catch (DataAccessException e) {
			String msg = "Unable to load StateProvince with id " + id;
			log.error(msg, e);
			throw new EntityNotFoundException(msg,e);
		}

		if (log.isInfoEnabled()) log.info("successfully retrieved state '" + state + "' in " + timer.printElapsedTime());

		return state;
	}


	public StateProvince findByCode(String countryCode, String stateCode)
	{
		Chronometer timer = new Chronometer(true);

		if (log.isDebugEnabled()) log.debug("attempting to find StateProvince with code '" + stateCode + "' in country '" + countryCode + "'");

		Object[] parms = {countryCode, stateCode};

		List l = getHibernateTemplate().find(
				"FROM biz.janux.geography.StateProvince as state WHERE state.country.code=? and state.code=?", parms);

		StateProvince state = (l.size()>0) ? (StateProvince)l.get(0) : null;

		if (state != null)
			if (log.isInfoEnabled()) log.info("successfully retrieved: '" + state + "' in " + timer.printElapsedTime());
		else
			log.warn("unable to find State with code: '" + stateCode + "' in country '" + countryCode + "'");

		return state;
	}


	public StateProvince findByName(String countryCode, String stateName)
	{
		Chronometer timer = new Chronometer(true);

		if (log.isDebugEnabled()) log.debug("attempting to find StateProvince with name '" + stateName + "' in country '" + countryCode + "'");

		// Object[] parms = {countryCode, stateName};

		final Criteria criteria = this.getSession().createCriteria(this.getPersistentClass());
		criteria.createAlias("country", "country");
		criteria.add( Restrictions.eq("country.code", countryCode).ignoreCase() );
		criteria.add( Restrictions.eq("name", stateName).ignoreCase() );
		List l = criteria.list();
		
	//	List l = getHibernateTemplate().find(
	//			"FROM biz.janux.geography.StateProvince as state WHERE state.country.code=? and state.name=?", parms);

		StateProvince state = (l.size()>0) ? (StateProvince)l.get(0) : null;

		if (state != null)
			if (log.isInfoEnabled()) log.info("successfully retrieved: '" + state + "' in " + timer.printElapsedTime());		
		else
			log.warn("unable to find State with name: '" + stateName + "' in country '" + countryCode + "'");

		return state;
	}


	public Map<String, StateProvince> findByCountry(String countryCode)
	{
		Chronometer timer = new Chronometer(true);

		if (log.isDebugEnabled()) log.debug("attempting to find all StateProvinces in country '" + countryCode + "'");

		List l =  getHibernateTemplate().find(
				"FROM biz.janux.geography.StateProvince as state WHERE state.country.code=? AND state.visible= true ORDER BY state.sortOrder", countryCode);

		Map<String, StateProvince> states = new LinkedHashMap<String, StateProvince>();

		for (Iterator i = l.iterator(); i.hasNext();)
		{
			StateProvince s = (StateProvince)i.next();
			states.put(s.getCode(), s);
		} // end for

		if (log.isInfoEnabled()) log.info("successfully retrieved: '" + states.keySet().size() + "' states in " + timer.printElapsedTime());

		return states;
	}
	
	
}
