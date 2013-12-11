package biz.janux.geography;

import java.util.Map;

import org.janux.bus.persistence.EntityNotFoundException;
import org.janux.bus.persistence.DataAccessObject;



/**
 ***************************************************************************************************
 * 
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version $Revision: 1.3 $ - $Date: 2006-07-17 05:18:23 $
 * @since 2006-02-17
 * 
 * @deprecated use {@link StateProvinceDaoGeneric}
 ***************************************************************************************************
 */
public interface StateProvinceDao extends DataAccessObject
{
	/** load a StateProvince object from persistence using its id */
	StateProvince load(Integer id) throws EntityNotFoundException;

	/** 
	 * retrieves a State using a country code and state code, where the country
	 * code is a standard ISO code, or returns <code>null</code> if a State with
	 * that state and country code is not found 
	 *
	 * @param countryCode a two-letter ISO country code
	 * @param stateCode a country-specific state abbreviation code
	 */
	public StateProvince findByCode(String countryCode, String stateCode);


	/** 
	 * retrieves a State by performing a cap-insensitive search by name, within a
	 * country with the specified ISO country code, or returns <code>null</code> if a
	 * State with that name is not found 
	 *
	 * TODO: this may have to be internationalized 
	 *
	 * @param countryCode a two-letter ISO country code
	 * @param stateName the name of a state in the default system language
	 */
	public StateProvince findByName(String countryCode, String stateName);

	/** 
	 * retrieves a Map of StateProvince objects that exist within a specific Country in the system,
	 * and indexed by the code assigned to the StateProvince within that Country;
	 * ordering of the Map returned is left to the implementing class
	 *
	 * @param countryCode a two-letter ISO country code
	 */
	public Map<String, StateProvince> findByCountry(String countryCode);


	/** 
	 * loads the State object used to link City and Country in the event that the
	 * specific StateProvince to which the City belongs has not been defined
	 *
	 * @param countryCode the two-letter ISO code for a country
	 *
	 * @throws IllegalStateException if an 'Unknown' StateProvince has not been
	 * created for that Country in the system
	 *
	 * TODO: perhaps instead of throwing an Exception we should dynamically
	 * create an 'UnknownState' for that Country
	 */
	//public StateProvince loadUnknown(String countryCode);

}
