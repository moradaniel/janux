package org.janux.adapt;

import java.util.HashSet;
import java.util.Set;

public class IntegerValidator implements PropertyValidator
{
	private Integer minValue;
	private Integer maxValue;
	private Set<Integer> picklist;
    private boolean picklistOnly;
    
    /**
     * Constructor for defining a valid range of integer values
     */
	public IntegerValidator()
	{
	    picklist = new HashSet<Integer>();
	}
    
    
    /**
     * Constructor for defining a valid range of integer values
     * @param aMinValue the minimum allowed value
     * @param aMaxValue the maximum allowed value
     */
	public IntegerValidator(final Integer aMinValue, final Integer aMaxValue)
	{
		this();
	    setMinValue(aMinValue);
	    setMaxValue(aMaxValue);
	}
	
	
    /**
     * Constructor for defining a valid range of integer values
     * @param aPicklist a list of allowed values
     */
	public IntegerValidator(final Set<Integer> aPicklist)
	{
		this();
		setPicklist(aPicklist);
		setPicklistOnly(true);
	}
	
	
	public Integer getMaxValue()
	{
		return maxValue;
	}


	public void setMaxValue(Integer maxValue)
	{
		this.maxValue = maxValue;
	}


	public Integer getMinValue()
	{
		return minValue;
	}


	public void setMinValue(Integer minValue)
	{
		this.minValue = minValue;
	}


	public Set<Integer> getPicklist()
	{
		return (new HashSet<Integer>(picklist));
	}


	public void setPicklist(Set<Integer> aPicklist)
	{
		// copy the list of integers to an internally owned HashSet
		this.picklist.clear();
		this.picklist.addAll(aPicklist);
	}


	public boolean isPicklistOnly()
	{
		return picklistOnly;
	}


	public void setPicklistOnly(boolean picklistOnly)
	{
		this.picklistOnly = picklistOnly;
	}


	public void validate(Object aValue) throws PropertyValidationException
	{
		if (!(aValue instanceof Integer))
		{
			throw new PropertyValidationException("Value must be an integer type", aValue);
		}
		
		final Integer value = (Integer )aValue;
		
		// check the min value
		if (minValue instanceof Integer)
		{
			if (value.intValue() < minValue.intValue())
			{
				throw new PropertyValidationException("Value '" + value + "' is less than the minimum allowed value of " + minValue, aValue);
			}
		}
		
		// check the max value
		if (maxValue instanceof Integer)
		{
			if (value.intValue() > maxValue.intValue())
			{
				throw new PropertyValidationException("Value '" + value + "' is greater than the maximum allowed value of " + maxValue, aValue);
			}
		}
		
		// check if given value is contained in the list
		if (picklistOnly)
		{
			if (picklist.contains(value) == false)
			{
				throw new PropertyValidationException("Value '" + value + "' is not in the list of allowed values", aValue);
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	public Object clone() 
	{
	    try 
	    {
	        IntegerValidator result = (IntegerValidator) super.clone();
	    
	        result.picklist = (Set<Integer>)((HashSet )this.picklist).clone();
	        
	        return result;
	    } 
	    catch (CloneNotSupportedException e) 
	    {
	        return null;
	    }
	}
	
}
