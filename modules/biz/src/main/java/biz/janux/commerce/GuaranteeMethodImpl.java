package biz.janux.commerce;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.janux.bus.persistence.PersistentAbstract;

public class GuaranteeMethodImpl extends PersistentAbstract implements GuaranteeMethod, Serializable, Cloneable
{
	private static final long serialVersionUID = -764599083970448607L;
	private String code;
	private String description;

	
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
	
	/**  Two guarantee methods are equal if all the codes are equal */
	public boolean equals(final Object aObject)
	{
		if (this == aObject) 
		{
			return true;
		}
		
		if (!(aObject instanceof GuaranteeMethod)) 
		{
			return false;
		}
		
		final GuaranteeMethod thatMethod = (GuaranteeMethod)aObject; 

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
	        GuaranteeMethodImpl result = (GuaranteeMethodImpl )super.clone();
	        
	        result.setId(-1);
	        
	        return result;
	    } 
	    catch (CloneNotSupportedException e) 
	    {
	        return null;
	    }
	}
	
}
