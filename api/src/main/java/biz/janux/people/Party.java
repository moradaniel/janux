package biz.janux.people;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.janux.bus.persistence.Persistent;
// import org.janux.adapt.Extensible;

import biz.janux.commerce.FormOfPayment;


/**
 *************************************************************************************************
 * Organization and people have common characteristics that describe them, such as addresses, phone
 * numbers and email addresses; they can also play similar roles as parties to contracts, such as
 * buyers/sellers, client/provider, etc; this interface is the super type for the Person and
 * Organization interfaces and provides a mean to refer to their implementing classes as a whole
 *************************************************************************************************
 */
public interface Party extends Persistent, Contacteable, Serializable, Cloneable
{
	/** optional string identifier for this Party */
	String getCode();
	void setCode(String code);

	/** 
	 * The name(s) by which this Party is known; sub-classes may implement this in different ways; for
	 * Persons, for example, it may the concatenation of the first and last name, while for an
	 * Organization it may be a short name or legal name
	 */
	PartyName getPartyName();
	
	
	/**
	 * returns the matching type of form of payment
	 */
	FormOfPayment getFormOfPayment(final Class aFormOfPaymentClass);
	
	
	/**
	 * Forms of payment associated with this party
	 * @return a list containing all the forms of payment associated with the party
	 */
	List<FormOfPayment> getFormsOfPayment(); 
	
	/**
	 * @param fopList The fopList to set.
	 */
	void setFormsOfPayment(List<FormOfPayment> fopList);
	
	
	Map<String, ContactMethod> getContactMethods();

	
	void setContactMethods(Map<String, ContactMethod> aContactMethods); 
	
	Object clone();
}
