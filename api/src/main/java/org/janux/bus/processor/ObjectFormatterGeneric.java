package org.janux.bus.processor;


/**
 ***************************************************************************************************
 * Base interface to represent any sort of class that takes an Object as it input and returns a
 * String as output; this is a version equivalent to ObjectFormatter, but which uses generics to
 * type the object being formatted
 *
 * @see StringProcessorAbstract
 *
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since version 0.4.02 - Mar 15, 2011
 ***************************************************************************************************
 */
public interface ObjectFormatterGeneric<T> extends Processor
{
	String format(T o);

} // end class


