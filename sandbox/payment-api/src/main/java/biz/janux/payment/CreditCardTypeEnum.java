package biz.janux.payment;

public enum CreditCardTypeEnum {
	
	VISA ("VI"),
	MASTERCARD ("MC"),
	AMERICAN_EXPRESS ("AX"),
	DISCOVER ("DS"),
	UNKNOWN ("XX");
	
	String code;

	CreditCardTypeEnum(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
}
