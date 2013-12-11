package biz.janux.geography;

import org.janux.bus.persistence.GenericDaoReadOnly;
import org.janux.bus.persistence.GenericDaoWrite;
import org.janux.bus.search.SearchCriteria;

/**
 ***************************************************************************************************
 * Data Access Class used to access and manage City entities
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.4
 ***************************************************************************************************
 */
public interface CityDaoGeneric<T extends City> 
	extends GenericDaoWrite<T, Integer>, 
	GenericDaoReadOnly<T, Integer, SearchCriteria>
{

	/** 
	 * retrieves a City within a country and state by performing a
	 * cap-insensitive search by name, or returns <code>null</code> if a City
	 * with that name is not found 
	 *
	 * @param state a StateProvince that has been persisted in the system
	 * @param cityName the name of a city in the default system language
	 */
	public City findByName(StateProvince state, String cityName);

	/** 
	 * retrieves a City within a country and state by performing a
	 * cap-insensitive search by name, and using the ISO country code and a state
	 * code, or returns <code>null</code> if a City with that name is not found 
	 *
	 * @param countryCode a two-letter ISO country code
	 * @param stateCode a country-specific state abbreviation code
	 * @param cityName the name of a city in the default system language
	 */
	public City findByName(String countryCode, String stateCode, String cityName);

	/** 
	 * instantiates a new City within the specified StateProvince, 
	 * with the name provided
	 *
	 * @param state a name that has been persisted in the system
	 * @param cityName the name for the new system, in the default system
	 * language
	 */
	public City newCity(StateProvince state, String cityName);

}
