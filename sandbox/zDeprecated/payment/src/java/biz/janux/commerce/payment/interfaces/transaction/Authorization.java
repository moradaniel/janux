package biz.janux.commerce.payment.interfaces.transaction;

import java.math.BigDecimal;

/**
 * 
 * @author Nilesh
 *	</br>
 *	Interface for Authorization
 *	</br>
 *	Extends Transaction
 */
public interface Authorization extends Transaction {
	/**
	 * 
	 * @return  BigDecimal
	 */
	public BigDecimal getAuthAmount();
	/**
	 * 
	 * @param amount
	 */
	public void setAuthAmount(BigDecimal amount);
}
