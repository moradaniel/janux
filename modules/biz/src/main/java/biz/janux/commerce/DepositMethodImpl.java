package biz.janux.commerce;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.janux.bus.persistence.PersistentAbstract;

public class DepositMethodImpl extends PersistentAbstract implements DepositMethod, Cloneable
{
	private static final long serialVersionUID = 8190316934776194303L;
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

	/**  Two deposit methods are equal if all the codes are equal */
	public boolean equals(final Object aObject)
	{
		if (this == aObject) 
		{
			return true;
		}
		
		if (!(aObject instanceof DepositMethodImpl)) 
		{
			return false;
		}
		
		final DepositMethod thatMethod = (DepositMethod)aObject; 

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
	        DepositMethodImpl result = (DepositMethodImpl )super.clone();
	        
	        result.setId(-1);
	        
	        return result;
	    } 
	    catch (CloneNotSupportedException e) 
	    {
	        return null;
	    }
	}
	
}
