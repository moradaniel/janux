package biz.janux.geography;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import org.janux.bus.persistence.PersistentAbstract;
import org.janux.util.JanuxToStringStyle;

/**
 ***************************************************************************************************
 * Simple bean representing a StateProvince
 * 	
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version $Revision: 1.11 $ - $Date: 2007-12-18 20:35:06 $
 * @since 2005-10-01
 ***************************************************************************************************
 */
public class StateProvinceImpl extends PersistentAbstract implements StateProvince
{
	private static final long serialVersionUID = 20071214;
	private String  code;
	private String  name;
	private Country country;
	private Integer sortOrder;
	private boolean visible = true;


	/** plain vanilla constructor */
	public StateProvinceImpl() {}


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


	public Country getCountry() {
		return this.country;
	}
	
	public void setCountry(Country country) {
		this.country = country;
	}


	public Integer getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(Integer i) {
		this.sortOrder = i;
	}


	/** 
	 * used to determine whether this State/Province should be visible to a
	 * calling client; currently this method is not part of the StateProvince interface
	 */
	public boolean isVisible() {
		return this.visible;
	}
	
	public void setVisible(boolean b) {
		this.visible = b;
	}


	public String toString() 
	{
		ToStringBuilder sb = new ToStringBuilder(this, JanuxToStringStyle.COMPACT);

		if (getId()      != -1)   sb.append("id", getId());
		if (getCode()    != null) sb.append("code", getCode());
		if (getName()    != null) sb.append("name", getName());
		if (getCountry() != null) sb.append("country", getCountry());
		sb.append("sortOrder", getSortOrder());
		sb.append("visible",   isVisible());

		return (sb.toString());
	}


	/**
	 * Two states are equal if they have the same code and are in the same
	 * Country; the code comparison is case-insensitive
	 */
	public boolean equals(Object other)
	{
		if ( (this == other ) ) return true;
		if ( !(other instanceof StateProvinceImpl) ) return false;
		StateProvinceImpl castOther = (StateProvinceImpl)other; 

		String thisCode  = (this.getCode() instanceof String) ? this.getCode().toUpperCase() : null;
		String otherCode = (castOther.getCode() instanceof String) ? castOther.getCode().toUpperCase() : null;

		return new EqualsBuilder()
			.append(thisCode, otherCode)
			.append(this.getCountry(), castOther.getCountry())
			.isEquals();
	}


	public int hashCode() 
	{
		String thisCode  = (this.getCode() instanceof String) ? this.getCode().toUpperCase() : null;

		return new HashCodeBuilder()
			.append(thisCode)
			.append(this.getCountry())
			.toHashCode();
	}   
} // end class StateProvinceImpl
