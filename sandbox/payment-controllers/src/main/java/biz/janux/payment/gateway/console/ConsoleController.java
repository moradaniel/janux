package biz.janux.payment.gateway.console;

import static biz.janux.payment.gateway.console.ConsoleConstant.*;
import static biz.janux.payment.gateway.AppStatus.*;
import biz.janux.payment.gateway.KeyInitEvent;

import java.util.Map;
import java.util.Properties;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import org.jasypt.digest.StringDigester;
import org.jasypt.encryption.pbe.PBEStringEncryptor;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.util.StringUtils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 ***************************************************************************************************
 * This controller provides the functionality necessary for the administrative console of the
 * payment gateway, such as initializing the gateway with the key to decrypt the passwords of
 * collaborating systems, and resetting such key. 
 *
 * In addition, this controller provides a feature whereby a new password encryptor can be
 * instantiated in a util screen with a new secret key and can be used to generate new passwords. In
 * order to implement this feature, it is necessary to instantiate a new PBEStandardStringEncryptor
 * on every request to this page, because the encryption key can only be set one time.  In order to
 * accomplish this, we defined the bean in the Spring config with a scope of 'prototype', so that a
 * new instance of such beans can be retrieved from the ApplicationContext as needed.
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.4.0 - 2012-01-11
 ***************************************************************************************************
 */
@Controller
public class ConsoleController implements ApplicationContextAware
{
	/** The default and not-so-secret initialization secret key: "initializeMe" */
	public static final String DEFAULT_INIT_KEY = "initializeMe";

	/** The name of the utility password encryptor prototype in the spring config: "utilStringEncryptor" */
	private static final String BEAN_NAME_UTIL_STRING_ENCRYPTOR = "utilStringEncryptor";

	Logger log = LoggerFactory.getLogger(this.getClass());

	private ApplicationContext applicationContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
			this.applicationContext = applicationContext;
	}

	protected Properties getAppStatus() {
		return (Properties)this.applicationContext.getBean(BEAN_APP_STATUS);
	}

	protected boolean isInit() {
		return Boolean.parseBoolean(this.getAppStatus().getProperty(IS_INITIALIZED));
	}

	protected void setInit(boolean isInit) 
	{
		this.getAppStatus().setProperty(IS_INITIALIZED, isInit+"");
	}


	/** 
	 * Used to initialize the payment-gateway with a secret Password Encryption Key that can be used
	 * to decrypt the system-level passwords included in the configuration
	 */
	@RequestMapping(value = "initialize", method = {RequestMethod.GET, RequestMethod.POST})
	public Map initializeApplication(
			@RequestParam(value="passEncryptKey", required = false) final String secretKey,
			@RequestParam(value="submitted",      required = false) final boolean isFormSubmitted 
			) 
	{
		Map model = new HashMap();
		List<String> messageList = new ArrayList<String>();
		model.put(MSG_LIST, messageList);
		model.put(IS_APP_INIT, isInit());

		// The user is accessing the page via a GET
		if (!isFormSubmitted) 
		{
			if (isInit()) {
				// If the app has been successfully initialized, show status information.
				messageList.add("The Payment Gateway has been properly initialized.");
			}
			return model;

		// The user has submitted the form, validate the key and trigger a KeyInitEvent
		} else {

			/*
			if (StringUtils.isEmpty(secretKey)) {
				messageList.add("Please enter a non-null key");
				return model;
			}
			*/

			if (validateKeyDigest(secretKey) == false) {
				messageList.add("The initialization key that you have entered does not match the Key Digest. Re-enter the key, or check the configuration of the application");
				return model;
			}

			// Fire event that indicates that a key was provided to initialize the application.
			// The actions taken at initialization can be customized for different deployments
			// in the bean that handles this event
			this.applicationContext.publishEvent(new KeyInitEvent(secretKey));

			log.debug("Finished initializing, isInit() is {}", isInit());
			model.put(IS_APP_INIT, isInit());

			if (isInit()) {
				messageList.add("The Payment Gateway has been properly initialized.");
			} else {
				messageList.add("There was a problem initializing the Payment Gateway, check the logs.");
			}

			return model;
		}
	}





	


	/** 
	 * Utility to generate a Key Digest from a key; useful when it is necessary to change the
	 * secretKey, and the new pre-stored KeyDigest must be modified in ApplicationContext.properties
	 */
	@RequestMapping(value = "util/string_digester", method = {RequestMethod.GET, RequestMethod.POST})
	public Map generateKeyDigest(@RequestParam(value="encryptMe", required = false) final String encryptMe) 
	{
		Map model = new HashMap();

		if ( StringUtils.hasLength(encryptMe) ) {
			model.put(ENCRYPT_ME, encryptMe);
			model.put(STRING_DIGEST, this.getStringDigester().digest(encryptMe));
		}

		return model;
	}


	/**
	 * Utility to encrypt/decrypt a Strings, based on an encryption key provided; this utility
	 * is useful to generate the encrypted password strings that will be populated in the
	 * ApplicationContext.properties, based on the secret Password Encryption Key (PEK) that is used
	 * to initialize the application at start-up.
	 */
	@RequestMapping(value = "util/encrypt_decrypt", method = {RequestMethod.GET, RequestMethod.POST})
	public Map encryptDecryptString(
			@RequestParam(value="encryptKey", required = false) final String encryptKey,
			@RequestParam(value="encryptMe",  required = false) final String encryptMe,
			@RequestParam(value="decryptMe",  required = false) final String decryptMe
	) 
	{
		Map model = new HashMap();

		// initial form load via a GET
		if (encryptKey == null) return model;

		// nothing to do if we do not have an encryption key
		if ("".equals(encryptKey)) {
			List<String> messageList = new ArrayList<String>();
			messageList.add("Please enter a non-empty Encryption Key");
			model.put(MSG_LIST, messageList);
			return model;
		}

		PBEStringEncryptor encryptor = this.createUtilStringEncryptor();
		encryptor.setPassword(encryptKey);
		model.put(ENCRYPT_KEY, encryptKey);

		if ( StringUtils.hasLength(encryptMe) ) {
			model.put(ENCRYPT_ME, encryptMe);
			model.put(ENCRYPTED_STRING, encryptor.encrypt(encryptMe));
		}

		if ( StringUtils.hasLength(decryptMe) ) {
			model.put(DECRYPT_ME, decryptMe);
			model.put(DECRYPTED_STRING, encryptor.decrypt(decryptMe));
		}

		return model;
	}



	/** 
	 * Returns true if the secretKey provided matches with the Key Digest stored by this controller;
	 * this makes it possible for the administrator to verify that they have provided the proper key
	 * needed to initialize the payment-gateway
	 */
	protected boolean validateKeyDigest(final String secretKey)
	{
		if (this.getInitKeyDigest() == null)
			throw new IllegalStateException("ConsoleController.initKeyDigest is null, and has not been properly configured");

    return this.getStringDigester().matches(secretKey, this.getInitKeyDigest());
	}


	private String initKeyDigest;

	/** A digest of the key is injected in the configuration to ensure that no typo is made when the key is entered */
  public String getInitKeyDigest() 
	{ 
		if (initKeyDigest == null) {
			this.initKeyDigest = this.getStringDigester().digest(DEFAULT_INIT_KEY);
		}
		return initKeyDigest;
	}

  public void setInitKeyDigest(String s) { initKeyDigest = s; }


	private PBEStringEncryptor createUtilStringEncryptor() {
		return this.applicationContext.getBean(BEAN_NAME_UTIL_STRING_ENCRYPTOR, PBEStringEncryptor.class);
	}

	private StringDigester stringDigester;

	/** 
	 * A one-way encryptor that we use to create a hash of the key input in the initialization form,
	 * so that we can compare it with the Key Digest of the actual key stored in the configuration.
	 */
  public StringDigester getStringDigester() 
	{ 
		if (this.stringDigester == null) {
			throw new IllegalStateException("ConsoleController.stringDigester is null, please configure it properly");
		}
		return stringDigester;
	}

  public void setStringDigester(StringDigester o) { stringDigester = o; }

	private PBEStringEncryptor passwordEncryptor;

	/** 
	 * The encryptor that is used to encrypt/decrypt the various system-level passwords on which the
	 * application relies 
	 */
  public PBEStringEncryptor getPasswordEncryptor() 
	{ 
		if (this.passwordEncryptor == null) {
			throw new IllegalStateException("ConsoleController.passwordEncryptor is null and needs to be properly configured");
		}

		return this.passwordEncryptor;
	}

  public void setPasswordEncryptor(PBEStringEncryptor o) { 
		this.passwordEncryptor = o; 
	}



	/** 
	 * A utility encryptor that can be used in a utility page to generate new encrypted passwords
	 * based on a key input via the UI; this can be useful when changing the initialization key.
	 */
	/*
	private PBEStringEncryptor utilStringEncryptor;
  public PBEStringEncryptor getUtilStringEncryptor() 
	{ 
		if (this.utilStringEncryptor == null) {
			throw new IllegalStateException("ConsoleController.utilStringEncryptor is null and needs to be properly configured");
		}

		return this.utilStringEncryptor;
	}

  public void setUtilStringEncryptor(PBEStringEncryptor o) { 
		this.utilStringEncryptor = o; 
	}
	*/


} // end class


