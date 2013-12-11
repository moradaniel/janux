package org.janux.adapt;

import java.math.BigDecimal;

public interface PrimitiveProperty extends Property
{
	/**
	 * Returns the property value
	 */
	String getStringValue();
	
	/**
	 * Returns the property value
	 */
	Integer getIntegerValue();
	
	
	/**
	 * Returns the property value
	 */
	Boolean getBooleanValue();
	
	/**
	 * Returns the property value
	 */
	BigDecimal getBigDecimalValue();
}
