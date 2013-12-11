package biz.janux.geography;

import java.util.Map;

import org.janux.bus.persistence.EntityNotFoundException;

public class GeographyServiceImplGeneric implements GeographyService
{
	private static final String UNKNOWN_STATE_PROVINCE_CODE = "UNKNOWN";

	private CountryDaoGeneric<Country>       		countryDaoGeneric;
	private StateProvinceDaoGeneric<StateProvince> stateProvinceDaoGeneric;
	private CityDaoGeneric<City>			          cityDaoGeneric;
	
	public GeographyServiceImplGeneric(CountryDaoGeneric countryDao, StateProvinceDaoGeneric stateProvinceDao, CityDaoGeneric cityDao) 
	{
		this.countryDaoGeneric       = countryDao;
		this.stateProvinceDaoGeneric = stateProvinceDao;
		this.cityDaoGeneric          = cityDao;
	}
	

	public Country loadCountry(Integer id) throws EntityNotFoundException {
		return countryDaoGeneric.load(id);
	}


	public Map loadAllCountries() {
		return countryDaoGeneric.loadAll();
	}


	public Country findCountryByCode(String code) {
		return countryDaoGeneric.findByCode(code);
	}


	public Country findCountryByName(String name) {
		return countryDaoGeneric.findByName(name);
	}


	public StateProvince findStateByCode(String countryCode, String stateCode) {
		return stateProvinceDaoGeneric.findByCode(countryCode, stateCode);
	}


	public StateProvince findStateByName(String countryCode, String stateName) {
		return stateProvinceDaoGeneric.findByName(countryCode, stateName);
	}

	public Map findStatesByCountry(String countryCode) {
		return stateProvinceDaoGeneric.findByCountry(countryCode);
	}


	public StateProvince loadUnknownState(String countryCode) 
	{
		StateProvince state = findStateByCode(countryCode, UNKNOWN_STATE_PROVINCE_CODE);

		if (state == null) 
		{
			Country country = findCountryByCode(countryCode);

			if (country == null)
				throw new IllegalArgumentException("Invalid Country code: '" + countryCode + "'");
			else
				throw new IllegalStateException(
						"An '" + UNKNOWN_STATE_PROVINCE_CODE + 
						"' StateProvince has not been configured for Country " + country);
		}

		return state;
	}



	public City newCity(StateProvince state, String cityName) {
		return cityDaoGeneric.newCity(state, cityName);
	}


	public void saveCity(City city) {
		cityDaoGeneric.save(city);
	}

	public City findCityByName(StateProvince state, String cityName) {
		return cityDaoGeneric.findByName(state, cityName);
		
	}


	public City findCityByName(String countryCode, String stateCode, String cityName) {
		return cityDaoGeneric.findByName(countryCode, stateCode, cityName);
	}

	/** 
	 * If the PostalAddress instance passed has non-null strings for
	 * City, StateProvince, and Country, this method attempts to match these
	 * Strings with existing City, StateProvince and Country entities in the
	 * database; the Country and StateProvince are searched by Code first, and
	 * than by Name;  the City is only searched by Name. The following scenarios
	 * may result: 
	 * <ul>
	 * <li>
	 * A match is made to Country, StateProvince, and City; in such a case, the
	 * City is associated with the PostalAddress, and the AsString fields are
	 * nulled
	 * </li><li>
	 * A match is made to Country, and StateProvince, but not City: in such a
	 * case a new City is instantiated, saved, and associated with the
	 * PostalAddress.  The AsString fields are nulled.
	 * </li><li>
	 * A match is made to the Country only: the instance will contain a reference
	 * to the Country entity only, and the Country name is populated in the
	 * getCountryAsString field (it may replace a Country Code stored
	 * therein).
	 * </li><li>
	 * No match is made to a Country: in such a case, the PostalAddress instance
	 * will only contain values in the AsString fields, and no references to a
	 * City, StateProvince or Country entities.
	 * </li>
	 * </ul>
	 * <p>
	 * This results in either of 2 states when the PostalAddress instance is
	 * persisted:
	 * </p>
	 * <ul>
	 * <li>The PostalAddress contains a reference to a City entity (and implicitly to
	 * StateProvince and Country entities). The table geography_postal_address
	 * record has a non-null cityId, and null city, state and country text fields.
	 * </li>
	 * <li>The PostalAddress contains no reference to a City entity, and all address
	 * fields are stored as Strings.  The table geography_postal_address record has a
	 * null cityId field, and one or more non-null values in the city, state and
	 * country text fields.
	 * </li>
	 * </ul>
	 * 
	 * <p>
	 * If the PostalAddress has both entities and AsString values set for any of City
	 * or State or Country, this is understood to mean that the intention is to
	 * change the City, State, and/or Country associated with the PostalAddress,
	 * and the AsString values are given precedence.  
	 * </p>
	 */
	public void setCityStateCountry(final PostalAddress aPostalAddress) 
	{
		final String sCountryText = aPostalAddress.getCountryAsString();
		final String sStateText   = aPostalAddress.getStateProvinceAsString();
		final String sCityText    = aPostalAddress.getCityAsString();

		Country country = aPostalAddress.getCountry();
		
		if (sCountryText != null) 
		{
			// Try to load the country by interpreting sCountryText as Code first
			country = countryDaoGeneric.findByCode(sCountryText);

			if (country == null) 
			{
				// Try to load the country by interpreting sCountryText as Name next
				country = countryDaoGeneric.findByName(sCountryText);
			}

			// String value is given higher precedence since we are using 
			// get<City/State/Country>Name() for reading and 
			// set<City/State/Country>AsString for saving. 
			// Replacing existing country entity even if it exists.

			aPostalAddress.setCountry(country);
		}

		// If country is not set, no need to match for state and city.  
		if (aPostalAddress.getCountry() == null) 
		{
			return;
		}

		StateProvince state = aPostalAddress.getStateProvince();
		
		if (sStateText != null) 
		{
			// Try to load the state by interpreting sStateText as a State or Province Code.
			state = stateProvinceDaoGeneric.findByCode(aPostalAddress
					.getCountry().getCode(), sStateText);

			if (state == null) 
			{
				// Try to load the state interpreting sStateText as a State or Province Name.
				state = stateProvinceDaoGeneric.findByName(aPostalAddress.getCountry()
						.getCode(), sStateText);
			}

			aPostalAddress.setStateProvince(state);
		}

		// If state is not set or sCityText is not set, no need to match for city.
		if (state == null || sCityText == null) 
		{
			return;
		}

		// Try to load the city using sCityText as Name and StateProvince entity.
		City city = cityDaoGeneric.findByName(state, sCityText);

		if (city == null) 
		{
			// Create a new City entity if there is no match found from the standard 
			// lookup
			city = cityDaoGeneric.newCity(state, sCityText);
			cityDaoGeneric.save(city);
		}else{
			city.setName(sCityText);
			cityDaoGeneric.save(city);
		}

		aPostalAddress.setCity(city);
	}
}
