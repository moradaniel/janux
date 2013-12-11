package biz.janux.people;

public interface PhoneNumber extends ContactMethod
{
	String getCountryCode();
	void setCountryCode(String i);

	String getAreaCode();
	void setAreaCode(String i);

	String getNumber();
	void setNumber(String s);

	String getExtension();
	void setExtension(String s);
}
