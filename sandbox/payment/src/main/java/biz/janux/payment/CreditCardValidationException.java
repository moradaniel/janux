package biz.janux.payment;

import java.util.HashMap;
import java.util.Map;

public class CreditCardValidationException extends RuntimeException
{
	private Map<String, String> errors = new HashMap<String, String>();

	
	public Map<String, String> getErrors()
	{
		return errors;
	}

	public void setErrors(Map<String, String> errors)
	{
		this.errors = errors;
	}

}
