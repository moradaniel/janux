package org.janux.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.safehaus.uuid.UUID;
import org.safehaus.uuid.UUIDGenerator;

/**
 * Generates uuid using the method version 4
 *  
 * Version 4 UUIDs use a scheme relying only on random numbers. 
 * This algorithm sets the version number as well as two reserved bits. 
 * All other bits are set using a random or pseudorandom data source. 
 * Version 4 UUIDs have the form xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx 
 * where x is any hexadecimal digit and y is one of 8, 9, A, or B. e.g. f47ac10b-58cc-4372-a567-0e02b2c3d479.
 * 
 * @author albertobuffagni@gmail.com
 *
 */
public class RandomBasedGenerator implements RandomStringGenerator {

	transient Log log = LogFactory.getLog(this.getClass());

	public synchronized String getString() {
	  UUID uuid = UUIDGenerator.getInstance().generateRandomBasedUUID();
	  return uuid.toString();
	}

}
