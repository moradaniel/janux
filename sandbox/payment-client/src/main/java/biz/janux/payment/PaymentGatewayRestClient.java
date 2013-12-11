package biz.janux.payment;

import org.json.JSONObject;

public interface PaymentGatewayRestClient {

	public CreditCard saveCreditCard(CreditCard creditCard)  throws Exception;
	
	public CreditCard updateCreditCard(String uuid,CreditCard creditCard)  throws Exception;

	public JSONObject deleteCreditCard(String uuid) throws Exception; 

	public CreditCard loadCreditCard(String uuid) throws Exception;

}
