/*
 * Created on Mar 23, 2006
 */
package biz.janux.commerce;

import org.janux.bus.persistence.PersistentAbstract;

import biz.janux.people.Party;

/**
 * 
 *
 * @author David Fairchild
 *
 */
public class AbstractFormOfPayment extends PersistentAbstract implements FormOfPayment
{
	private static final long serialVersionUID = 634079964174836924L;
	private Party party;
	private int position;

	/**
	 * @return Returns the position.
	 */
	public int getPosition()
	{
		return position;
	}
	/**
	 * @param position The position to set.
	 */
	public void setPosition(int position)
	{
		this.position = position;
	}
	
	/**
	 * @return Returns the party.
	 */
	public Party getParty()
	{
		return party;
	}
	/**
	 * @param party The party to set.
	 */
	public void setParty(Party party)
	{
		this.party = party;
	}
	
	public Object clone() 
	{
	    try 
	    {
	        FormOfPayment result = (FormOfPayment )super.clone();
	        
	        result.setId(-1);
	        result.setParty(this.getParty());
	        
	        return result;
	    } 
	    catch (CloneNotSupportedException e) 
	    {
	        return null;
	    }
	}
 
}
