package org.janux.bus.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;
import java.io.OutputStream;


/**
 ***************************************************************************************************
 * Base implementation of the ObjectFormatter interface; will implement a template Processor.process method
 * at some point in the future, currently throws an UnsupportedOperationException
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version $Revision: 1.2 $ - $Date: 2007-12-27 00:51:17 $
 ***************************************************************************************************
 */
public abstract class ObjectFormatterAbstract implements ObjectFormatter
{
	protected transient Log log = LogFactory.getLog(this.getClass());

	public void process(InputStream in, OutputStream out) 
	{
		String msg = "process(in,out) not yet implemented"; 
		log.error(msg);
		throw new UnsupportedOperationException(msg);
	}

	public abstract String format(Object o);
	

} // end class
