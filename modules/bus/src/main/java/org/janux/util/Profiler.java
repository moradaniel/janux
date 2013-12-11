package org.janux.util;

import org.apache.commons.logging.Log;

/**
 ***************************************************************************************************
 * Utility class that provides profiling convenience methods
 * 
 * @author   Philippe Paravicini
 * @version  $Revision: 1.1.1.1 $ - $Date: 2005-10-18 02:24:02 $
 ***************************************************************************************************
 */
public class Profiler
{
	/** 
	 * convenience method to log profile messages at the INFO level 
	 * - wrap this method in a 'isInfoEnabled' if statement to minimize logging overhead
	 */
	public static void recordTime(Log log, String msg, long startTime)
	{
		long end = System.currentTimeMillis();
		log.info(msg + " in " + (end-startTime) + "ms");
	}

} // end class
