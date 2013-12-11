package biz.janux.geography;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.janux.bus.persistence.DataAccessHibImplAbstract;
import org.janux.bus.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;

/**
 * Used to create, save, retrieve, update and delete StateProvince objects from
 * persistent storage
 *
 * @author  <a href="mailto:philippe.paravicini@janux.biz">Philippe Paravicini</a>
 * @version $Id: StateProvinceDaoHibImpl.java,v 1.4 2006-07-17 05:18:23 dfairchild Exp $
 * @since 2006-02-17
 * 
 * @deprecated use {@link StateProvinceDaoGeneric} 
 */
public class StateProvinceDaoHibImpl extends DataAccessHibImplAbstract implements StateProvinceDao
{

	public StateProvince load(Integer id) throws EntityNotFoundException
	{
		long begin = System.currentTimeMillis();

		if (log.isDebugEnabled()) log.debug("attempting to load StateProvince with id '" + id + "'");

		StateProvince state;

		try {
			state = (StateProvince)getHibernateTemplate().load(StateProvinceImpl.class, id);
		} 
		catch (DataAccessException e) {
			String msg = "Unable to load StateProvince with id " + id;
			log.error(msg, e);
			throw new EntityNotFoundException(msg,e);
		}

		if (log.isInfoEnabled()) recordTime("successfully retrieved: " + state, begin); 

		return state;
	}


	public StateProvince findByCode(String countryCode, String stateCode)
	{
		long begin = System.currentTimeMillis();

		if (log.isDebugEnabled()) log.debug("attempting to find StateProvince with code '" + stateCode + "' in country '" + countryCode + "'");

		Object[] parms = {countryCode, stateCode};

		List l = getHibernateTemplate().find(
				"FROM biz.janux.geography.StateProvince as state WHERE state.country.code=? and state.code=?", parms);

		StateProvince state = (l.size()>0) ? (StateProvince)l.get(0) : null;

		if (state != null)
			if (log.isInfoEnabled()) recordTime("successfully retrieved: " + state, begin);
		else
			log.warn("unable to find State with code: '" + stateCode + "' in country '" + countryCode + "'");

		return state;
	}


	public StateProvince findByName(String countryCode, String stateName)
	{
		long begin = System.currentTimeMillis();

		if (log.isDebugEnabled()) log.debug("attempting to find StateProvince with name '" + stateName + "' in country '" + countryCode + "'");

		// Object[] parms = {countryCode, stateName};

		final Criteria criteria = this.getSession().createCriteria(StateProvince.class);
		criteria.createAlias("country", "country");
		criteria.add( Restrictions.eq("country.code", countryCode).ignoreCase() );
		criteria.add( Restrictions.eq("name", stateName).ignoreCase() );
		List l = criteria.list();
		
	//	List l = getHibernateTemplate().find(
	//			"FROM biz.janux.geography.StateProvince as state WHERE state.country.code=? and state.name=?", parms);

		StateProvince state = (l.size()>0) ? (StateProvince)l.get(0) : null;

		if (state != null)
			if (log.isInfoEnabled()) recordTime("successfully retrieved: " + state, begin);
		else
			log.warn("unable to find State with name: '" + stateName + "' in country '" + countryCode + "'");

		return state;
	}


	public Map<String, StateProvince> findByCountry(String countryCode)
	{
		long begin = System.currentTimeMillis();

		if (log.isDebugEnabled()) log.debug("attempting to find all StateProvinces in country '" + countryCode + "'");

		List l =  getHibernateTemplate().find(
				"FROM biz.janux.geography.StateProvince as state WHERE state.country.code=? AND state.visible= true ORDER BY state.sortOrder", countryCode);

		Map<String, StateProvince> states = new LinkedHashMap<String, StateProvince>();

		for (Iterator i = l.iterator(); i.hasNext();)
		{
			StateProvince s = (StateProvince)i.next();
			states.put(s.getCode(), s);
		} // end for

		if (log.isDebugEnabled()) recordTime("successfully retrieved: " + states.keySet().size() + " states", begin); 

		return states;
	}
	
	
}
