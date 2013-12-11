package org.janux.bus.processor;


/**
 ***************************************************************************************************
 * Base interface to represent any sort of class that takes an Object as it input and returns a
 * String as output; 
 *
 * Derived classes that extend ObjectFormatterAbstract, only need to implement 
 * the {@link #format(Object)} as that class provides a convenience implementation of
 * {@link Processor#process(InputStream, OutputStream)} that turns the
 * 'in' InputStream into a String, calls {@link ObjectFormatterAbstract#format(Object)},
 * and pipes the String returned to the 'out' OutputStream.
 *
 * @see ObjectFormatterAbstract
 *
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since version 0.2.04 - Jun 20, 2007
 ***************************************************************************************************
 */
public interface ObjectFormatter extends Processor
{
	String format(Object o);

} // end class


