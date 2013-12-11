package org.janux.adapt;

import java.math.BigDecimal;

import org.janux.adapt.PropertyMetadata.DataType;

public class PrimitivePropertyImpl implements PrimitiveProperty
{
	private static final long serialVersionUID = 6349358087511450551L;
	private final PropertyMetadata propertyMetadata;
    private Object value;
    
    public PrimitivePropertyImpl(final String aName, final DataType aDataType)
    {
    	this( new PropertyMetadataImpl(aName, aName, aDataType) );
    }
 
    public PrimitivePropertyImpl(final String aName, final String aLabel, final DataType aDataType)
    {
    	this( new PropertyMetadataImpl(aName, aLabel, aDataType) );
    }
    
    public PrimitivePropertyImpl(final PropertyMetadata aPropertyMetadata)
    {
    	propertyMetadata = aPropertyMetadata;
    }
    
	public PropertyMetadata getMetadata()
	{
		return propertyMetadata;
	}
	
	public Object getValue()
	{
        return value;
	}
	
	/**
	 * Sets the property value
	 * @return
	 */
	public void setValue(final Object aValue)
	{
		value = aValue;
	}
		
	
	public Object getValidValue()
	{
		try
		{
			// validate the saved value before returning it
		    validate(value);
		    return value;
		}
		catch (PropertyValidationException e)
		{
			// this should never happen, it means a property value got stored that is invalid
			// if this happens, then it is a programming error that must be fixed
		    throw new IllegalStateException(e);	
		}
	}
	
	
	/**
	 * Sets the property value
	 * @return
	 * @throws PropertyValidationException 
	 */
	public void setValidValue(final Object aValue) throws PropertyValidationException
	{
	    // validate the arg aValue before saving it
	    validate(aValue);
	    value = aValue;
	}
		
	
	
	/**
	 * Checks the current property value
	 * @return
	 */
	public void validate() throws PropertyValidationException
	{
	    validate(value);
	}
	
	/**
	 * Checks the current property value
	 * @return
	 */
	private void validate(final Object aValue) throws PropertyValidationException
	{
		if (propertyMetadata.getValidator() != null)
		{
			try
			{
			    propertyMetadata.getValidator().validate(aValue);
			}
			catch (PropertyValidationException e)
			{
				e.setProperty(this);
				throw e;
			}
		}
	}
	
	
	/**
	 * Returns the property value
	 * @return
	 */
	public String getStringValue()
	{
		String stringValue = null;
		
		if (value instanceof String)
		{
			stringValue = (String) value;
		}
		else if (value != null)
		{
			throw new IllegalArgumentException("Property " + propertyMetadata.getName() + " is not a string type.  type = " + value.getClass().getName());
		}
		
		return stringValue;
	}
	
	/**
	 * Returns the property value
	 * @return
	 */
	public Integer getIntegerValue()
	{
		Integer intValue = null;
		
		if (value instanceof Integer)
		{
			intValue = (Integer) value;
		}
		else if (value instanceof Number)
		{
			intValue = ((Number) value).intValue();
		}
		else if (value != null)
		{
			throw new IllegalArgumentException("Property " + propertyMetadata.getName() + " is not an integer type.  type = " + value.getClass().getName());
		}
		
		return intValue;
	}
	
	
	/**
	 * Returns the property value
	 * @return
	 */
	public Boolean getBooleanValue()
	{
		Boolean boolValue = null;
		
		if (value instanceof Boolean)
		{
			boolValue = (Boolean) value;
		}
		else if (value != null)
		{
			throw new IllegalArgumentException("Property " + propertyMetadata.getName() + " is not a boolean type.  type = " + value.getClass().getName());
		}
		
		return boolValue;
	}
	
	/**
	 * Returns the property value
	 * @return
	 */
	public BigDecimal getBigDecimalValue()
	{
		BigDecimal bigDecimal = null;
		
		if (value instanceof BigDecimal)
		{
			bigDecimal = (BigDecimal) value;
		}
		else if (value instanceof Number)
		{
			bigDecimal =  new BigDecimal(((Number) value).doubleValue());
		}
		else if (value != null)
		{
			throw new IllegalArgumentException("Property " + propertyMetadata.getName() + " is not a bigdecimal type.  type = " + value.getClass().getName());
		}
		
		return bigDecimal;
	}
	
	public void accept(PropertyVisitor visitor)
	{
		visitor.visit(this);
	}
	
	
	public Object clone() 
	{
    	final PropertyMetadata metadata = (PropertyMetadata) this.propertyMetadata.clone();
        PrimitivePropertyImpl result = new PrimitivePropertyImpl(metadata);
        result.value = this.value;
        
        return result;
	}
}
