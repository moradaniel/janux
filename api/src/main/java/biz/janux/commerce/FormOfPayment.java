/*
 * Created on Mar 17, 2006
 */
package biz.janux.commerce;

import java.io.Serializable;

import org.janux.bus.persistence.Persistent;

import biz.janux.people.Party;

/**
 * This interface is used as a marker to identify a form of payment
 *
 * @author David Fairchild
 *
 */
public interface FormOfPayment extends Serializable, Persistent, Cloneable
{

	/**
	 * @return Returns the position.
	 */
	int getPosition();
	
	/**
	 * @param position The position to set.
	 */
	void setPosition(int position);
	
	/**
	 * @return Returns the party.
	 */
	Party getParty();
	/**
	 * @param party The party to set.
	 */
	void setParty(Party party);
	
	Object clone() throws CloneNotSupportedException;
}
