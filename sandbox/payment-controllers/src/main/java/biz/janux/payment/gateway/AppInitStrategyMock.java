package biz.janux.payment.gateway;

import biz.janux.payment.mock.Credential;

import org.jasypt.encryption.pbe.PBEStringEncryptor;;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 ***************************************************************************************************
 * This class implements a mock initialization strategy and can be used as a model for a production
 * initialization process; in this case, it decrypts the 'password' field of a {@link Credential}
 * object that may be injected in a service bean to access an encrypted storage back-end; this class
 * could be used as is, if it's only necessary to decrypt a single credential.  This class should be
 * enhanced to decrypt a List of Credential instances, and could be renamed AppInitStrategySimple,
 * for example, so that it can be used generically by implementations with simple decryption needs.
 *
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @see AppInitializer
 * @since 0.4.0 - 2012-02-25
 ***************************************************************************************************
 */
public class AppInitStrategyMock implements AppInitStrategy
{
	Logger log = LoggerFactory.getLogger(this.getClass());

	protected String passwordEncryptionKey;
	protected PBEStringEncryptor stringEncryptor;
	protected Credential credential;

	public String getPasswordEncryptionKey() { return passwordEncryptionKey; }
	public void setPasswordEncryptionKey(String o) { passwordEncryptionKey = o;}

	/** 
	 * Contains the password string to decrypt; TODO: this should be changed to a List of Credentials
	 * to make it more generic and re-usable in specific implementations
	 * */
	public Credential getCredential() { return this.credential;}
	public void setCredential(Credential n) { this.credential = n; }

	/**
	 * The encryptor that is used to encrypt/decrypt the various system-level passwords on which the
	 * application relies
	 */
	public PBEStringEncryptor getStringEncryptor()
	{
		if (this.stringEncryptor == null) {
			throw new IllegalStateException("PasswordDecryptor.stringEncryptor is null and needs to be properly configured");
		}

		return this.stringEncryptor;
	}

	public void setStringEncryptor(PBEStringEncryptor o) {
		this.stringEncryptor = o;
	}


	/** decrypts the password of the Encrypted Storage Service */
	public void preInit()
	{
		// Remember to do this in your strategy, or you won't be able to decrypt
		log.warn("Initializing StringDecryptor with Password Encryption Key...");
		this.getStringEncryptor().setPassword(this.getPasswordEncryptionKey());

		String encryptedPass = getCredential().getPassword();
		log.debug("encrypted password is: '{}'", encryptedPass);

		String decryptedPass = this.getStringEncryptor().decrypt(encryptedPass);

		// NEVER LOG THE PASSWORD IN A STRATEGY DEPLOYED IN PRODUCTION
		log.trace("decrypted password is: '{}'", decryptedPass);

		log.debug("Setting decrypted password in credential");
		this.getCredential().setPassword(decryptedPass);
	}

	public void postInit() {
		log.debug("Nothing to do in postInit()");
	}

} // end class AppInitStrategyMock
