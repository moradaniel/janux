package biz.janux.people;

import java.io.Serializable;
import java.util.Map;

import org.janux.bus.persistence.Persistent;

/** 
 * marker interface used to signify that a sub-interface or implementing class
 * represents a means of contacting a Party; examples of ContactMethod
 * sub-interfaces are PostalAddress, PhoneNumber, EmailAddress and URL
 */
public interface ContactMethod extends Persistent, Serializable, Cloneable 
{ 
	public Object clone();
}
