package biz.janux.people;


//import org.apache.commons.logging.LogFactory;
//import org.apache.commons.logging.Log;


/**
 ***************************************************************************************************
 * Represents an Organization
 * 	
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version $Revision: 1.8 $ - $Date: 2007-05-01 23:20:58 $
 ***************************************************************************************************
 */
public class OrganizationImpl extends PartyAbstract implements Organization
{
	private static final long serialVersionUID = 3451285864936586272L;
	private OrganizationName name;


	/** plain vanilla constructor */
	public OrganizationImpl() {}

	public PartyName getPartyName() {
		return this.getName();
	}

	/*
	public void setPartyName(PartyName name) {
		if (name != null) {
			this.getName().setShort(name.getShort());
			this.getName().setLong(name.getLong());
		}
	}
	*/

	public OrganizationName getName() 
	{
		if (this.name == null)
			this.name = new OrganizationNameImpl();

		return this.name;
	}
	
	public void setName(OrganizationName name) {
		this.name = name;
	}

	public Object clone() 
	{
        OrganizationImpl result = (OrganizationImpl) super.clone();
        if (this.name != null)
        {
            result.name = (OrganizationName )this.name.clone();
        }
        return result;
	}
	
} // end class OrganizationImpl
