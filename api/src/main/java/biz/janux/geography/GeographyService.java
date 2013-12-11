package biz.janux.geography;

import java.util.Map;

import org.janux.bus.persistence.EntityNotFoundException;
//import org.janux.bus.persistence.DataAccessObject;


/**
 * Used to create, save, retrieve, update and delete Geographical entities from
 * persistent storage
 *
 * The biz.janux.geography package strives to treat City/State/Province/Country 
 * as entities, rather than as mere strings within an address (though it also
 * supports the string-based approach); by treating geographical subdivisions
 * as entities, it becomes possible, for example, to store information about
 * these geographical subdivisions, or to easily derive geography-based
 * statistics.  
 * <p>
 * This approach presents challenges at the implementation level, as it may
 * not be possible, or practical, to pre-store every city in a single
 * Country, or every state in every Country.
 * </p><p>
 * Thus, the implementing class must choose how it will deal with the
 * different use cases that may arise in a specific application.  The default
 * implementation considers the following three use-cases, which should
 * satisfy most situations:
 * <ol>
 * <li>
 * The city is entered through a free-form input box, and the State/Province
 * and Country fields are selected via pre-populated drop-downs.
 * </li><li>
 * The City, State/Province and Country fields are entered as free-form
 * strings where the application has no control over the formatting of these
 * strings (for example they are received from a third-party or legacy system
 * via an integration channel)
 * </li>
 * <li>A combination of the two cases above.</li>
 * </ol>
 * <p>
 * The default implementation stores Postal Addresses in the following
 * manner. If the Postal Address
 * </p>
 * <ul>
 * 	<li><p><b>contains a match to a City entity that exists
 * 	in the database</b> (application recognizes a city, State/Province, and Country
 * 	combination) than the Postal Address is matched against the
 * 	corresponding City entity (which contains references to its enclosing
 * 	State/Province and Country)
 * 	</p></li>
 * 	<li><p>
 * 	<b>contains a match to a State/Province entity that exists in the
 * 	database</b> (application recognizes State/Province and Country
 * 	combination). In such a case, a new City entity is created with the name
 * 	supplied, and the Postal Address will contain a reference to this City
 * 	entity.  This would be the Use Case, for example, when a User is entering
 * 	an address using pre-populated Country and State/Province drop-downs, and a
 * 	free-form City.	Note that this is imperfect and may mean that two records
 * 	may be created for the same city, if two different spellings are given for
 * 	the City, e.g.  New York vs. NewYork.
 * 	</p></li>
 * 	<li><p>
 * 	<b>does not contain a match to a State/Province entity that exists in the
 * 	database</b>, than all fields in the Postal Address are stored using the
 * 	strings contained in the PostalAddress instance, and no attempt is made to
 * 	create City or State/Province entities.  This would be the Use Case where
 * 	the City, State/Province and Country are entered via free-form strings and
 * 	it is not possible or desirable to match these to pre-existing entities.
 * 	</p></li>
 * </ul>
 * <p>Note that the default implementation includes a list of all Countries
 * in the world and their ISO code, as well as all States in the United States,
 * and all Provinces in Canada. Also, it contains a special 'UNKNOWN'
 * State/Province for each Country when it is desireable to associate a City to
 * a Country, without using a State/Province.
 * </p><p>
 * As a result of all the above, the default implementation has the following
 * characteristics:
 * </p>
 * <ul>
 * <li>Neither new Countries or new State/Provinces are ever created when
 * saving Postal Addresses</li>
 * <li>Postal Addresses are saved as Strings whenever the database does not
 * contain a list of State/Provinces for a particular Country (unless the
 * special 'UNKNOWN' State/Province is used)</li>
 * <li>Conversely, new City entities are created for those Countries for which
 * the database contains a list of State/Provinces (US and Canada by default),
 * and the State/Province can be matched</li>
 * </p><p>
 * This default implementation, while making a step in the direction of
 * relating Postal Adresses to Geographical Entities (City, State/Province,
 * Country), still leaves the possibility open of there being duplicate cities
 * with different spellings. An improvement of this default implementation
 * would be to ask the User to disambiguate close spellings. For example, if
 * the User enters 'NewYork', a dialog box would display that says "did you
 * mean 'New York' ?" and provides a list of closely spelled alternatives.  An
 * approximate match algorythm could be used to implement such a flow.  An
 * other alternative would be to integrate with a third-party geographic
 * service, such as ESRI or google maps.
 * </p>
 *
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @author  <a href="mailto:david.fairchild@janux.org">David Fairchild</a>
 * @version $Id: GeographyService.java,v 1.6 2008-03-31 20:40:37 philippe Exp $
 * @since 2005-10-01
 *
 */
public interface GeographyService
{
	/** 
	 * loads a Country object from persistence using its id
	 *
	 * @param id the internal identifier of the Country
	 * @throws EntityNotFoundException if a Country object with that id is not found
	 */
	public Country loadCountry(Integer id) throws EntityNotFoundException;

	/**
	 * Returns a map of all Countries in the system, keyed by ISO code; 
	 * the keys of the map may be ordered by the implementation 
	 * (for example according to Country.getSortOrder())
	 *
	 * @since 2005-02-21
	 */
	public Map loadAllCountries();

	/** 
	 * retrieves a Country using a standard ISO code, or returns
	 * <code>null</code> if a Country with that code is not found 
	 *
	 * @param code the two-letter ISO country code
	 */
	public Country findCountryByCode(String code);

	/** 
	 * retrieves a Country by performing a cap-insensitive search by name, 
	 * or returns <code>null</code> if a Country with that name is not found 
	 *
	 * @param name the name in the default system language
	 */
	public Country findCountryByName(String name);


	/** 
	 * retrieves a State using a country code and state code, where the country
	 * code is a standard ISO code, or returns <code>null</code> if a State with
	 * those codes is not found 
	 *
	 * @param countryCode a two-letter ISO country code
	 * @param stateCode a country-specific state abbreviation code
	 */
	public StateProvince findStateByCode(String countryCode, String stateCode);

	/** 
	 * retrieves a State by performing a cap-insensitive search by name, within a
	 * country with the specified ISO country code, or returns <code>null</code> if a
	 * State with that name is not found 
	 *
	 * @param countryCode a two-letter ISO country code
	 * @param stateName the name of a state in the default system language
	 */
	public StateProvince findStateByName(String countryCode, String stateName);

	/** 
	 * loads the State object used to link City and Country in the event that the
	 * specific StateProvince to which the City belongs has not been defined
	 *
	 * @param countryCode the two-letter ISO code for a country
	 *
	 * @throws IllegalStateException if an 'Unknown' StateProvince has not been
	 * created for that Country in the system
	 */
	public StateProvince loadUnknownState(String countryCode);

	/** 
	 * loads all the State/Provinces that exist within a Country in the system
	 *
	 * @param countryCode the two-letter ISO code for a country
	 * @since 2005-02-21
	 */
	public Map findStatesByCountry(String countryCode);


	/** 
	 * retrieves a City within a country and state by performing a
	 * cap-insensitive search by name, or returns <code>null</code> if a City
	 * with that name is not found 
	 *
	 * @param state a StateProvince that has been persisted in the system
	 * @param cityName the name of a city in the default system language
	 */
	public City findCityByName(StateProvince state, String cityName);

	/** 
	 * retrieves a City within a country and state by performing a
	 * cap-insensitive search by name, and using the ISO country code and a state
	 * code, or returns <code>null</code> if a City with that name is not found 
	 *
	 * @param countryCode a two-letter ISO country code
	 * @param stateCode a country-specific state abbreviation code
	 * @param cityName the name of a city in the default system language
	 */
	public City findCityByName(String countryCode, String stateCode, String cityName);

	/** 
	 * instantiates a new City within the specified StateProvince, with the name
	 * provided
	 *
	 * @param state a name that has been persisted in the system
	 * @param cityName the name for the new system, in the default system
	 * language
	 */
	public City newCity(StateProvince state, String cityName);

	/** adds a city to the system */
	public void saveCity(City city);
	
	/**
	 * Checks to see whether the PostalAddress provided is associated with 
	 * Geographic Entities (City, StateProvince and/or Country), based on the
	 * contents of the PostalAddress AsString fields
	 */
	public void setCityStateCountry(final PostalAddress aPostalAddress);

}
