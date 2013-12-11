package biz.janux.payment;

import org.apache.commons.lang.builder.ToStringBuilder;

import org.janux.bus.persistence.PersistentVersionableAbstract;
import org.janux.util.JanuxToStringStyle;

/**
 ***************************************************************************************************
 * Represents the credit card company (Visa, Mastercard, American Express, etc...; should be a java
 * Enumeration, but we want to be able to store/edit the list outside the java code, so we created a
 * simple pojo to represent this class
 *
 * @author <a href="mailto:philippe.paravicini@janux.org">philippe.paravicini@janux.org</a>
 * @since 0.4
 ***************************************************************************************************
 */
public class CreditCardTypeImpl extends PersistentVersionableAbstract implements CreditCardType
 {
	public static int DEFAULT_SORT_ORDER = 99;

	private String code;
	private String description;
	private int    sortOrder = CreditCardTypeImpl.DEFAULT_SORT_ORDER;

	public CreditCardTypeImpl() { }
	
	public CreditCardTypeImpl(String code, String description)
	{
		this.code = code;
		this.description = description;
	}
   
	public CreditCardTypeImpl(String code, String description, int sortOrder)
	{
		this(code, description);
		this.sortOrder = sortOrder;
	}
   

	public String getCode() {
		return this.code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}


	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}


	public int getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(int i) {
		this.sortOrder = i;
	}


	public String toString() 
	{
		ToStringBuilder sb = new ToStringBuilder(this, JanuxToStringStyle.COMPACT);

		if (getId() != null)          sb.append("id", getId());
		if (getCode() != null)        sb.append("code", getCode());
		if (getDescription() != null) sb.append("descr", getDescription());
		sb.append("sort", getSortOrder());
		if (getDateCreated() != null) sb.append("created", getDateCreated());
		if (getDateUpdated() != null) sb.append("updated", getDateUpdated());

		return sb.toString();
	}


	public boolean equals(Object other)
	{
		if ( (this == other) ) return true;
		if ( !(other instanceof CreditCardType) ) return false;

		CreditCardType castOther = (CreditCardType)other; 

		return new org.apache.commons.lang.builder.EqualsBuilder()
			.append(this.getCode(), castOther.getCode())
			.isEquals();
	}

	public int hashCode() 
	{
		return new org.apache.commons.lang.builder.HashCodeBuilder()
			.append(this.getCode())
			.toHashCode();
	}   

} // end class CreditCardTypeImpl
