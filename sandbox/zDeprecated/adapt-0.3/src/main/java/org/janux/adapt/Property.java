package org.janux.adapt;

import java.io.Serializable;

public interface Property extends Cloneable, Serializable
{
	PropertyMetadata getMetadata();
	
	Object getValue();
	
	Object getValidValue();
	
	void setValue(Object value);
	
	void setValidValue(Object value) throws PropertyValidationException;
	
	void validate() throws PropertyValidationException;
	
	void accept(PropertyVisitor visitor);
	
	Object clone();
}
