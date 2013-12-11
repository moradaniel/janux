package biz.janux.payment;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.janux.bus.persistence.Persistent;
import org.janux.bus.persistence.PersistentVersionableIdentifiableAbstract;
import org.janux.util.JanuxToStringStyle;

import biz.janux.geography.PostalAddress;

/**
 ***************************************************************************************************
 * Represents a Credit Card Account
 *
 * @author <a href="mailto:philippe.paravicini@janux.org">philippe.paravicini@janux.org</a>
 * @since 0.4
 ***************************************************************************************************
 */
public class CreditCardImpl extends PersistentVersionableIdentifiableAbstract implements CreditCard
 {
	/* instantiates default formatter with pattern: "MM/yyyy" */
	public static SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("MM/yyyy");

	private CreditCardType type;
	private String number;
	private String numberMasked;
	private Date expirationDate;
	private String securityCode;
	private String cardholderName;
	private PostalAddress billingAddress;
	private boolean enabled = true;
	private String token;
	private BusinessUnit businessUnit;
	private String businessUnitCode;
	private String cardNumberHash;
	private boolean swiped = false;

	public CreditCardImpl() { }
	
	/*
	public CreditCardImpl(String uuid, String number, Date expirationDate, CreditCardTypeImpl type) 
	{
		this.uuid = uuid;
		this.number = number;
		this.expirationDate = expirationDate;
		this.type = type;
	}

	public CreditCardImpl(String uuid, String number, Date expirationDate, String securityCode, Date dateCreated, PersonName cardHolder, PostalAddress billingAddress, CreditCardTypeImpl type) 
	{
		this.uuid = uuid;
		this.number = number;
		this.expirationDate = expirationDate;
		this.securityCode = securityCode;
		this.dateCreated = dateCreated;
		this.cardHolderName = cardHolderName;
		this.billingAddress = billingAddress;
		this.type = type;
	}
	*/

	public CreditCardType getType() {
		return this.type;
	}
	
	public void setType(CreditCardType type) {
		this.type = type;
	}

	public String getTypeCode() {
		if (type!=null)
			return type.getCode();
		
		return null;
	}

	public void setTypeCode(String code) {
		if (type==null)
		{
			type = new CreditCardTypeImpl();
		}
		else if (type instanceof Persistent && ((Persistent)type).getId()!=Persistent.UNSAVED_ID){
			throw new UnsupportedOperationException("The type is persistent.Use the setType method to update it.");
		}
		
		type.setCode(code);	
	}   
	
	public String getNumber() {
		return this.number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}


	public String getNumberMasked() {
		return this.numberMasked;
	}
	
	public void setNumberMasked(String numberMasked) {
		this.numberMasked = numberMasked;
	}


	public Date getExpirationDate() {
		return this.expirationDate;
	}
	
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}


	public String getSecurityCode() {
		return this.securityCode;
	}
	
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}


	public String getCardholderName() {
		return this.cardholderName;
	}
	
	public void setCardholderName(String cardholderName) {
		this.cardholderName = cardholderName;
	}


	public PostalAddress getBillingAddress() {
		return this.billingAddress;
	}
	
	public void setBillingAddress(PostalAddress billingAddress) {
		this.billingAddress = billingAddress;
	}


	public boolean isEnabled() { return enabled;}
	public void setEnabled(boolean b) { enabled = b; }
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setBusinessUnit(BusinessUnit businessUnit) {
		this.businessUnit = businessUnit;
	}

	/**
	 * Indicate what {@link BusinessUnit} saved the credit card
	 */
	public BusinessUnit getBusinessUnit() {
		return businessUnit;
	}

	public void setCardNumberHash(String cardNumberHash) {
		this.cardNumberHash = cardNumberHash;
	}

	/**
	 * Used to determine whether two creditCard numbers match, without having to decrypt them.
	 * The Hash number is created using a one-way hash utility that ensures that for a
	 * given String, the same hash code will always be generated. 
	 */
	public String getCardNumberHash() {
		return cardNumberHash;
	}

	public void setBusinessUnitCode(String businessUnitCode) {
		
		if (businessUnit==null)
		{
			businessUnit = new BusinessUnitImpl();
		}
		else if (businessUnit instanceof Persistent && ((Persistent)businessUnit).getId()!=Persistent.UNSAVED_ID){
			throw new UnsupportedOperationException("The businessUnit is persistent.Cannot be to update it.");
		}
		
		businessUnit.setCode(businessUnitCode);	
	}

	public String getBusinessUnitCode() {
		if (businessUnit!=null)
			return businessUnit.getCode();
		
		return null;
	}
	

	public boolean isSwiped() {
		return swiped;
	}

	public void setSwiped(boolean swiped) {
		this.swiped = swiped;
	}

	public String toString() 
	{
		ToStringBuilder sb = new ToStringBuilder(this, JanuxToStringStyle.COMPACT);

		if (getUuid() != null)           sb.append("uuid",  getUuid());
		if (getToken() != null)          sb.append("token", getToken());
		if (getTypeCode() != null)       sb.append("type",  getTypeCode());
		if (getNumberMasked() != null)   sb.append("number",  getNumberMasked());
		if (getExpirationDate() != null) sb.append("expDate", DEFAULT_DATE_FORMAT.format(getExpirationDate()));
		if (getCardholderName() != null) sb.append("holder",  getCardholderName());
		if (getBusinessUnit() != null)   sb.append("bizUnit", getBusinessUnit().getCode());
		if (getCardNumberHash() != null) sb.append("numberHash", getCardNumberHash());
		if (getBillingAddress() != null) sb.append("billingAddress", getBillingAddress());
		sb.append("enabled",isEnabled());
		sb.append("swiped",isSwiped());

		return sb.toString();
	}


	public boolean equals(Object other)
	{
		if ( (this == other) ) return true;
		if ( !(other instanceof CreditCard) ) return false;

		CreditCard castOther = (CreditCard)other; 

		return new org.apache.commons.lang.builder.EqualsBuilder()
			.append(this.getUuid(), castOther.getUuid())
			.isEquals();
	}

	public int hashCode() 
	{
		return new HashCodeBuilder().append(this.getUuid()).toHashCode();
	}


} // end class CreditCardImpl
