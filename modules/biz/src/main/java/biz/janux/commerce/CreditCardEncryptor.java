package biz.janux.commerce;

/**
 * Interface for convenience classes used to encrypt and decrypt strings, such as a Credit Card's 
 * Personal Account Number (PAN); this interface should be re-declared more generically elsewhere,
 * as there are probably other business contexts in which it is desirable to have 2-way encryption
 * of information.
 *
 * @author  <a href="mailto:alberto.buffagni@janux.org">Alberto Buffagni</a>
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.4
 */
public interface CreditCardEncryptor {

	/**
	 * Decrypt a number
	 * @param num
	 * @return the unencrypted number
	 * @throws Exception
	 */
	public String decrypt(String num) throws Exception;

	/**
	 * Encrypt a number
	 * @param num
	 * @return the encrypted number
	 * @throws Exception
	 */
	public String encrypt(String num) throws Exception;

}
