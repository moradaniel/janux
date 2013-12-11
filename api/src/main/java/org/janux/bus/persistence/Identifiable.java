package org.janux.bus.persistence;

import java.io.Serializable;

/**
 ***************************************************************************************************
 * Defines a random alphanumeric string that uniquely identifies the objects.
 * This is the code that external clients must use to reference the objects.
 * 	
 * @author  <a href="mailto:albertobuffagni@gmail.com">Alberto Buffagni</a>
 * @version $Revision:  $ - $Date: 2011-01-12 00:00:00 $
 ***************************************************************************************************
 */
public interface Identifiable extends Serializable {

	/**       
	 * A random alphanumeric string that uniquely identifies the objects.
	 */
	public String getUuid();
	public void setUuid(String uuid);
		
}
