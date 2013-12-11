package biz.janux.commerce;


import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.janux.bus.persistence.PersistentAbstract;

public class CurrencyImpl extends PersistentAbstract implements Currency, Serializable
{
	private static final long serialVersionUID = 2232331951136652051L;
	private String code;
	private String description;
	 // the number of digits to the right of the decimal for this currency, -1 means unknown	
	private int defaultFractionDigits;  

	
	public CurrencyImpl()
	{
		super();
		this.defaultFractionDigits = -1;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
		setDefaultFractionDigits();
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public int getDefaultFractionDigits()
	{
		return defaultFractionDigits;
	}

	
	private void setDefaultFractionDigits()
	{
		this.defaultFractionDigits = -1;
		
		try
		{
			final java.util.Currency curr = java.util.Currency.getInstance(this.getCode());
			if (curr != null)
			{
				this.defaultFractionDigits = curr.getDefaultFractionDigits();
			}
		}
		catch (Exception e)
		{
			this.defaultFractionDigits = -1;
		}
		
	}
	
	
	/**  Two payment methods are equal if the codes are equal */
	public boolean equals(final Object aObject)
	{
		if (this == aObject) 
		{
			return true;
		}
		
		if (!(aObject instanceof Currency)) 
		{
			return false;
		}
		
		final Currency thatCurrency = (Currency)aObject; 

		return new EqualsBuilder()
			.append(this.getCode(), thatCurrency.getCode())
			.isEquals();
	}

	public int hashCode() 
	{
		return new HashCodeBuilder()
		.append(this.getCode())
		.toHashCode();
	}
	
}
