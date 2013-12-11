package biz.janux.geography;


import java.util.Map;

import org.janux.bus.persistence.EntityNotFoundException;

import biz.janux.geography.City;
import biz.janux.geography.Country;
import biz.janux.geography.CountryImpl;
import biz.janux.geography.GeographyService;
import biz.janux.geography.PostalAddress;
import biz.janux.geography.StateProvince;
import biz.janux.geography.StateProvinceImpl;

public class GeographyServiceMockImpl implements GeographyService{

	public City findCityByName(StateProvince arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public City findCityByName(String arg0, String arg1, String arg2) {
		// TODO Auto-generated method stub
		return null;
	}

	public Country findCountryByCode(String arg0) {
		Country country = new CountryImpl();
		country.setCode("US");
		country.setName("United States");
		return country;
	}

	public Country findCountryByName(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public StateProvince findStateByCode(String arg0, String arg1) {
		StateProvince state = new StateProvinceImpl();
		state.setCode("CA");
		state.setName("California");
		return state;
	}

	public StateProvince findStateByName(String arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map findStatesByCountry(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map loadAllCountries() {
		// TODO Auto-generated method stub
		return null;
	}

	public Country loadCountry(Integer arg0) throws EntityNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	public StateProvince loadUnknownState(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public City newCity(StateProvince arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveCity(City arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setCityStateCountry(PostalAddress arg0) {
		// TODO Auto-generated method stub
		
	}

}
