package biz.janux.payment;

/**
 * 
 * Represents a Address Verification Transaction
 * 
 * AVS (Address Verification Service) is where the address information of the customer 
 * is checked for accuracy during the authorization request to help prevent fraud. 
 * This information consists of a street address and zip code.
 * After the authorization is sent, the ResponseAVS property will contain the results 
 * of the address verification which can be used by the merchant to decide 
 * whether or not to honor the transaction and capture the funds or let it void.
 * 
 * @author Nilesh
 * @author albertobuffagni@gmail.com
 */
public interface AddressVerification extends Transaction<AddressVerificationResponse> {
}
