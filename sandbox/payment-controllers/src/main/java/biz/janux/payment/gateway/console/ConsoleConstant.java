package biz.janux.payment.gateway.console;

/**
 ***************************************************************************************************
 * Convenience interface to enumerate the string literals that are used in the Console Controller and
 * its views;
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.4.0
 ***************************************************************************************************
 */
public interface ConsoleConstant
{
	/** The name of the bean where the application's global variables are stored: "appStatus" */
	public final static String BEAN_APP_STATUS = "appStatus";

	/** variable name used to store whether the app has been initalizatied : "isAppInit" */
	public static final String IS_APP_INIT = "isAppInit";

	/** variable name used to store messages to be displayed on a view: "messageList" */
	public static final String MSG_LIST = "messageList";

	/** variable name to store a digested string: "stringDigest" */
	public static final String STRING_DIGEST = "stringDigest";

	/** variable name to store an encryption key "encrypteKey" */
	public static final String ENCRYPT_KEY = "encryptKey";

	/** variable name to store a string to be encrypted "encryptMe" */
	public static final String ENCRYPT_ME  = "encryptMe";

	/** variable name to store a string to be decrypted "decryptMe" */
	public static final String DECRYPT_ME  = "decryptMe";

	/** variable name to store an encrypted string: "encryptedString" */
	public static final String ENCRYPTED_STRING = "encryptedString";

	/** variable name to store an decrypted string: "decryptedString" */
	public static final String DECRYPTED_STRING = "decryptedString";
}
