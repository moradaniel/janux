package biz.janux.people;

import java.util.Map;

import biz.janux.geography.PostalAddress;

import biz.janux.people.net.Uri;
import biz.janux.people.net.Url;


/**
 *************************************************************************************************
 * An interface for an entity which may have Phone Numbers, Postal Addresses, Emails, Urls, or other
 * contact methods; for example, Person, Organizations, Orders, etc...
 *************************************************************************************************
 */
public interface Contacteable
{
	/**	   
	 * map of ContactMethods keyed by a string code representing a user-defined kind of
	 * ContactMethod, such as PHYSICAL_ADDRESS, CUST_SERVICE_PHONE, EMAIL_INFO, etc...  
	 */
	Map<String, ContactMethod> getContactMethods();
	void setContactMethods(Map<String, ContactMethod> contactMethods);

	/** return the contact method of the kind indicated */
	ContactMethod getContactMethod(String kind);

	/** set the contact method of the kind indicated */
	void setContactMethod(String kind, ContactMethod contactMethod);


	/**	   
	 * Postal mailing addresses keyed by a string code representing a user-defined
	 * kind of ContactMethod, such as PHYSICAL_ADDRESS, MAILING_ADDRESS,
	 * BILLING_ADDRESS, etc...
	 */
	Map getPostalAddresses();

	/** return the postal address of the kind indicated */
	PostalAddress getPostalAddress(String kind);


	/**	   
	 * map of Telephone numbers keyed by a string code representing a user-defined kind
	 * of ContactMethod, such as CUST_SERVICE_PHONE, BILLING_PHONE, etc...  
	 */
	Map getPhoneNumbers();

	/** return the phone number of the kind indicated */
	PhoneNumber getPhoneNumber(String kind);


	/**	   
	 * map of Email addresses keyed by a string code representing a user-defined kind of
	 * ContactMethod, such as EMAIL1, INFO_EMAIL etc...
	 */
	Map getEmailAddresses();

	/** return the email address of the kind indicated */
	Uri getEmailAddress(String kind);


	/**
	 * map of Uniform Resource Locators (eg web page or ftp addresses) keyed by a string
	 * code representing a user-defined kind of ContactMethod such as WEB_SITE, INTRANET, etc...
	 */
	Map getUrls();

	/** return the url of the kind indicated */
	Url getUrl(String kind);
}
