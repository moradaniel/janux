package org.janux.adapt;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


public class PropertyMetadataImpl implements PropertyMetadata
{
	private static final long serialVersionUID = -7194852247759431086L;
	/** the name (key) of the property */
	private final String name;
	/** the display name of the property */
	private final String label;
	/** identifies the datatype for the property */
	private DataType dataType;
    /** property validator */
	private PropertyValidator validator;
	
	public PropertyMetadataImpl(final String aName, final String aLabel, final DataType aDataType)
	{
	    name = aName;
	    label = aLabel;
	    dataType = aDataType;
	}
	
	
	public DataType getDataType()
	{
		return dataType;
	}

	public String getName()
	{
		return name;
	}

	public String getLabel()
	{
		return label;
	}

	
	public PropertyValidator getValidator()
	{
		return validator;
	}


	public void setValidator(PropertyValidator validator)
	{
		this.validator = validator;
	}


	/**  Two metadata objects are equal if the names are equal */
	public boolean equals(final Object aObject)
	{
		if (this == aObject) 
		{
			return true;
		}
		
		if (!(aObject instanceof PropertyMetadataImpl)) 
		{
			return false;
		}
		
		final PropertyMetadataImpl thatObject = (PropertyMetadataImpl)aObject; 

		return new EqualsBuilder()
			.append(this.getName(), thatObject.getName())
			.isEquals();
	}

	public int hashCode() 
	{
		return new HashCodeBuilder()
		.append(this.getName())
		.toHashCode();
	}

	@SuppressWarnings("unchecked")
	public Object clone() 
	{
	    try 
	    {
	        PropertyMetadataImpl result = (PropertyMetadataImpl) super.clone();
	    
	        if (this.validator instanceof PropertyValidator)
	        {
	            result.validator = (PropertyValidator) this.validator.clone();
	        }
	        
	        return result;
	    } 
	    catch (CloneNotSupportedException e) 
	    {
	        return null;
	    }
	}
	
}
