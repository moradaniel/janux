package biz.janux.payment.gateway;

import biz.janux.payment.EncryptedStorageServiceMockImpl;
import biz.janux.payment.gateway.console.ConsoleController;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import javax.annotation.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 ***************************************************************************************************
 * Tests the application initialization process, whereby a KeyInitEvent is generated when a Password
 * Encryption Key is provided via the UI that is handled via the AppInitializer framework class and
 * a pluggable AppInitStrategy subclass
 *
 * @author <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.4.0 - 2012-02-26
 ***************************************************************************************************
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ApplicationContext.xml" })
public class AppInitTest 
{
	private static final String TEST_ENCRYPTED_PASSWORD = "s6Qh0xxF5KFuSFICXHaMp4G06EVl3HXq";
	private static final String TEST_DECRYPTED_PASSWORD = "aVeryLongPassword";

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	AppInitializer appInitializer;

	@Autowired
	EncryptedStorageServiceMockImpl storageService;

	@Test
	public void testAppInitializer()
	{
		assertFalse(this.appInitializer.isInit());

		assertEquals(TEST_ENCRYPTED_PASSWORD, this.storageService.getCredential().getPassword());
		log.debug("encrypted password: '{}'", TEST_ENCRYPTED_PASSWORD);

		KeyInitEvent init = new KeyInitEvent(ConsoleController.DEFAULT_INIT_KEY);
		this.appInitializer.onApplicationEvent(init);

		String decryptedString = ((AppInitStrategyMock)this.appInitializer.getAppInitStrategy()).getStringEncryptor().decrypt(TEST_ENCRYPTED_PASSWORD);
		log.debug("decrypted password: '{}'", decryptedString);

		assertEquals(TEST_DECRYPTED_PASSWORD, this.storageService.getCredential().getPassword());
		assertTrue(this.appInitializer.isInit());
	}

}

