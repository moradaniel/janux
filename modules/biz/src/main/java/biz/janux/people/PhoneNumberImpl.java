package biz.janux.people;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.janux.bus.persistence.PersistentAbstract;

/**
 ***************************************************************************************************
 * Simple bean representing a phone number
 * 	
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version $Revision: 1.10 $ - $Date: 2007-12-06 01:20:41 $
 ***************************************************************************************************
 */
public class PhoneNumberImpl extends PersistentAbstract implements PhoneNumber
{
	private static final long serialVersionUID = 20071204;
	private static Log log = LogFactory.getLog(PhoneNumberImpl.class);
	
	private int countryCode  = -1;
	private int areaCode     = -1;
	private String number;
	private String extension;


	/** plain vanilla constructor */
	public PhoneNumberImpl() {}


	public String getCountryCode() {
		return (this.countryCode == -1) ? "" : String.valueOf(this.countryCode);
	}
	
	public void setCountryCode(String countryCode) 
	{
		if (StringUtils.isEmpty(countryCode))
			this.countryCode = -1;
		else {
			try { 
				this.countryCode = Integer.parseInt(countryCode);
			}
			catch (Exception e)
			{
				String msg = "The Country Code of a phone number should be a numeric value";
				log.error(msg, e);
				throw new IllegalArgumentException(msg);
			}
		}
	}


	public String getAreaCode() {
		return (this.areaCode == -1) ? "" : String.valueOf(this.areaCode);
	}
	
	public void setAreaCode(String areaCode) 
	{
		if (StringUtils.isEmpty(areaCode))
			this.areaCode = -1;
		else {
			try { 
				this.areaCode = Integer.parseInt(areaCode);
			}
			catch (Exception e)
			{
				String msg = "The Area Code of a phone number should be a numeric value";
				log.error(msg, e);
				throw new IllegalArgumentException(msg);
			}
		}
	}


	public String getNumber() {
		return this.number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}


	public String getExtension() {
		return this.extension;
	}
	
	public void setExtension(String extension) {
		this.extension = extension;
	}



	public String toString() 
	{
		return new ToStringBuilder(this)
			.append("id", getId())
			.append("countryCode", getCountryCode())
			.append("areaCode", getAreaCode())
			.append("number", getNumber())
			.append("extension", getExtension())
			.toString();
	}

	/**  Two phone numbers are equal if all their business fields are equal */
	public boolean equals(Object other)
	{
		if ( (this == other ) ) return true;
		if ( !(other instanceof PhoneNumberImpl) ) return false;
		PhoneNumberImpl castOther = (PhoneNumberImpl)other; 

		return new EqualsBuilder()
			.append(this.getCountryCode(), castOther.getCountryCode())
			.append(this.getAreaCode(),    castOther.getAreaCode())
			.append(this.getNumber(),      castOther.getNumber())
			.append(this.getExtension(),   castOther.getExtension())
			.isEquals();
	}

	public int hashCode() 
	{
		return new HashCodeBuilder()
		.append(this.getCountryCode())
		.append(this.getAreaCode())
		.append(this.getNumber())
		.append(this.getExtension())
		.toHashCode();
	}


	@SuppressWarnings("unchecked")
	public Object clone() 
	{
		try 
		{
			PhoneNumberImpl result = (PhoneNumberImpl) super.clone();
		
			result.setId(-1);
			
			return result;
		} 
		catch (CloneNotSupportedException e) 
		{
			return null;
		}
	}
	
	
} // end class PhoneNumberImpl
