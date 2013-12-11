package biz.janux.people;

import java.io.Serializable;

/**
 * Simple bean to store a short and long name for a Person or Organization
 */
public interface PartyName extends Cloneable, Serializable
{		
	/** a short name to refer to a Person or Organization */
	String getShort();
	void setShort(String s);

	/** a long (or legal) name to refer to a Person or Organization */
	String getLong();
	void setLong(String s);
	
	Object clone();
}
