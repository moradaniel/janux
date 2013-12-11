package biz.janux.payment;

import java.io.Serializable;
import java.util.TimeZone;

import biz.janux.geography.PostalAddress;

/**
 ***************************************************************************************************
 * Represents a Merchant Bank Account in which a Party can receive payment for Credit Card transactions
 *
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.4
 ***************************************************************************************************
 */
public interface MerchantAccount extends Serializable
{
	/**
	 * A random alphanumeric string that uniquely identifies this MerchantAccount in the Janux Payment
	 * Service. This is the code that external clients must use to reference a MerchantAccount in the
	 * Janux Payment Service.
	 */
	public String getUuid();

	/**
	 * This field contains a 12-character numeric number assigned by the merchant's bank or processor.
	 * This field contains a unique non-zero number used to identify the merchant within the member
	 * systems. This field should be configurable as a parameter.
	 */
	public String getNumber();
	public void setNumber(String number);

	/**
	 * The title of the account (such as the name of the Party who owns the account), or any other
	 * descriptive string that would help cross-reference the account; we sugget that this field be
	 * used as a convenience while troubleshoot the data in the database, but that the party/account
	 * relationship be stored by referencing the identifying code of this account in the external
	 * calling system
	 */
	public String getName();
	public void setName(String name);

	/**
	 * This field contains a 6-character numeric Bank Identification Number (BIN) issued by the
	 * merchant's member bank or processor. The Acquirer BIN identifies the member bank or processor
	 * that signed the merchant using the POS application.
	 */
	public String getAcquiringBankBin();
	public void setAcquiringBankBin(String acquiringBankBin);

	/**
	 * This field contains a 6-character numeric number assigned by the merchant's bank or processor.
	 * This field is issued by the merchant's member bank or processor for purposes of identifying a
	 * specific agent entity of the member bank or processor. This number must be right-justified and
	 * zero-filled.
	 */
	public String getAgentBankNum();
	public void setAgentBankNum(String agentBankNum);

	/**
	 * This field contains a 6-character numeric number assigned by the merchant's bank or processor.
	 * This field is issued by the merchant's member bank or processor for purposes of identifying a
	 * specific chain of an agent organization. This number must be right-justified and zero-filled.
	 */
	public String getAgentChainNum();
	public void setAgentChainNum(String agentChainNum);

	/**
	 * This 4-character numeric field contains a number assigned by the merchant's bank or processor.
	 * This field is used to identify a specific merchant store location within the member systems.
	 * This number must be right-justified and zero-filled. This field should be configurable as a
	 * parameter.
	 */
	public String getStoreNum();
	public void setStoreNum(String storeNum);

	/**
	 * This 8-character numeric field contains the number to accommodate a POS device tracking number.
	 * This field is also known as the "V" number.  NOTE The "V" alpha character must be changed to
	 * numeric 7 when sending this field.
	 */
	public String getTerminalId();
	public void setTerminalId(String terminalId);

	/**
	 * This 4-character numeric field contains a number assigned by the merchant's bank or processor.
	 * This field contains a number used to identify a specific store terminal within the member
	 * systems. This number must be right-justified and zero-filled. This field must be configurable
	 * as a parameter.
	 */
	public String getTerminalNum();
	public void setTerminalNum(String terminalNum);

	/** The currency code representing the default currency for this account */
	public String getCurrencyCode();
	public void setCurrencyCode(String currencyCode);

	/** 
	 * The Postal Address of the Party owning this account; this field is required during the general
	 * course of business because the Merchant must provide a Business Address.
	 */
	public PostalAddress getMerchantAddress();
	public void setMerchantAddress(PostalAddress merchantAddress);

	/** 
	 * The Phone Number of the Party owning this account; 
	 */
	public String getMerchantPhone();
	public void setMerchantPhone(String phone);

	/** 
	 * The Merchant must provide a telephone number to which the Card Holder can call for service.
	 */
	public String getCardholderServicePhone();
	public void setCardholderServicePhone(String phone);

	/** The Time Zone in which the Merchant operates */
	public TimeZone getMerchantTimeZone();
	public void setMerchantTimeZone(TimeZone timeZone);

	/** 
	 * A String representation of the TimeZone that can be used to capture or
	 * store the timezone as a String;
	 * java.util.TimeZone.getTimeZone(this.getMerchantTimeZoneAsString()) 
	 * must return the same TimeZone as this.getMerchantTimeZone();
	 */
	public String getMerchantTimeZoneAsString();
	public void setMerchantTimeZoneAsString(String s);

	/**       
	 * The Party who owns this account.  This field is required during the general course of business,
	 * because the Merchant must provide a telephone number that the Card Holder can call for service,
	 * as well as a Business Address.  
	 *
	 * TODO: The following formatting requirement is in the Vital docs, and may be specific to Vital:
	 * The telephone number must appear in the format "NNN-nnnnnnn". A
	 * hyphen is required in the fourth character position. This field entry must be left-justified
	 * and space-filled.  Depending upon its location, this field size may be either 11 or 13
	 * characters. This field can be used for e-mail or URL.
	 */
	/*
	public Party getAccountHolder();
	public void setAccountHolder(Party accountHolder);
	*/
	
	/** Provides a way to disable a merchant account without deleting it from the system */
	public boolean isEnabled();
	public void setEnabled(boolean b);


} // end interface MerchantAccount
