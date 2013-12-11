package biz.janux.payment;

import java.math.BigDecimal;
import java.util.Date;


/**
 * This service is a facade of the {@link CreditCardProcessingService}. 
 *    
 * @author albertobuffagni@gmail.com
 *
 */
public interface CreditCardProcessingRemotingService extends RemotingService 
{
	/**
	 * Sends an authorization request to the processor. 
	 * When the method is complete, the TransactionResponse property will indicate 
	 * the status of the request (whether or not it was approved). 
	 * The response code merely indicates that funds were available and were put on hold 
	 * for the merchant. The response code does not indicate that the transaction passed 
	 * any sort of fraud tests.
	 * 	
	 * You should inspect the values of the AVSResponse 
	 * properties to determine if you want to honor the transaction and capture 
	 * the funds or let the transaction be voided.
	 * @param businessUnitUuid
	 * @param creditCardUuid
	 * @param authAmount
	 * @param stayDuration 
	 */
	public AuthorizationHotel authorize(String businessUnitUuid,String creditCardUuid, BigDecimal authAmount, Integer stayDuration,String externalSourceId);

	public AuthorizationHotel increment(String originalAuthorizationUuid,BigDecimal amount);
	
	public AuthorizationHotel reverse(String originalAuthorizationUuid,BigDecimal amount);

	public AuthorizationHotel modify(String originalAuthorizationUuid,BigDecimal amount);
	
	/**
	 * A void is for a transaction that has been authorized, but not captured. 
	 * This is done before any funds have been actually transferred. 
	 * A void cancels the originally authorized transaction so it will not be captured.
	 * 
	 * If you do not perform a Void and you do not capture the authorized funds, 
	 * after a period of time the authorized transaction will be timed out by the processor, 
	 * and the hold on the funds will go away.
	 */
	public void voidAuthorization(String originalAuthorizationUuid);
	
	/**
	 * Create off-line credit to be settled then. 
	 * 
	 * This authorization can be create calling the processor for voice authorization, or using
	 * alternate equipment such as a verifone, omnipac, or hypercom terminal to obtain authorization.  
	 * (Note:  you do not need authorization for a credit.)
	 * 
	 * @param businessUnitUuid
	 * @param creditCardUuid
	 * @param authAmount
	 */
	public AuthorizationHotel authorizeOffline(String businessUnitUuid, String creditCardUuid, BigDecimal authAmount, Integer stayDuration, String approvalCode,String externalSourceId);
	
	/**
	 * If the authAmount is negative is a CREDIT, if it is positive is a PAYMENT
	 * If it is a payment, the system looks for a existent authorization. If it is not, the system processes one.
	 * 
	 * @param amount
	 * @param date
	 * @param creditCardUuid
	 * @param businessUnitUuid
	 * @param purchaseIdentifier
	 * @param averageRate
	 * @param checkoutDate
	 * @param stayDuration it is used when a authorization has to be created before of payment
	 *
	 * @throws Exception
	 */
	public SettlementItemHotel applyPayment(BigDecimal amount, Date date, String creditCardUuid, String businessUnitUuid, String purchaseIdentifier, BigDecimal averageRate, Date checkoutDate, Integer stayDuration,String externalSourceIdAuthorization,String externalSourceId);
	
	/**
	 * Used when the authorization is known.
	 * 
	 * @param amount
	 * @param date
	 * @param creditCardUuid
	 * @param businessUnitUuid
	 * @param purchaseIdentifier
	 * @param averageRate
	 * @param checkOutDate
	 * @param stayDuration
	 * @param externalSourceIdAuthorization
	 * @param externalSourceId
	 * @param authorizationUuid
	 * @return
	 */
	public SettlementItemHotel applyPayment(BigDecimal amount, Date date, String creditCardUuid, String businessUnitUuid, String purchaseIdentifier, BigDecimal averageRate, Date checkOutDate, Integer stayDuration,String externalSourceIdAuthorization,String externalSourceId,String authorizationUuid);
	
	/**
	 * Run the settlement process, so all payments or credits are captured. 
	 * 
	 * It will settle the transactions it has performed that day.  
	 * By communicating again with the processor, the funds that were charged earlier are then transferred 
	 * for the cardholders accounts to the merchants account. 
	 * 
	 * This method should be synchronized. 
	 * 
	 * @param businessUnitUuid
	 */
	public Settlement settlement(String businessUnitUuid);
	
	/**
	 * This method is used only for test. The test increment this number for avoid duplicated batch
	 *
	 * @param businessUnitUuid
	 * @deprecated
	 */
	public Settlement settlement(String businessUnitUuid, int batchNumber);
	
	/**
	 * Setup the initial batch number for the {@link MerchantAccount} of the {@link BusinessUnit}
	 * 
	 * @param businessUnitUuid
	 * @param number
	 * @return
	 */
	public int initializeBatchNumber(String businessUnitUuid,int number);
	
	public Object generic(Object object[]);

}
