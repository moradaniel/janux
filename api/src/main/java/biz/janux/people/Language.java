package biz.janux.people;

import java.io.Serializable;

import org.janux.bus.persistence.Persistent;

public interface Language extends Persistent, Serializable, Cloneable
{
	String getCode();

	void setCode(String code);

	String getDescription();

	void setDescription(String description);


	void setId(final Integer aIdNumber);
	 
	Integer getId();

	Object clone();
}
