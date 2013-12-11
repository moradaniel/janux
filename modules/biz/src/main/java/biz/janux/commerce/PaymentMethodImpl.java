package biz.janux.commerce;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.janux.bus.persistence.PersistentAbstract;

public class PaymentMethodImpl extends PersistentAbstract implements PaymentMethod, Serializable, Cloneable
{
	private static final long serialVersionUID = -4674930313707816709L;
	private String code;
	private String description;
	private String extraInfo;

	
	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
	
	
	/**  Two payment methods are equal if all the codes are equal */
	public boolean equals(final Object aObject)
	{
		if (this == aObject) 
		{
			return true;
		}
		
		if (!(aObject instanceof PaymentMethod)) 
		{
			return false;
		}
		
		final PaymentMethod thatMethod = (PaymentMethod)aObject; 

		return new EqualsBuilder()
			.append(this.getCode(), thatMethod.getCode())
			.isEquals();
	}

	public int hashCode() 
	{
		return new HashCodeBuilder()
		.append(this.getCode())
		.toHashCode();
	}
	
	public Object clone() 
	{
	    try 
	    {
	        PaymentMethodImpl result = (PaymentMethodImpl )super.clone();
	        
	        result.setId(-1);
	        
	        return result;
	    } 
	    catch (CloneNotSupportedException e) 
	    {
	        return null;
	    }
	}

	public String getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}
	
}
