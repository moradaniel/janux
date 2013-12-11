package biz.janux.geography;

import biz.janux.people.ContactMethod;

/**
 ***************************************************************************************************
 * Represents a Postal Address; this class aims to be independent of how an address may be
 * represented in an individual country;  StateProvince, Country and City are represented as objects
 * rather than Strings; refer to the package documentation for biz.janux.geography for more
 * information on the relationship between City, StateProvince and Country
 * <p>
 * This interface also provides getCityAsString, getStateProvinceAsString, and getCountryAsString so
 * that implementing classes can receive text values representing the geographical entities, for
 * example from a UI form. These methods can also be used in cases where it is not possible or
 * desirable to create the different geographical entitie.  For example, it may be that the Country
 * is entered by-hand, rather than through a pick list, and may contain a spelling other than the
 * one that exists in the pre-populated Country tables.  In such case, it would not be desireable to
 * create a new Country entity for the different spellings, yet it is necessary to store whatever
 * String was entered for the Country.  In effect, via these methods it is possible to completely
 * bypass the use of the Geographical entities, if in a given implementation it is desireable or
 * necessary to store all address fields as text.
 * </p><p>
 * Finally, as a convenience feature, this interface provides the methods getCityName,
 * getStateProvinceCode, getStateProvinceName, and getCountryName, which makes it possible to
 * retrieve City, State, Country Strings without concern as to whether these are stored as entities
 * or as simple Strings
 * </p><p>
 * Note that the plan is to eventually make the 'Name' fields of the different geographical entities
 * localizable, For example, it may be necessary to display a City both as 'Munich' or 'Munchen',
 * depending on the language of the site.  Hence, rather than being a single-valued field, name
 * fields will be maps of strings keyed by Language, Country, and Variant code, in the usual java
 * way.  We foresee that such future implementation will still provide the ability to only use a
 * single language when desireable, and that the methods which do not specify a locale will be
 * interpreted to be requesting the default locale.
 * </p>
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version $Revision: 1.12 $ - $Date: 2008-03-27 00:51:37 $
 ***************************************************************************************************
 */
public interface PostalAddress extends ContactMethod
{
	String getLine1();
	void setLine1(String s);

	String getLine2();
	void setLine2(String s);

	String getLine3();
	void setLine3(String s);
	
	/** 
	 * The City where the PostalAddress is located; may be null if it is not possible to ascertain the
	 * City entity in with this Postal Address is located; in such case, the city may be stored as a
	 * String in getCityAsString.  If the City is not null, the getStateProvince and getCountry fields
	 * must be the same as the StateProvince/Country of the City.
	 */
	City getCity();
	void setCity(City o);

	/** 
	 * StateProvince in which this PostalAddress is located; if getCity or getCountry fields ane not
	 * null, all three of these entities must be congruent.
	 */
	StateProvince getStateProvince();
	void setStateProvince(StateProvince o);

	String getPostalCode();
	void setPostalCode(String s);

	/** 
	 * Country in which this PostalAddress is located; if getCity or getStateProvince fields are not
	 * null, this Country must be the same as the one in which the City and StateProvince are located
	 */
	Country getCountry();
	void setCountry(Country o);


	/** 
	 * @return Convenience method that returns, if it exists, this.getCity().getName(); or,
	 * if getCity() is null, it returns getCityAsString().
	 */
	String getCityName();
	
	/** 
	 * @return Convenience method that returns, if it exists, this.getStateProvince().getName(); or,
	 * if getStateProvince() is null, it returns this.getStateProvinceNameAsString().
	 */
	String getStateProvinceName();
	
	/** 
	 * @return Convenience method that returns, if it exists, this.getStateProvince().getCode(); or,
	 * if getStateProvince() is null, it returns this.getStateProvinceNameAsString().
	 */
	String getStateProvinceCode();
	
	/** 
	 * @return Convenience method that returns, if it exists, this.getCountry().getName(); or,
	 * if getCountry() is null, it returns this.getCountryAsString().
	 */
	String getCountryName();
	
	/** 
	 * @return Convenience method that returns, if it exists, this.getCountry().getCode(); or,
	 * if getCountry() is null, it returns this.getCountryAsString().
	 */
	String getCountryCode();
	

	/** 
	 * @return Returns a String used to represent the City, in the event that this PostalAddress has
	 * not been related to a City entity 
	 */
	String getCityAsString();

	/** @param s a String that can be used to represent a city */
	void setCityAsString(String s);
	

	/**
	 * @return Returns a String used to represent the name of a StateProvince, in the event that this
	 * PostalAddress has not been related to a StateProvince entity
	 */
	String getStateProvinceAsString();

	/** @param s a String that can be used to represent a state or a province */
	void setStateProvinceAsString(String s);
	

	/**
	 * @return Returns a String used to represent the Country, in the event that this PostalAddress
	 * has not been related to a Country entity
	 */
	String getCountryAsString();

	/** @param s a String that can be used to represent a country */
	void setCountryAsString(String s);
}
