package org.janux.adapt;

public interface PropertyValidator extends Cloneable
{
   void validate(final Object aValue) throws PropertyValidationException;
   
   Object clone();
}
