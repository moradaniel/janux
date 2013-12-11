package org.janux.adapt;

import java.io.Serializable;

public interface PropertyMetadata extends Serializable, Cloneable
{
	public enum DataType {UNDEFINED, BOOLEAN, NUMERIC, STRING, DATE, TIME, COMPOSITE}
	
	DataType getDataType();

	String getName();

	String getLabel();
	
	PropertyValidator getValidator();

	void setValidator(PropertyValidator validator);
	
	Object clone();
}
