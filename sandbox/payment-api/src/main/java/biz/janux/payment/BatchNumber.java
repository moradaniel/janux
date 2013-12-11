package biz.janux.payment;

import java.io.Serializable;

/**
 ***************************************************************************************************
 * Indicate the number of the batch used in the settlement process. 
 * This number will be incremented for each batch sent.
 * 
 * @author  <a href="mailto:alberto.buffagni@janux.org">Alberto Buffagni</a>
 *
 * @since 0.4.0 - 2011-11-15
 ***************************************************************************************************
 */
public interface BatchNumber extends Serializable {
	public int getNumber();
	public void setNumber(int number);

	public MerchantAccount getMerchantAccount();
	public void setMerchantAccount(MerchantAccount merchantAccount);
}
