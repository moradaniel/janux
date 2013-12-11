package org.janux.bus.processor;

import java.io.InputStream;
import java.io.OutputStream;

/**
 ***************************************************************************************************
 * Base interface to represent any sort of class that takes an input and returns an output; mostly
 * used to define classes that perform transformations to messages, whatever form those messages may
 * take (Objects, Strings, bytes, etc...) or to satisfy any part of a Request/Response process or
 * other Messaging process.
 *
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since version 0.2.03 - Jun 20, 2007
 ***************************************************************************************************
 */
public interface Processor 
{
	/** @throws RuntimeException as necessary */
	void process(InputStream in, OutputStream out);
}
