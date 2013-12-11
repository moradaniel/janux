package biz.janux.geography;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

import org.janux.bus.persistence.PersistentAbstract;
import org.janux.util.JanuxToStringStyle;

import biz.janux.people.Party;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 ***************************************************************************************************
 * Simple bean representing a physical address
 *
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @author  <a href="mailto:david.fairchild@janux.org">David Fairchild</a>
 * @since $Revision: 1.23 $ - $Date: 2008-03-27 00:51:37 $
 ***************************************************************************************************
 */
public class PostalAddressImpl extends PersistentAbstract implements PostalAddress
{
	protected static Log log = LogFactory.getLog(PostalAddressImpl.class);

	private static final long serialVersionUID = 20071205;

	private String line1;
	private String line2;
	private String line3;
	private String postalCode;
	private String cityText;
	private String stateText;
	private String countryText;
	private City   city;
	private StateProvince stateProvince;
	private Country country;


	/** plain vanilla constructor */
	public PostalAddressImpl() {}


	public String getLine1() {
		return this.line1;
	}
	
	public void setLine1(String line1) {
		this.line1 = line1;
	}


	public String getLine2() {
		return this.line2;
	}
	
	public void setLine2(String line2) {
		this.line2 = line2;
	}


	public String getLine3() {
		return this.line3;
	}
	
	public void setLine3(String line3) {
		this.line3 = line3;
	}


	public String getPostalCode() {
		return this.postalCode;
	}
	
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}


	public City getCity() {
		return this.city;
	}
	
	/** 
	 * Assigning a City to this PostalAdress causes the cityAsString to be
	 * nulled, and the StateProvince and Country fields of this Address to be set
	 * to, respectively, city.getState() and city.getCountry()
	 */
	public void setCity(City city) 
	{
		this.city = city;
		if (city instanceof City) 
		{
			this.cityText    = null;
			this.setStateProvince(city.getState());
			this.setCountry(city.getCountry());
		}
	}


	/** 
	 * returns getCity().getState() if a City is assigned to this
	 * PostalAddress, or else the StateProvince field
	 */
	public StateProvince getStateProvince() 
	{ 
		if (this.getCity() instanceof City) {
			return this.city.getState();
		}
		else {
			return this.stateProvince;
		}
	}


	/** 
	 * Assigning a StateProvince to this PostalAdress causes the
	 * stateProvinceAsString field to be nulled, and the Country fields of this
	 * Address to be set to stateProvince.getCountry()
	 */
	public void setStateProvince(StateProvince aStateProvince) 
	{
		this.stateProvince = aStateProvince;
		if (aStateProvince instanceof StateProvince)
		{
			this.stateText = null;
			this.setCountry(aStateProvince.getCountry());
		}
	}


	/** 
	 * returns getCity().getCountry() if a City is assigned to this
	 * PostalAddress, or else the Country field
	 */
	public Country getCountry() 
	{ 
		if (this.city instanceof City) {
			return this.city.getCountry();
		}
		else {
			return this.country;
		}
	}

	/** 
	 * Assigning a Country to this address will null the countryAsString field;
	 * also, if a City is associated to this PostalAddress, and the Country
	 * assigned herewith is different from City.getCountry(), we understand that
	 * to mean that the existing City will be changed, and thus the City filed is
	 * nulled
	 */
	public void setCountry(Country aCountry) 
	{
		this.country = aCountry;

		if (aCountry instanceof Country)
		{
			this.countryText = null;
		}

		if (this.city instanceof City && !this.city.getCountry().equals(aCountry))
		{
			this.city = null;
			this.stateProvince = null;
		}
		
	}


	public String getCityAsString()
	{
		return cityText;
	}

	public String getCountryAsString()
	{
		return countryText;
	}

	public String getStateProvinceAsString()
	{	
		return stateText;		
	}

	public void setCityAsString(String cityText)
	{
		this.cityText = cityText;
	}

	public void setCountryAsString(String countryText)
	{
		this.countryText = countryText;
	}

	public void setStateProvinceAsString(String stateName)
	{
		this.stateText = stateName;
	}
	

	public String getCityName() 
	{
		if (this.getCity() != null){
			return this.getCity().getName();
		}
		else {
			return cityText;
		}
	}


	public String getCountryName() 
	{
		if (this.getCountry() != null) {
			return this.getCountry().getName();
		}
		else {
			return countryText;
		}
	}


	public String getCountryCode() 
	{
		if (this.getCountry() != null) {
			return this.getCountry().getCode();
		}
		else {
			return countryText;
		}
	}


	public String getStateProvinceName() 
	{
		if (this.getStateProvince() != null) {
			return this.getStateProvince().getName();
		}
		else {
			return stateText;
		}
			
	}


	public String getStateProvinceCode() 
	{
		if (this.getStateProvince() != null) {
			return this.getStateProvince().getCode();
		}
		else {
			return stateText;
		}
			
	}


	public void setCityName(String cityText) {
		this.cityText = cityText;
	}


	public void setCountryName(String countryText) {
		this.countryText = countryText;
	}


	public void setStateName(String stateText) {
		this.stateText = stateText;
	}


	public String toString() 
	{
		ToStringBuilder sb = new ToStringBuilder(this, JanuxToStringStyle.COMPACT);

		if (getId()         != -1)   sb.append("id", getId());
		if (getLine1()      != null) sb.append("line1", getLine1());
		if (getLine2()      != null) sb.append("line2", getLine2());
		if (getLine3()      != null) sb.append("line3", getLine3());
		if (getPostalCode() != null) sb.append("postalCode", getPostalCode());

		if (this.getCity() instanceof City) {
			sb.append("city", getCity());
		} else {
			sb.append("cityAsString",  getCityAsString())
			  .append("stateAsString", getStateProvinceAsString());

			if (this.getCountry() instanceof Country) {
				sb.append("country", getCountry());
			} else {
				sb.append("countryAsString", getCountryAsString());
			}
		}

		return sb.toString();
	}


	@SuppressWarnings("unchecked")
	public Object clone() 
	{
		try 
		{
			PostalAddressImpl result = (PostalAddressImpl) super.clone();
		
			result.setId(-1);
			
			result.line1         = this.line1;
			result.line2         = this.line2;
			result.line3         = this.line3;
			result.postalCode    = this.postalCode;

			result.country       = this.country;
			result.stateProvince = this.stateProvince;
			result.city          = this.city;		

			result.countryText   = this.countryText;
			result.stateText     = this.stateText;
			result.cityText      = this.cityText;
			
			return result;
		} 
		catch (CloneNotSupportedException e) 
		{
			return null;
		}
	}
	
} // end class PostalAddressImpl
