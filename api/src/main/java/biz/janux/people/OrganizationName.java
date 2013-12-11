package biz.janux.people;

/**
 * Simple bean to store the name of an Organization
 */
public interface OrganizationName extends PartyName
{		
	/** the full legal name of an Organization */
	String getLegal();
	void setLegal(String s);
}
