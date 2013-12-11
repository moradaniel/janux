package biz.janux.commerce;


public interface PaymentMethod {
	
	public String getCode();

	public void setCode(String code);

	public String getDescription();

	public void setDescription(String description);

	public String getExtraInfo();

	public void setExtraInfo(String extraInfo);
	
}
