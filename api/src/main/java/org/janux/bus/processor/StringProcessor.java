package org.janux.bus.processor;


/**
 ***************************************************************************************************
 * Base interface to represent any sort of class that takes a String as input and returns a String
 * as output; mostly used to define classes that perform transformations to String messages,  or to
 * satisfy any part of a Request/Response process or other messaging process that uses String
 * Messages. 
 *
 * Derived classes that extend StringProcessorAbstract, only need to implement 
 * the {@link #process(String)} as that class provides a convenience implementation of
 * {@link Processor#process(InputStream, OutputStream)} that turns the
 * 'in' InputStream into a String, calls {@link StringProcessorAbstract#process(String)},
 * and pipes the String returned to the 'out' OutputStream.
 *
 * @see StringProcessorAbstract
 *
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since version 0.2.03 - Jun 20, 2007
 ***************************************************************************************************
 */
public interface StringProcessor extends Processor
{
	String process(String in);
}
