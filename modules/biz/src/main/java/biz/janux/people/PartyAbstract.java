package biz.janux.people;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
// import org.janux.adapt.CompositeProperty;
// import org.janux.adapt.CompositePropertyImpl;
import org.janux.bus.persistence.PersistentAbstract;

import biz.janux.commerce.FormOfPayment;
import biz.janux.geography.PostalAddress;
import biz.janux.people.net.Uri;
import biz.janux.people.net.Url;

/**
 ***************************************************************************************************
 * Base implementation for the class hierarchy to which a Person and an Organization belong
 * 	
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.1 2006-11-14
 ***************************************************************************************************
 */
public abstract class PartyAbstract extends PersistentAbstract implements Party
{
	private static Log log = LogFactory.getLog(PartyAbstract.class);

	private String code;
	private ContactMethodManager contactMan = new ContactMethodManager();
	// private CompositeProperty properties = new CompositePropertyImpl("ROOT");
	private List<FormOfPayment> fopList;


	/** Optional unique business identifier for this Party */
	public String getCode() {
		return this.code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}

	public abstract PartyName getPartyName();

	/**	   
	 * Telephone numbers keyed by a string code representing a user-defined type of
	 * Phone Number, such as PHYSICAL, BILLING, etc...  
	 */
	public Map<String, ContactMethod> getContactMethods() 
	{
		if (!(contactMan instanceof ContactMethodManager))
		{
			 log.warn("getContactMethods method is having to create another instance of contactMan - somehow it wasn't created with constructor");
			 contactMan = new ContactMethodManager();	
		}
		return this.contactMan.getContactMethods();
	}
	
	public void setContactMethods(Map<String, ContactMethod> contactMethods) {
		this.contactMan.setContactMethods(contactMethods);
	}

	public ContactMethod getContactMethod(String kind)  {
		return this.contactMan.getContactMethod(kind);
	}

	public void setContactMethod(String kind, ContactMethod phone)  {
		this.contactMan.setContactMethod(kind, phone);
	}


	/**	   
	 * Postal mailing addresses keyed by a string code representing a
	 * user-defined type of ContactMethod kind, such as PHYSICAL_ADDRESS,
	 * CHECK-IN_ADDRESS, MAILING_ADDRTestCaseESS, BILLING_ADDRESS, etc...
	 */
	public Map getPostalAddresses() {
		return this.contactMan.getPostalAddresses();
	}
	
	public PostalAddress getPostalAddress(String kind)  {
		return this.contactMan.getPostalAddress(kind);
	}


	/**	   
	 * Telephone numbers keyed by a string code representing a user-defined type of
	 * Phone Number, such as PHYSICAL_PHONE, BILLING_PHONE, etc...  
	 */
	public Map getPhoneNumbers() {
		return this.contactMan.getPhoneNumbers();
	}
	
	public PhoneNumber getPhoneNumber(String kind)  {
		return this.contactMan.getPhoneNumber(kind);
	}


	/**	   
	 * Email addresses keyed by a string code representing a user-defined kind of
	 * Email, such as EMAIL1, INFO_EMAIL etc...
	 */
	public Map getEmailAddresses() {
		return this.contactMan.getEmailAddresses();
	}
	
	public Uri getEmailAddress(String kind)  {
		return this.contactMan.getEmailAddress(kind);
	}


	/**
	 * Uniform Resource Locators (eg web page or ftp addresses) keyed by a string
	 * code representing a user-defined type of URL such as WEB_SITE, INTRANET, etc...
	 */
	public Map getUrls() {
		return this.contactMan.getUrls();
	}
	
	public Url getUrl(String kind)  {
		return this.contactMan.getUrl(kind);
	}

/*
	public Map<String, Object> getProperties() {
		if (this.properties == null)
			this.properties = new HashMap<String, Object>();

		return this.properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public Object getProperty(String key) {
		return this.getProperties().get(key);
	}

	public Object get(String key) {
		return this.getProperty(key);
	}

	public void setProperty(String key, Object o) {
		this.getProperties().put(key, o);
	}

	public void set(String key, Object o) {
		this.setProperty(key, o);
	}
	*/
	

	/**
	 * Form of Payment
	 */
	public List<FormOfPayment> getFormsOfPayment() 
	{
		if (this.fopList == null)
		{
		    this.fopList = new ArrayList<FormOfPayment>();		
		}
		
		return this.fopList;
	}
	
	
	/**
	 * @param aFopList The fopList to set.
	 */
	public void setFormsOfPayment(List<FormOfPayment> aFopList)
	{
		this.fopList = aFopList;
	}
	
	/**
	 * returns the matching type of form of payment
	 */
	public FormOfPayment getFormOfPayment(final Class aFormOfPaymentClass) 
	{
		final int iNum = getNumFormsOfPayment(aFormOfPaymentClass);
		if (iNum > 1)
		{
			throw new IllegalStateException("Unable to return a single instance of class " + aFormOfPaymentClass.getName() + " There is more than one");
		}
		
		
		// loop through all the forms of payment and find the matching instance
		final Iterator<FormOfPayment> it = getFormsOfPayment().iterator();
		while (it.hasNext())
		{
			final FormOfPayment fop = it.next();
			if (aFormOfPaymentClass.isInstance(fop))
			{
				return (fop);
			}
		}
		
		
		// no matching instance found
		return (null);
	}
	
	public List<FormOfPayment> getFormsOfPayment(final Class aFormOfPaymentClass)
	{
		final  List<FormOfPayment> list = new ArrayList<FormOfPayment>();

		// loop through all the forms of payment and find the matching instance
		final Iterator<FormOfPayment> it = getFormsOfPayment().iterator();
		while (it.hasNext())
		{
			final FormOfPayment fop = it.next();
			if (aFormOfPaymentClass.isInstance(fop))
			{
				list.add(fop);
			}
		}
		return list;
	}
	
	
	/**
	 * returns the number of types of forms of payment
	 */
	private int getNumFormsOfPayment(final Class aFormOfPaymentClass) 
	{
		int iNum = 0;
		
		final Iterator<FormOfPayment> it = getFormsOfPayment().iterator();
		while (it.hasNext())
		{
			final FormOfPayment fop = it.next();
			if (aFormOfPaymentClass.isInstance(fop))
			{
				iNum++;
			}
		}
		
		return (iNum);
	}
	
	

	/*
	public CompositeProperty getPropertyMap()
	{
		return properties;
	}
	*/
	
	public Object clone() 
	{
	    try 
	    {
	        PartyAbstract result = (PartyAbstract) super.clone();
	    
	        result.setId(-1);
	        
	        if (this.contactMan instanceof ContactMethodManager)
	        {
	            result.contactMan = (ContactMethodManager )this.contactMan.clone();
	        }
	        
					/*
	        if (this.properties instanceof CompositeProperty)
	        {
	            result.properties = (CompositeProperty )this.properties.clone();
	        }
					*/
	        
	        // do a deep copy of forms of payment
	        if (this.fopList instanceof List)
	        {
		        result.fopList = new ArrayList<FormOfPayment>();
		        for (FormOfPayment fop : this.fopList)
		        {
		        	final FormOfPayment cloneFop  = (FormOfPayment )fop.clone();
		        	cloneFop.setParty(result);
		        	result.fopList.add(cloneFop);
		        }
	        }
	        
	        return result;
	    } 
	    catch (CloneNotSupportedException e) 
	    {
	        return null;
	    }
	}
	
} // end class PartyAbstract
