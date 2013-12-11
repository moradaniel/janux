package biz.janux.payment;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.janux.util.JanuxToStringStyle;

public class AuthorizationHotelImpl extends AuthorizationImpl implements AuthorizationHotel{

		Integer stayDuration;

		/**
		 * used by hibernate 
		 */
		public AuthorizationHotelImpl() {
			super();
		}

		public AuthorizationHotelImpl(BusinessUnit businessUnit, CreditCard creditCard, BigDecimal authAmount,Integer stayDuration, String externalSourceId) {
			super(businessUnit,creditCard,authAmount, externalSourceId);
			setStayDuration(stayDuration);
		}

		public Integer getStayDuration() {
			return stayDuration;
		}

		public void setStayDuration(Integer stayDuration) {
			this.stayDuration = stayDuration;
		}
		
	
		public String toString() 
		{
			ToStringBuilder sb = new ToStringBuilder(this, JanuxToStringStyle.COMPACT);
			
			sb.append(super.toString());
			if (getStayDuration() != null)	sb.append("stayDuration",   getStayDuration());
			return sb.toString();
		}


		public boolean equals(Object other)
		{
			if ( (this == other) ) return true;
			if ( !(other instanceof AuthorizationHotelImpl) ) return false;

			AuthorizationHotelImpl castOther = (AuthorizationHotelImpl)other; 

			return new org.apache.commons.lang.builder.EqualsBuilder()
				.append(this.getUuid(), castOther.getUuid())
				.isEquals();
		}

		public int hashCode() 
		{
			return new org.apache.commons.lang.builder.HashCodeBuilder()
				.append(this.getUuid())
				.toHashCode();
		}
}
