package biz.janux.payment;

import java.util.TimeZone;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.janux.bus.persistence.PersistentVersionableIdentifiableAbstract;
import org.janux.util.JanuxToStringStyle;

import biz.janux.geography.PostalAddress;
import biz.janux.geography.PostalAddressImpl;

/**
 ***************************************************************************************************
 * Represents a Merchant Bank Account in which a Party can receive payment for Credit Card transactions
 *
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.4
 ***************************************************************************************************
 */
public class MerchantAccountImpl extends PersistentVersionableIdentifiableAbstract implements MerchantAccount
{
	private String number;
	private String name;
	// private Party  accountHolder;
	private String acquiringBankBin;
	private String agentBankNum;
	private String agentChainNum;
	private String storeNum;
	private String terminalId;
	private String terminalNum;
	private String currencyCode;
	private PostalAddress merchantAddress;
	private String   merchantPhone;
	private String   servicePhone;
	private TimeZone merchantTimeZone;
	private String timeZoneAsString;
	private boolean enabled;
	private IndustryType industryType;
	
	/** plain vanilla constructor */
	public MerchantAccountImpl() {}

	public String getNumber() {
		return this.number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}


	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}


	/*
	public Party getAccountHolder() {
		return this.accountHolder;
	}
	
	public void setAccountHolder(Party accountHolder) {
		this.accountHolder = accountHolder;
	}
	*/


	public String getAcquiringBankBin() {
		return this.acquiringBankBin;
	}
	
	public void setAcquiringBankBin(String acquiringBankBin) {
		this.acquiringBankBin = acquiringBankBin;
	}


	public String getAgentBankNum() {
		return this.agentBankNum;
	}
	
	public void setAgentBankNum(String agentBankNum) {
		this.agentBankNum = agentBankNum;
	}


	public String getAgentChainNum() {
		return this.agentChainNum;
	}
	
	public void setAgentChainNum(String agentChainNum) {
		this.agentChainNum = agentChainNum;
	}


	public String getStoreNum() {
		return this.storeNum;
	}
	
	public void setStoreNum(String storeNum) {
		this.storeNum = storeNum;
	}


	public String getTerminalId() {
		return this.terminalId;
	}
	
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}


	public String getTerminalNum() {
		return this.terminalNum;
	}
	
	public void setTerminalNum(String terminalNum) {
		this.terminalNum = terminalNum;
	}


  public String getCurrencyCode() { 
		return currencyCode;
	}

  public void setCurrencyCode(String o) { 
		currencyCode = o; 
	}


  public PostalAddress getMerchantAddress() { 
	  	if(this.merchantAddress == null)
			this.merchantAddress = new PostalAddressImpl();
		return this.merchantAddress;
	}

  public void setMerchantAddress(PostalAddress o) { 
		merchantAddress = o; 
	}


  public String getMerchantPhone() { 
		return merchantPhone;
	}

  public void setMerchantPhone(String o) { 
		merchantPhone = o; 
	}


  public String getCardholderServicePhone() { 
		return servicePhone;
	}

  public void setCardholderServicePhone(String o) { 
		servicePhone = o; 
	}
	


  public TimeZone getMerchantTimeZone() { 
		return merchantTimeZone;
	}

  public void setMerchantTimeZone(TimeZone tz) 
	{ 
		this.merchantTimeZone = tz; 

		// harmonize getMerchantTimeZone and getMerchantTimeZoneAsString
		if (tz == null && this.getMerchantTimeZoneAsString() != null) {
			this.setMerchantTimeZoneAsString(null);
		}
		else if (tz != null && !tz.getID().equals(this.getMerchantTimeZoneAsString())) {
			this.setMerchantTimeZoneAsString(tz.getID());
		}
		
	}


  public String getMerchantTimeZoneAsString() { 
		return timeZoneAsString;
	}

  public void setMerchantTimeZoneAsString(String s) 
	{ 
		this. timeZoneAsString = s; 

		// harmonize getMerchantTimeZone and getMerchantTimeZoneAsString
		if (s == null) {
			if (this.getMerchantTimeZone() != null) this.setMerchantTimeZone(null);
		} else {
			TimeZone tz = TimeZone.getTimeZone(s);
			if (!tz.equals(this.getMerchantTimeZone())) {
				this.setMerchantTimeZone(tz);
			}
		}
	}


	


	public String toString() 
	{
		ToStringBuilder sb = new ToStringBuilder(this, JanuxToStringStyle.COMPACT);

		if (getUuid()              != null) sb.append("uuid", getUuid());
		if (getNumber()            != null) sb.append("number", getNumber());
		if (getName()              != null) sb.append("name", getName());

		return sb.toString();
	}


	public boolean equals(Object other)
	{
		if ( (this == other ) ) return true;
		if ( !(other instanceof MerchantAccount) ) return false;
		MerchantAccount castOther = (MerchantAccount)other; 

		return new EqualsBuilder()
			.append(this.getAcquiringBankBin(), castOther.getAcquiringBankBin())
			.append(this.getNumber(), castOther.getNumber())
			.isEquals();
	}


	public int hashCode() 
	{
		return new HashCodeBuilder()
		.append(this.getAcquiringBankBin())
		.append(this.getNumber())
		.toHashCode();
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEnabled() {
		return enabled;
	}

} // end class MerchantAccountImpl
