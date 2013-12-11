package biz.janux.payment.test;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import biz.janux.payment.EncryptedStorageService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:PaymentStorageStrongKeyContext.xml" })
public class StrongKeyIntegrationTest {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EncryptedStorageService<String, String> encryptedStorageService;
	
	String CREDIT_CARD_VISA_TOKEN = "9999000000000018";
	String NUMBER_VISA="4111111111111111";

	@Test
	public void testAll()
	{
		//these lines have to be uncommented to execute the test on a StrongKey appliance
		//encryption(NUMBER_VISA);
		//search(NUMBER_VISA);
		//decryption(CREDIT_CARD_VISA_TOKEN);
		//delete(CREDIT_CARD_VISA_TOKEN);
	}
	
	private void decryption(String token) {
		String number = getEncryptedStorageService().decryption(token);	
		Assert.assertEquals(NUMBER_VISA,number);
	}
	
	private void delete(String token) {
		getEncryptedStorageService().delete(token);
		/**
		 * TODO add the assert
		 */
	}

	public void encryption(String number)
	{
		String token = getEncryptedStorageService().encryption(number);	
		Assert.assertEquals(CREDIT_CARD_VISA_TOKEN,token);
	}
	
	public void search(String number)
	{
		String token = getEncryptedStorageService().search(number);	
		Assert.assertEquals(CREDIT_CARD_VISA_TOKEN,token);
	}

	public EncryptedStorageService<String, String> getEncryptedStorageService() {
		return encryptedStorageService;
	}

	public void setEncryptedStorageService(EncryptedStorageService<String, String> encryptedStorageService) {
		this.encryptedStorageService = encryptedStorageService;
	}


}
