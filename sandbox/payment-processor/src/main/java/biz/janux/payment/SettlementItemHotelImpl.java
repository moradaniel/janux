package biz.janux.payment;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.janux.util.JanuxToStringStyle;

public class SettlementItemHotelImpl  extends SettlementItemImpl implements SettlementItemHotel {
	
	BigDecimal averageRate;
	Date checkOutDate;
	
	/**
	 * used by hibernate 
	 * @deprecated
	 */
	public SettlementItemHotelImpl() {
		super();
	}
	
	public SettlementItemHotelImpl(BigDecimal amount, SettlementItemType type, Date date, CreditCard creditCard, BusinessUnit businessUnit, String purchaseIdentifier,BigDecimal averageRate, Date checkOutDate, String externalSourceId) {
		super(amount, type, date, creditCard, businessUnit, purchaseIdentifier, externalSourceId);
		this.averageRate = averageRate;
		this.checkOutDate = checkOutDate;
	}

	public BigDecimal getAverageRate() {
		return averageRate;
	}
	public void setAverageRate(BigDecimal averageRate) {
		this.averageRate = averageRate;
	}
	public Date getCheckOutDate() {
		return checkOutDate;
	}
	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	
	
	public String toString() 
	{
		ToStringBuilder sb = new ToStringBuilder(this, JanuxToStringStyle.COMPACT);
		
		sb.append(super.toString());
		if (getAverageRate() != null)    sb.append("averageRate", getAverageRate());
		if (getCheckOutDate() != null)    sb.append("checkOutDate", getCheckOutDate());
		
		return sb.toString();
	}


	public boolean equals(Object other)
	{
		if ( (this == other) ) return true;
		if ( !(other instanceof SettlementItemHotelImpl) ) return false;

		SettlementItemHotelImpl castOther = (SettlementItemHotelImpl)other; 

		return new org.apache.commons.lang.builder.EqualsBuilder()
			.append(this.getUuid(), castOther.getUuid())
			.isEquals();
	}

	public int hashCode() 
	{
		return new org.apache.commons.lang.builder.HashCodeBuilder()
			.append(this.getUuid())
			.toHashCode();
	}
	
}
