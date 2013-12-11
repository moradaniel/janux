package org.janux.bus.persistence;

/**
 ***************************************************************************************************
 * Utility class that provides an Integer identity field, date created/updated fields and a String random
 * 	
 * @author  <a href="mailto:albertobuffagni@gmail.com">Alberto Buffagni</a>
 * @since 0.4
 ***************************************************************************************************
 */
public abstract class PersistentVersionableIdentifiableAbstract extends PersistentVersionableAbstract 
	implements Identifiable
{
	private String uuid;

	public String getUuid() {
		return this.uuid;
	}
	
	public void setUuid(String aUuid) 
	{
		if (aUuid == null || "".equals(aUuid)) {
			throw new IllegalArgumentException("Cannot set a UUID to a null or empty string");
		}

		if (this.uuid != null && !this.uuid.equals(aUuid)) {
			throw new UnsupportedOperationException("A UUID is immutable and cannot be changed");
		}

		this.uuid = aUuid;
	}	
	
} 
