package biz.janux.geography;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import org.janux.bus.persistence.PersistentAbstract;
import org.janux.util.JanuxToStringStyle;

/**
 ***************************************************************************************************
 * Simple bean representing a Country
 * 	
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version $Revision: 1.10 $ - $Date: 2007-12-18 20:33:31 $
 ***************************************************************************************************
 */
public class CountryImpl extends PersistentAbstract implements Country
{
	private static final long serialVersionUID = 20071204;
	private String  code;
	private int     phoneCode;
	private String  name;
	private Integer sortOrder;
	private boolean visible = true;


	/** plain vanilla constructor */
	public CountryImpl() {}


	public String getCode() {
		return this.code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}


	public int getPhoneCode() {
		return this.phoneCode;
	}
	
	public void setPhoneCode(int phoneCode) {
		this.phoneCode = phoneCode;
	}


	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}


	public Integer getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(Integer i) {
		this.sortOrder = i;
	}


	/** 
	 * used to determine whether this Country should be visible to a
	 * calling client; currently this method is not part of the Country interface
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

		if (getId()   != -1)   sb.append("id", getId());
		if (getCode() != null) sb.append("code", getCode());
		if (getName() != null) sb.append("name", getName());
		sb.append("sortOrder", getSortOrder());
		sb.append("visible",   isVisible());

		return (sb.toString());
	}


	/**
	 * Two countries are equal if they have the same code; 
	 * the comparison is case insensitive
	 */
	public boolean equals(Object other)
	{
		if ( (this == other ) ) return true;
		if ( !(other instanceof CountryImpl) ) return false;
		CountryImpl castOther = (CountryImpl)other; 

		String thisCode  = (this.getCode() instanceof String) ? this.getCode().toUpperCase() : null;
		String otherCode = (castOther.getCode() instanceof String) ? castOther.getCode().toUpperCase() : null;

		return new EqualsBuilder()
			.append(thisCode, otherCode)
			.isEquals();
	}


	public int hashCode() 
	{
		String thisCode  = (this.getCode() instanceof String) ? this.getCode().toUpperCase() : null;

		return new HashCodeBuilder()
			.append(thisCode)
			.toHashCode();
	}   

} // end class CountryImpl
