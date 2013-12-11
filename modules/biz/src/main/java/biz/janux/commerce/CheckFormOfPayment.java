/*
 * Created on Mar 17, 2006
 */
package biz.janux.commerce;


/**
 * This class identifies a check form of payment
 *
 * @author David Fairchild
 *
 */
public class CheckFormOfPayment extends AbstractFormOfPayment
{
	private static final long serialVersionUID = -697802750762791618L;
	/** the name of the bank */
   private String bankName;
   /** bank routing number */
   private String routingNumber;
   /** account number for the check */
   private String accountNumber;
   /** name on the account */
   private String accountName;
   /** type of account (checking, savings, business etc.) */
   private String accountType;
   
	/**
	 * Constructor
	 */
	public CheckFormOfPayment()
	{
	}

	/**
	 * @return Returns the accountName.
	 */
	public String getAccountName()
	{
		return accountName;
	}
	/**
	 * @return Returns the accountNumber.
	 */
	public String getAccountNumber()
	{
		return accountNumber;
	}
	/**
	 * @return Returns the accountType.
	 */
	public String getAccountType()
	{
		return accountType;
	}
		/**
		 * @return Returns the bankName.
		 */
		public String getBankName()
		{
			return bankName;
		}
	/**
	 * @return Returns the routingNumber.
	 */
	public String getRoutingNumber()
	{
		return routingNumber;
	}
	/**
	 * @param accountName The accountName to set.
	 */
	public void setAccountName(String accountName)
	{
		this.accountName = accountName;
	}
	/**
	 * @param accountNumber The accountNumber to set.
	 */
	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
	}
	/**
	 * @param accountType The accountType to set.
	 */
	public void setAccountType(String accountType)
	{
		this.accountType = accountType;
	}
		/**
		 * @param bankName The bankName to set.
		 */
		public void setBankName(String bankName)
		{
			this.bankName = bankName;
		}
	/**
	 * @param routingNumber The routingNumber to set.
	 */
	public void setRoutingNumber(String routingNumber)
	{
		this.routingNumber = routingNumber;
	}
	
	public Object clone() 
	{
        Object result = super.clone();
        return result;
	}
	
}
