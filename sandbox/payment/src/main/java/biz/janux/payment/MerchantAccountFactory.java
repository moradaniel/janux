package biz.janux.payment;

import biz.janux.payment.MerchantAccount;

public interface MerchantAccountFactory {

	public MerchantAccount getMerchantAccount();
	public MerchantAccount getVitalMerchantAccount();
	
}
