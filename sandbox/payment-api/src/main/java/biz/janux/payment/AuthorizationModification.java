package biz.janux.payment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Represents each authorization request.
 * 
 * If the {@link Authorization} was incremented or reverted, it will have more than one instance of {@link AuthorizationModification}
 *    
 * @author albertobuffagni@gmail.com
 *
 */

public interface AuthorizationModification extends Serializable {

	/**       
	 * A random alphanumeric string that uniquely identifies this Credit Card in the Janux Payment
	 * Service. This is the code that external clients must use to reference the Credit Card when
	 * calling the Janux Payment Service.
	 */
	public String getUuid();
	
	public Date getDate();
	public void setDate(Date date);
	
	/**
	 * The sign of this field will indicate if the modifications is a increment or reversal.
	 */
	public BigDecimal getAmount();
	public void setAmount(BigDecimal amount);
	
}
