package org.janux.bus.processor;


/**
 ***************************************************************************************************
 * Base interface to represent any sort of class that takes a String as input and returns an Object
 * as output; mostly used to define classes that parse/unmarshall Strings into objects
 *
 * Derived classes that extend StringParserAbstract (TODO), only need to implement the 
 * {@link #parse(String in)} as that class provides a convenience implementation of 
 * {@link Processor#process} that turns the 'in' InputStream into a String, calls 
 * {@link StringProcessorAbstract#process(String in)}, and returns on ObjectOutputStream via the the
 * out OutputStream.
 *
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 ***************************************************************************************************
 */
public interface StringParser extends Processor
{
	Object parse(String in);
}
