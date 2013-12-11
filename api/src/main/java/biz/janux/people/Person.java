package biz.janux.people;

/** represents a physical Person in a variety of contexts */
public interface Person extends Party
{		
	PersonName getName();
	public void setName(PersonName name);
}
