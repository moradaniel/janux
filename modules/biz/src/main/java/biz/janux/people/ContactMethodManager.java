package biz.janux.people;

import java.util.Map;
import java.util.HashMap;
import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import biz.janux.people.net.Url;
import biz.janux.people.net.Uri;
import biz.janux.people.net.EmailAddressImpl;

import biz.janux.geography.PostalAddress;


/**
 ***************************************************************************************************
 * This is a utility class that can be used as a private field to manage ContactMethods inside a
 * {@link Contacteable} entity
 *
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version $Revision: 1.9 $ - $Date: 2006-11-14 01:26:27 $
 ***************************************************************************************************
 */
public class ContactMethodManager implements Contacteable, Serializable, Cloneable
{
    private static final long serialVersionUID = -7277872471797101296L;
	private static Log log = LogFactory.getLog(ContactMethodManager.class);
	private Map<String, ContactMethod> contactMethods;

	/** plain vanilla empty constructor */
	public ContactMethodManager() 
	{ 
		contactMethods = new HashMap<String, ContactMethod>();
	}


	
	public ContactMethod getContactMethod(String kind)  
	{
		if (contactMethods.containsKey(kind))
		{
		    return ((ContactMethod) contactMethods.get(kind));
		}
		else
		{
			return (null);
		}
	}

	
	public void setContactMethod(String kind, ContactMethod contactMethod)  
	{
		contactMethods.put(kind, contactMethod);
		if (log.isInfoEnabled()) 
		{
			log.info("added contact method of kind '" + kind + "'" + contactMethod.toString());
		}
	}


	/*
	 * Postal mailing addresses keyed by a string code representing a
	 * user-defined type of ContactMethod kind, such as PHYSICAL_ADDRESS,
	 * CHECK-IN_ADDRESS, MAILING_ADDRESS, BILLING_ADDRESS, etc...
	 */
	public Map<String, ContactMethod> getPostalAddresses()
	{
		return createContactMethodMap(PostalAddress.class);
	}

	public PostalAddress getPostalAddress(String kind)  
	{
		return (PostalAddress)this.getContactMethod(kind);
	}


	/*
	 * Telephone numbers keyed by a string code representing a user-defined type of
	 * Phone Number, such as PHYSICAL_PHONE, BILLING_PHONE, etc...
	 */
	public Map getPhoneNumbers()
	{
		return ( createContactMethodMap(PhoneNumber.class) );
		
	}

	public PhoneNumber getPhoneNumber(String kind)  
	{
		return (PhoneNumber)this.getContactMethod(kind);
	}


	/*
	 * Email addresses keyed by a string code representing a user-defined kind of
	 * Email, such as EMAIL1, INFO_EMAIL etc...
	 */
	public Map getEmailAddresses()
	{
		return ( createContactMethodMap(EmailAddressImpl.class) );
	}

	
	public Uri getEmailAddress(String kind)  
	{
		return (Uri)this.getContactMethod(kind);
	}


	/*
	 * Uniform Resource Locators (eg web page or ftp addresses) keyed by a string
	 * code representing a user-defined type of URL such as WEB_SITE, INTRANET, etc...
	 */
	public Map getUrls()
	{
		return ( createContactMethodMap(Url.class) );
	}

	
	public Url getUrl(String kind)  
	{
		return (Url)this.getContactMethod(kind);
	}

	
	/** creates a map for each subclass of ContactMethod found in the main ContactMethod map */
	@SuppressWarnings("unchecked")
	private Map<String, ContactMethod> createContactMethodMap(final Class aClass)
	{
		final Map<String, ContactMethod> map = new HashMap<String, ContactMethod>();

		for (String key : this.contactMethods.keySet())
		{
			final ContactMethod contactMethod = this.contactMethods.get(key);
            
            if (aClass.isAssignableFrom(contactMethod.getClass()))
			{
				map.put(key, contactMethod);
			}
		}
		
		
		if (log.isDebugEnabled()) 
		{
			log.debug("recreated contact method maps");
		}
		
		return (map);
	}


	public Map<String, ContactMethod> getContactMethods()
	{
		if (!(contactMethods instanceof Map))
		{
			 log.warn("getContactMethods method is having to create another instance of contactMethods map - somehow it wasn't created with constructor");
		    contactMethods = new HashMap<String, ContactMethod>();	
		}
		
		return (contactMethods);
	}


	/**
	 * @param aContactMethods
	 */
	public void setContactMethods(Map<String, ContactMethod> aContactMethods)
	{
		if (aContactMethods == null)
		{
			throw new IllegalArgumentException("setContactMethods was pass a null instance");
		}
		
		if (!(aContactMethods instanceof Map))
		{
			throw new IllegalArgumentException("setContactMethods expects an instance of Map.  Was actually passed " + aContactMethods.getClass().getName());
		}
		
		contactMethods = aContactMethods;
	}
	
	@SuppressWarnings("unchecked")
	public Object clone() 
	{
	    try 
	    {
	        ContactMethodManager result = (ContactMethodManager) super.clone();
	    
	        // deep copy of the contact method map
	        result.contactMethods = new HashMap<String, ContactMethod>();
	        for (String key : this.contactMethods.keySet())
	        {
	        	final ContactMethod value  = (ContactMethod )this.contactMethods.get(key).clone();
	        	result.contactMethods.put(key, value);
	        }
	        
	        return result;
	    } 
	    catch (CloneNotSupportedException e) 
	    {
	        return null;
	    }
	}
	

} // end class

