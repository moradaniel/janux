package biz.janux.payment;

import java.math.BigDecimal;
import java.util.Date;

/**
 * This settlement item is specific for {@link MerchantAccount} of type {@link IndustryType#HOTEL}
 * @author albertobuffagni@gmail.com
 *
 */
public interface SettlementItemHotel extends SettlementItem {
	
	/**
	 * This 12-character numeric field contains the daily room rate associated with the settlement 
	 * transaction. The field entry must be right-justified and zero-filled. This field must contain at 
	 * least one non-zero value.
	 */
	public BigDecimal getAverageRate();
	public void setAverageRate(BigDecimal averageRate);
	
	/**
	 * For Hotel category merchants, this field must contain the Checkout Date.
	 * 
	 */
	public Date getCheckOutDate();
	public void setCheckOutDate(Date checkOutDate);
	
}
