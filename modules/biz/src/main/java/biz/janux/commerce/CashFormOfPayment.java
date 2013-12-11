/*
 * Created on Mar 17, 2006
 */
package biz.janux.commerce;

/**
  * This class identifies a cash form of payment
 * @author David Fairchild
 *
 */
public class CashFormOfPayment extends AbstractFormOfPayment
{
	private static final long serialVersionUID = -5575498897810243136L;
	/** the cash currency */
    private String currencyCode;
    
	/**
	 * Constructor
	 */
	public CashFormOfPayment()
	{
	}

	/**
	 * @return Returns the currencyCode.
	 */
	public String getCurrencyCode()
	{
		return currencyCode;
	}
	/**
	 * @param currencyCode The currencyCode to set.
	 */
	public void setCurrencyCode(String currencyCode)
	{
		this.currencyCode = currencyCode;
	}
	
	public Object clone() 
	{
        Object result = super.clone();
        return result;
	}
	
}
