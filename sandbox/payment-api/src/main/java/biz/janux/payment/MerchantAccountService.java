package biz.janux.payment;

import biz.janux.payment.MerchantAccount;
import org.janux.bus.search.SearchCriteria;

public interface MerchantAccountService<S extends SearchCriteria>  
{
	
	/**
	 * If the merchant account has transactions associated , it is disabled. 
	 * Otherwise the merchant account is removed. 
	 *
	 * @param uuid
	 */
	public void delete(String uuid);

	/**
	 * If the merchant account has transactions associated , it is disabled. 
	 * Otherwise the merchant account is removed. 
	 */
	public void delete(MerchantAccount merchantAccount);

	public MerchantAccount find(S searchCriteria);

	public MerchantAccount save(MerchantAccount merchantAccount);
	
}
