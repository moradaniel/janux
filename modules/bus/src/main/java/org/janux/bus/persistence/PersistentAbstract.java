package org.janux.bus.persistence;

import java.io.Serializable;

/**
 ***************************************************************************************************
 * Utility class that provides an Integer identity field
 * 	
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.1
 ***************************************************************************************************
 */
public abstract class PersistentAbstract implements Persistent,Serializable
{
	protected Integer id = new Integer(UNSAVED_ID);

	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
} // end class PersistentAbstract
