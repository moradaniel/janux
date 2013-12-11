package biz.janux.geography;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import org.janux.bus.persistence.PersistentAbstract;
import org.janux.util.JanuxToStringStyle;

/**
 ***************************************************************************************************
 * Simple bean representing a City
 * 	
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version $Revision: 1.10 $ - $Date: 2007-12-18 20:31:22 $
 ***************************************************************************************************
 */
public class CityImpl extends PersistentAbstract implements City
{
	private static final long serialVersionUID = 20071204;
	private String code;
	private String name;
	private StateProvince state = new StateProvinceImpl();


	/** plain vanilla constructor */
	public CityImpl() {}

	public CityImpl(StateProvince state, String name) {
		this.setState(state);
		this.setName(name);
	}



	/** An optional business code by which an industry may identify a City */
	public String getCode() {
		return this.code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}


	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * A City belongs to a Country implicitly by way of a StateProvince; nevertheless,
	 * it is conceivable that there may be times when we may know the Country in which
	 * a City is located, but not its StateProvince; rather than having to maintain a
	 * direct and explicit relationship between City and Country, which must be
	 * harmonized with the implicit relationship via a StateProvince, we instead
	 * define a default 'Unknown' StateProvince for each Country, which can be used to
	 * relate a City to a Country when the StateProvince is not known.
	 */
	public StateProvince getState() {
		return this.state;
	}
	
	public void setState(StateProvince state) {
		this.state = state;
	}


	public StateProvince getProvince() {
		return this.getState();
	}

	public void setProvince(StateProvince o) {
		this.setState(o);
	}


	public Country getCountry() {
		return this.getState().getCountry();
	}

	public void setCountry(Country o) {
		this.getState().setCountry(o);
	}


	public String toString() 
	{
		ToStringBuilder sb = new ToStringBuilder(this, JanuxToStringStyle.COMPACT);

		if (getId()    != -1)    sb.append("id", getId());
		if (getCode()  != null)  sb.append("code", getCode());
		if (getName()  != null)  sb.append("name", getName());
		if (getState() != null)  sb.append("state", getState());

		return (sb.toString());
	}
	
	/** 
	 * two cities are equal if they share the same code, name and are in the
	 * same city; the code and name comparison are case-insensitive
	 */
	public boolean equals(Object other)
	{
		if ( (this == other ) ) return true;
		if ( !(other instanceof CityImpl) ) return false;
		City castOther = (City)other; 

		String thisCode  = (this.getCode() instanceof String) ? this.getCode().toUpperCase() : null;
		String otherCode = (castOther.getCode() instanceof String) ? castOther.getCode().toUpperCase() : null;

		String thisName  = (this.getName() instanceof String) ? this.getName().toUpperCase() : null;
		String otherName = (castOther.getName() instanceof String) ? castOther.getName().toUpperCase() : null;

		return new EqualsBuilder()
			.append(thisCode, otherCode)
			.append(thisName, otherName)
			.append(this.getState(), castOther.getState())
			.isEquals();
	}


	public int hashCode() 
	{
		String thisCode  = (this.getCode() instanceof String) ? this.getCode().toUpperCase() : null;
		String thisName  = (this.getName() instanceof String) ? this.getName().toUpperCase() : null;

		return new HashCodeBuilder()
			.append(thisCode)
			.append(thisName)
			.append(this.getState())
			.toHashCode();
	}   
} // end class CityImpl
