package biz.janux.geography;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.janux.bus.persistence.GenericDaoHibImpl;
import org.janux.bus.search.SearchCriteria;
import org.janux.util.Chronometer;
import org.springframework.dao.DataAccessException;

/**
 * Used to create, save, retrieve, update and delete City objects from
 * persistent storage
 *
 * @author  <a href="mailto:philippe.paravicini@janux.biz">Philippe Paravicini</a>
 * @since 0.4
 */

public class CityDaoHibImplGeneric 
	extends GenericDaoHibImpl<CityImpl, Integer, SearchCriteria> 
	implements CityDaoGeneric<CityImpl>
{
	public City findByName(StateProvince state, String cityName)
	{
		Chronometer timer = new Chronometer(true);

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
			if (log.isInfoEnabled()) log.info("successfully retrieved city '" + cityName + "' in " + timer.printElapsedTime() + " - " + city);
		}
		else{
			log.warn("unable to find City with name: '" + cityName + "' in state " + state);
		}
		
		return city;
	}


	public City findByName(String countryCode, String stateCode, String cityName)
	{
		Chronometer timer = new Chronometer(true);

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
			if (log.isInfoEnabled()) log.info("successfully retrieved city '" + cityName + "' in " + timer.printElapsedTime() + " - " + city);
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
