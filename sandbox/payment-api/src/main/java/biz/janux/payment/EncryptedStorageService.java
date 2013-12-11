package biz.janux.payment;

/**
 * 
 * Service for store and encrypt SensitiveData. 
 * It can be used by client-applications such as e-commerce, 
 * payment-processing, business-intelligence, health-care or other applications that deal with sensitive data 
 *  
 * @author albertobuffagni@gmail.com
 *
 * @param <Token> Data type of the identifier of the encrypted in the storage.
 * @param <SensitiveData> Data type of the data encrypted and stored in the encrypted storage.
 */
public interface EncryptedStorageService<Token,SensitiveData> {

	/**
	 * Encrypt and save a SensitiveData and returns a Token identifies this data.
	 */
	public Token encryption(SensitiveData sensitiveData);

	/**
	 * Decrypt the Token generated from SensitiveData
	 */
	public SensitiveData decryption(Token token);

	/**
	 * Remove a SensitiveData of the storage. If return true the sensitive data was removed.
	 */
	public boolean delete(Token token);

	/**
	 * Search if exists a Token by SensitiveData
	 */
	public Token search(SensitiveData sensitiveData);

}
