package biz.janux.people;


//import org.apache.commons.logging.LogFactory;
//import org.apache.commons.logging.Log;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 ***************************************************************************************************
 * Represents a Person in a variety of contexts
 *
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version $Revision: 1.8 $ - $Date: 2007-05-01 23:20:58 $
 ***************************************************************************************************
 */
public class PersonImpl extends PartyAbstract implements Person
{
	private static final long serialVersionUID = 6924147515503069793L;
	private PersonName name;


	/** plain vanilla constructor */
	public PersonImpl() {}


	public PersonName getName() 
	{
		if(this.name == null)
			this. name = new PersonNameImpl();

		return this.name;
	}
	
	public void setName(PersonName name) {
		this.name = name;
	}


	public PartyName getPartyName() {
		return this.getName();
	}
	
	/*
	public void setPartyName(PartyName partyName) {
		// do nothing;
	}
	*/

	public String toString() 
	{
		return new ToStringBuilder(this)
			.append("name", getName())
			.toString();
	}

	public Object clone() 
	{
        PersonImpl result = (PersonImpl) super.clone();
        if (this.name != null)
        {
            result.name = (PersonName )this.name.clone();
        }
        return result;
	}
	
	
} // end class PersonImpl
