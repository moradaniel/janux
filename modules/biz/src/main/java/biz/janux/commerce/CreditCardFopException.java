package biz.janux.commerce;

import java.io.Serializable;

public class CreditCardFopException extends RuntimeException implements Serializable
{
	public CreditCardFopException(Exception e) {
		this.initCause(e);
	}
}
