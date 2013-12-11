package biz.janux.payment.gateway;

import static biz.janux.payment.gateway.console.ConsoleConstant.*;
import static biz.janux.payment.gateway.AppStatus.*;

import java.util.Properties;

import org.jasypt.encryption.pbe.PBEStringEncryptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.ApplicationListener;


/**
 ***************************************************************************************************
 * This class provides basic support for a listener that listens for a Password Encryption Key to be
 * provided to the application, and upon receipt of such key, uses a plugged-in {@link
 * AppInitStrategy} class to decrypt the various credentials that need to be decrypted in the spring
 * context files; it provides a String Encryptor/Decryptor field using the {@link
 * org.jasypt.encryption.pbe.PBEStringEncryptor} class; 
 *
 * @see AppInitMock for an example that decrypts a fake encrypted password injected in 
 * {@link biz.janux.payment.EncryptedStorageServiceMockImpl}
 *
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.4.0 - 2012-02-05
 ***************************************************************************************************
 */
public class AppInitializer implements ApplicationListener<KeyInitEvent>
{
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	protected Properties appStatus;
	private   AppInitStrategy appInitStrategy;


	/** The Application Initialization Strategy that this initializer should use */
	public AppInitStrategy getAppInitStrategy() { return appInitStrategy;}
	public void setAppInitStrategy(AppInitStrategy o) { appInitStrategy = o; }

	/** The Properties map that holds global application variables */
	public Properties getAppStatus() {
		return this.appStatus;
	}

	public void setAppStatus(Properties props) {
		this.appStatus = props;
	}

	/** Convenience method to access the global variable AppStatus.IS_INITIALIZED */
	protected boolean isInit() {
		return Boolean.parseBoolean(this.getAppStatus().getProperty(IS_INITIALIZED));
	}

	protected void setInit(boolean isInit) {
		this.getAppStatus().setProperty(IS_INITIALIZED, isInit+"");
	}


	/** 
	 * Framework method that executes the domain specific preInit and postInit of the injected
	 * AppInitStrategy instance, and sets the AppStatus.IS_INITIALIZED variable to true 
	 * if all went well
	 */
	public void onApplicationEvent(KeyInitEvent event) 
	{
		//String secretKey = event.getKey();
		this.getAppInitStrategy().setPasswordEncryptionKey(event.getKey());

		log.debug("Before init: appStatus.isInitialized = {}", this.isInit());

		// Do all the adapter specific init work
		log.info("Running preInit()... ");
		this.getAppInitStrategy().preInit();

		// Everything seems to have worked so far, declare victory
		log.info("Changing appStatus.isInitialized to true...");
		this.setInit(true);

		// adapter specific post init work, if necessary
		log.info("Running posInit()... ");
		this.getAppInitStrategy().postInit();

		log.debug("After init: appStatus.isInitialized = {}", this.isInit());
		log.info("Application has been properly initialized");

	}

} // end class AppInitializer
