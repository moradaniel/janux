package biz.janux.commerce.payment.model;

import biz.janux.commerce.payment.util.CreditCardUtil;

public class MyhmsCreditcard {
	
	private long id;
	private long ccid;
	private long guestid;
	private String creditcardnumber;
	
	CreditCardUtil ccu =new CreditCardUtil();
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCcid() {
		return ccid;
	}
	public void setCcid(long ccid) {
		this.ccid = ccid;
	}
	public long getGuestid() {
		return guestid;
	}
	public void setGuestid(long guestid) {
		this.guestid = guestid;
	}
	public String getCreditcardnumber() {
		
		return creditcardnumber;
	}
	public void setCreditcardnumber(String creditcardnumber) {
		String	ccnum= ccu.getMaskedNumber(creditcardnumber);
		
		this.creditcardnumber =creditcardnumber;
	}
}
