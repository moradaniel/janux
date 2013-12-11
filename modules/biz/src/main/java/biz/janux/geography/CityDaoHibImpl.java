package biz.janux.geography;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.janux.bus.persistence.DataAccessHibImplAbstract;
import org.janux.bus.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;

/**
 * Used to create, save, retrieve, update and delete City objects from
 * persistent storage
 *
 * @author  <a href="mailto:philippe.paravicini@janux.biz">Philippe Paravicini</a>
 * @version $Id: CityDaoHibImpl.java,v 1.4 2007-12-18 20:41:38 philippe Exp $
 * @since 2006-02-17
 * 
 * @deprecated use {@link CityDaoHibImplGeneric}
 */
public class CityDaoHibImpl extends DataAccessHibImplAbstract implements CityDao
{
	public City load(Integer id) throws EntityNotFoundException
	{
		long begin = System.currentTimeMillis();

		if (log.isDebugEnabled()) log.debug("attempting to load City with id '" + id + "'");

		City city;

		try {
			city = (City)getHibernateTemplate().load(CityImpl.class, id);
		} 
		catch (DataAccessException e) {
			String msg = "Unable to load City with id " + id;
			log.error(msg, e);
			throw new EntityNotFoundException(msg,e);
		}

		if (log.isInfoEnabled()) recordTime("successfully retrieved: " + city, begin); 

		return city;
	}


	public City findByName(StateProvince state, String cityName)
	{
		long begin = System.currentTimeMillis();

		// Object[] parms = {state, cityName};

		final Criteria criteria = this.getSession().createCriteria(City.class);
		criteria.add( Restrictions.eq("state", state) );
		criteria.add( Restrictions.eq("name", cityName).ignoreCase() );
		List l = criteria.list();
		
		//	List l = getHibernateTemplate().find(
		//	"FROM biz.janux.geography.City as city WHERE city.state=? and city.name=?", parms);

		City city = (l.size()>0) ? (City)l.get(0) : null;

		if (city != null) {
			log.debug("city name: " + city.getName());
			if (log.isInfoEnabled()) recordTime("successfully retrieved: " + city, begin);
		}
		else
			log.warn("unable to find City with name: '" + cityName + "' in state " + state);

		return city;
	}


	public City findByName(String countryCode, String stateCode, String cityName)
	{
		long begin = System.currentTimeMillis();

		//	Object[] parms = {countryCode, stateCode, cityName};

		final Criteria criteria = this.getSession().createCriteria(City.class);
		criteria.createAlias("state","state");
		criteria.createAlias("state.country","country");
		criteria.add( Restrictions.eq("country.code", countryCode).ignoreCase() );
		criteria.add( Restrictions.eq("state.code", stateCode).ignoreCase() );
		criteria.add( Restrictions.eq("name", cityName).ignoreCase() );
		List l = criteria.list();
		
		//	List l = getHibernateTemplate().find(
		//			"FROM biz.janux.geography.City as city WHERE city.state.country.code=? and city.state.code=? and city.name=?", parms);

		City city = (l.size()>0) ? (City)l.get(0) : null;

		if (city != null) {
			if (log.isInfoEnabled()) recordTime("successfully retrieved: " + city, begin);
		}
		else
			log.warn("unable to find City with name: '" + cityName + "' in country '" + countryCode + "' and state '" + stateCode + "'");

		return city;
	}


	public City newCity(StateProvince state, String cityName)
	{
		return new CityImpl(state, cityName);
	}

}
