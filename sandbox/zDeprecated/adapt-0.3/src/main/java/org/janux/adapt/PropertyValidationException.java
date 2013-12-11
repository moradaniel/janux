package org.janux.adapt;

public class PropertyValidationException extends Exception
{
	private static final long serialVersionUID = -505673090589753944L;
	private Property property;
	private Object value;

	public PropertyValidationException(final String aMessage)
	{
	    super(aMessage);
    }
	
	public PropertyValidationException(final String aMessage, final Object aValue)
	{
	    super(aMessage);
		
	    value = aValue;
	}
	
	public PropertyValidationException(final String aMessage, final Object aValue, final Property aProperty)
	{
	    super(aMessage);
		
	    property = aProperty;
	    value = aValue;
	}
	
	public Property getProperty()
	{
		return property;
	}

	public Object getValue()
	{
		return value;
	}

	public void setProperty(Property property)
	{
		this.property = property;
	}

	public void setValue(Object value)
	{
		this.value = value;
	}
	
}
