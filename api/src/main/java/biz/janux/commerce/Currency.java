package biz.janux.commerce;

public interface Currency
{
	String getCode();

	void setCode(String code);

	String getDescription();

	void setDescription(String description);

	int getDefaultFractionDigits();

	void setId(final Integer aIdNumber);
	 
	Integer getId();
}
