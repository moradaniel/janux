package org.janux.adapt;

import java.util.HashMap;
import java.util.Map;

public class CompositePropertyImpl implements CompositeProperty
{
	private static final long serialVersionUID = -2764195663700366709L;
	private final Map<String, Property> properties;
    private final PropertyMetadata propertyMetadata;
    
    
    public CompositePropertyImpl(final String aName)
    {
    	this( new PropertyMetadataImpl(aName, aName, PropertyMetadata.DataType.COMPOSITE) );
    }
    
    public CompositePropertyImpl(final String aName, final String aLabel)
    {
    	this( new PropertyMetadataImpl(aName, aLabel, PropertyMetadata.DataType.COMPOSITE) );
    }

    
    public CompositePropertyImpl(final PropertyMetadata aPropertyMetadata)
    {
		properties = new HashMap<String, Property>();
		propertyMetadata = aPropertyMetadata;
		if (propertyMetadata.getDataType().equals(PropertyMetadata.DataType.COMPOSITE) == false)
		{
			throw new IllegalArgumentException("Datatype must be composite");
		}
    }
	
	/**
	 * Clears all the property values
	 * @return
	 */
	public void clearProperties()
	{
		properties.clear();
	}
	
	public void add(final Property aProperty)
	{
		properties.put(aProperty.getMetadata().getName(), aProperty);
	}
	
	public void remove(final String aName)
	{
		properties.remove(aName);
	}
	
	
	/**
	 * Returns the named property metadata
	 * @param aName
	 * @return
	 */
	/*
	private PropertyMetadata getChildPropertyMetadata(final String aName)
	{
		if (properties.containsKey(aName))
		{
		    return getProperty(aName).getMetadata();
		}
		else
		{
			return (null);
		}
	}
	*/
	
	
	/**
	 * Returns the property value
	 * @return
	 */
	/*
	private Object getChildPropertyValue(final String aName)
	{
		if (properties.containsKey(aName))
		{
		    return getProperty(aName).getValue();
		}
		else
		{
			return (null);
		}
	}
	*/
	
	
	/**
	 * Returns the property value
	 * @return
	 */
	public PrimitiveProperty getPrimitiveProperty(final String aName)
	{
		return getPrimitiveProperty(aName, 0);
	}
	
	
	/**
	 * Returns the property value
	 * @return
	 */
	public PrimitiveProperty getPrimitiveProperty(final String aName, final int aRecursiveDepth)
	{
		final Property prop = getProperty(aName, aRecursiveDepth);
		if (prop instanceof PrimitiveProperty)
		{
			return (PrimitiveProperty) prop;
		}
		else if (prop instanceof Property)
		{
			throw new IllegalArgumentException("Property " + aName + " is not a primitive property");
		}
		else
		{
			return (null);
		}
	}
	
	
	/**
	 * Returns the property value
	 * @return
	 */
	public CompositeProperty getCompositeProperty(final String aName, final int aRecursiveDepth)
	{
		final Property prop = getProperty(aName, aRecursiveDepth);
		if (prop instanceof CompositeProperty)
		{
			return (CompositeProperty) prop;
		}
		else if (prop instanceof Property)
		{
			throw new IllegalArgumentException("Property " + aName + " is not a composite property");
		}
		else
		{
			return (null);
		}
	}
	
	
	/**
	 * Returns the property value
	 * @return
	 */
	public CompositeProperty getCompositeProperty(final String aName)
	{
		return getCompositeProperty(aName, 0);
	}
	
	
	/**
	 * Returns the property value
	 * @return
	 */
	public Property getProperty(final String aName)
	{
		return (getProperty(aName, 0));
	}
	
	
	
	/**
	 * Returns the property value
	 * @param aName the name of the propert
	 * @param aRecursiveDepth the number of levels of child properties to look into
	 * @return
	 */
	public Property getProperty(final String aName, final int aRecursiveDepth)
	{
		{
		final Property prop =  properties.get(aName);
		if (prop instanceof Property)
		{
			return prop;
		}
		}
		
		if (aRecursiveDepth > 100)
		{
			// lets not go crazy here
			throw new IllegalArgumentException("Cannot specify a depth greater than 100");
		}
		
		// search through the child composite properties
		if (aRecursiveDepth > 0)
		{
			for (Property prop : properties.values())
			{
			    if (prop instanceof CompositeProperty)
			    {
			    	final CompositeProperty compProperty = (CompositeProperty )prop;
			    	final Property childProperty = compProperty.getProperty(aName, aRecursiveDepth - 1);
			    	if (childProperty instanceof Property)
			    	{
			    		return childProperty;
			    	}
			    }
			}
		}
		
		return (null);
	}
	
	
	
	/**
	 * Returns the property value
	 * @return
	 */
	public void setValue(final String aName, final Object aValue)
	{
		final Property prop = getProperty(aName);
		if (prop instanceof Property)
		{
			prop.setValue(aValue);
		}
		else
		{
			throw new IllegalArgumentException("Property " + aName + " not defined");
		}
	}
	
	/**
	 * Returns the property value
	 * @return
	 */
	public void setValidValue(final String aName, final Object aValue) throws PropertyValidationException
	{
		final Property prop = getProperty(aName);
		if (prop instanceof Property)
		{
			prop.setValidValue(aValue);
		}
		else
		{
			throw new IllegalArgumentException("Property " + aName + " not defined");
		}
	}
		
	public void setMetadata(final String aName, final PropertyMetadata.DataType aDataType)
	{
		setMetadata(aName, aName, aDataType, null);
	}
	
	public void setMetadata(final String aName, final String aLabel, final PropertyMetadata.DataType aDataType)
	{
		final PropertyValidator validator = null;
		setMetadata(aName, aLabel, aDataType, validator);
	}
	
	public void setMetadata(final String aName, final String aLabel, final PropertyMetadata.DataType aDataType, final PropertyValidator aValidator)
	{
		final Property prop;
		if (aDataType.equals(PropertyMetadata.DataType.COMPOSITE))
		{
		    prop = new CompositePropertyImpl(aName, aLabel);
		}
		else
		{
			final PropertyMetadata metadata = new PropertyMetadataImpl(aName, aLabel, aDataType);
			metadata.setValidator(aValidator);
			prop = new PrimitivePropertyImpl(metadata);
		}
		
		properties.put(aName, prop);
	}
	
	
	/**
	 * Returns the property value
	 * @return
	 */
	/*
	private void setChildPropertyMetadata(final PropertyMetadata aPropertyMetadata)
	{
		final String sName = aPropertyMetadata.getName();
		final Property prop;
		if (aPropertyMetadata.getDataType().equals(PropertyMetadata.DataType.COMPOSITE))
		{
		    prop = new CompositePropertyImpl(sName);
		}
		else
		{
		    prop = new PrimitivePropertyImpl(aPropertyMetadata);
		}
		
		properties.put(sName, prop);
	}
	*/

	// methods to implement


	public PropertyMetadata getMetadata()
	{
		return propertyMetadata;
	}


	public Object getValue()
	{
		return getProperties();
	}

	public Object getValidValue()
	{
		return getProperties();
	}
	
	
	public Map<String, Property> getProperties()
	{
		return properties;
	}
	
	
	public CompositeProperty createCompositeProperty(final String aName)
	{
		return createCompositeProperty(aName,null);
	}
	
	public CompositeProperty createCompositeProperty(final String aName, final String aLabel)
	{
		setMetadata(aName, aLabel, PropertyMetadata.DataType.COMPOSITE);
		return getCompositeProperty(aName);
	}
	

	public void setValidValue(Object aValue)
	{
		setValue(aValue);
	}
	
	@SuppressWarnings("unchecked")
	public void setValue(Object aValue)
	{
		if (aValue instanceof Map)
		{
			final Map<String, Property> map = (Map )aValue;
		//	properties = map;
			
			properties.clear();
			properties.putAll(map);
		}
		else if (aValue instanceof CompositeProperty)
		{
			final CompositeProperty compProp = (CompositeProperty ) aValue;
		 //   properties = compProp.getProperties();
		    
		    properties.clear();
		    properties.putAll(compProp.getProperties());
		}
	}


	public void validate() throws PropertyValidationException
	{
		// call the validate method for all the child properties
		for (Property prop : properties.values())
		{
			prop.validate();
		}
	}
	
	
	public void accept(PropertyVisitor visitor)
	{
		visitor.visit(this);
		visitor.pushLevel();
		for (Property prop : properties.values())
		{
			prop.accept(visitor);
		}
		visitor.popLevel();
	}


	public int getCount()
	{
		return properties.size();
	}

	public Object clone() 
	{
    	final PropertyMetadata metadata = (PropertyMetadata) this.propertyMetadata.clone();
        CompositePropertyImpl result = new CompositePropertyImpl(metadata);
        
        // deep copy of properties
        for (String key : this.properties.keySet())
        {
        	final Property property = this.getProperty(key);
        	final Property cloneProperty = (Property )property.clone();
            result.getProperties().put(key, cloneProperty);
        }
        
        return result;
	}
	
}
