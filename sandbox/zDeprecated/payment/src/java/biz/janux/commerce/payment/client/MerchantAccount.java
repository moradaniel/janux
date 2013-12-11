package biz.janux.commerce.payment.client;


public abstract class MerchantAccount implements
		biz.janux.commerce.payment.interfaces.model.MerchantAccount {
	
	public MerchantAccount(){}
	VENDORTYPE vendorType;
  	
	/**
	 * @see 
	 * {@link biz.janux.commerce.payment.interfaces.model.MerchantAccount.getVendorType}
	 * 
	 * */	
	public VENDORTYPE getVendorType() {
		//getVendor which type of vendor you are using (vital or tsys)  
		return vendorType;
	}
 	/**
	 * @see 
	 * {@link biz.janux.commerce.payment.interfaces.model.MerchantAccount.setVendorType}
	 * 
	 * */	
 	public void setVendorType(VENDORTYPE vendorType) {
  		// set vendor type(vital or tsys)  
 		this.vendorType= vendorType;
	}

}
