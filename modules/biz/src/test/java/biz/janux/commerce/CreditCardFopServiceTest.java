package biz.janux.commerce;

import biz.janux.test.TransactionalBizTestAbstract;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 ***************************************************************************************************
 *
 *
 * @author  <a href="mailto:alberto.buffagni@janux.org">Alberto Buffagni</a>
 * @since
 ***************************************************************************************************
 */
public class CreditCardFopServiceTest extends TransactionalBizTestAbstract
{
	private CreditCardFopService creditCardFopService;
	
	/** define the tests to be run in this class */
	public static Test suite() throws Exception
	{
		final TestSuite suite = new TestSuite(CreditCardFopServiceTest.class);
		return suite;
	}
	
	public void testEncryptCreditCard()
	{
		/**
		 * cc no encrypted and no masked
		 */
		CreditCardFop cc = new CreditCardFop();
		cc.setCardNumber("4111111111111111");
		cc.setCardNumberToShow("1212132132132");
		
		getCreditCardFopService().encryptCreditCardNumber(cc, false);
		
		assertEquals("v+7gSuFUS4fmOIBhM1my5EhGSKNEpRrK", cc.getCardNumber());
		assertEquals(null, cc.getCardNumberToShow());
		assertEquals(true,cc.isEncrypted());

		
		try
		{
			/**
			 * cc encrypted and no masked
			 */
			getCreditCardFopService().encryptCreditCardNumber(cc, false);
		
			fail("Should have gotten CreditCardFopException");
	    } catch (CreditCardFopException expected) {
	        ; // Expected - intentional
	    }
	    
	    try
		{
			/**
			 * cc no encrypted and masked
			 */
	    	cc = new CreditCardFop();
			cc.setCardNumber("4***********1111");
			cc.setMasked(true);
		
			getCreditCardFopService().encryptCreditCardNumber(cc, false);
			
			fail("Should have gotten CreditCardFopException");
	    } catch (CreditCardFopException expected) {
	        ; // Expected - intentional
	    }
	    
		try
		{
			/**
			 * cc number null
			 */
			cc = new CreditCardFop();
			cc.setCardNumber(null);
			
			getCreditCardFopService().encryptCreditCardNumber(cc, false);

			fail("Should have gotten CreditCardFopException. The cc number is null");
	    } catch (CreditCardFopException expected) {
	        ; // Expected - intentional
	    }
				

	    /**
	     **************************************
	     * To Show 
	     **************************************
	     */
	    
	    /**
		 * cc no encrypted and no masked
		 */
		cc = new CreditCardFop();
		cc.setCardNumber("4111111111111111");
		
		getCreditCardFopService().encryptCreditCardNumber(cc, true);
		
		assertEquals("4111111111111111",cc.getCardNumber());
		assertEquals("v+7gSuFUS4fmOIBhM1my5EhGSKNEpRrK", cc.getCardNumberToShow());
		assertEquals(false,cc.isEncrypted());
		
		/**
		 * cc encrypted and no masked
		 */
		cc = new CreditCardFop();
		cc.setCardNumber("v+7gSuFUS4fmOIBhM1my5EhGSKNEpRrK");
		cc.setEncrypted(true);
		
		getCreditCardFopService().encryptCreditCardNumber(cc, true);
	
		assertEquals("v+7gSuFUS4fmOIBhM1my5EhGSKNEpRrK", cc.getCardNumber());
		assertEquals("v+7gSuFUS4fmOIBhM1my5EhGSKNEpRrK", cc.getCardNumberToShow());
		assertEquals(true, cc.isEncrypted());
	    
		try
		{
			/**
			 * cc no encrypted and masked
			 */
	    	cc = new CreditCardFop();
			cc.setCardNumber("4***********1111");
			cc.setMasked(true);

			getCreditCardFopService().encryptCreditCardNumber(cc, true);
			
			fail("Should have gotten CreditCardFopException");
	    } catch (CreditCardFopException expected) {
	        ; // Expected - intentional
	    }
	    
		try
		{
			/**
			 * cc number null
			 */
			cc = new CreditCardFop();
			cc.setCardNumber(null);
			
			getCreditCardFopService().encryptCreditCardNumber(cc, true);

			fail("Should have gotten CreditCardFopException. The cc number is null");
	    } catch (CreditCardFopException expected) {
	        ; // Expected - intentional
	    }
		
	}
	
	public void testDecryptCreditCard()
	{
		/**
		 * cc no encrypted and no masked
		 */
		CreditCardFop cc = new CreditCardFop();
		cc.setCardNumber("4111111111111111");

		try
		{
			getCreditCardFopService().decryptCreditCardNumber(cc, false);
			
			fail("Should have gotten CreditCardFopException");
	    } catch (CreditCardFopException expected) {
	        ; // Expected - intentional
	    }

	    
		/**
		 * cc encrypted and no masked
		 */
	    cc = new CreditCardFop();
		cc.setCardNumber("v+7gSuFUS4fmOIBhM1my5EhGSKNEpRrK");
		cc.setEncrypted(true);
		
		getCreditCardFopService().decryptCreditCardNumber(cc, false);
		
	    assertEquals("4111111111111111",cc.getCardNumber());
	    assertEquals(null, cc.getCardNumberToShow());
		assertEquals(false,cc.isEncrypted());
		assertEquals(false,cc.isMasked());
		
	    
	    try
		{
			/**
			 * cc no encrypted and masked
			 */
	    	cc = new CreditCardFop();
			cc.setCardNumber("4***********1111");
			cc.setMasked(true);

			getCreditCardFopService().decryptCreditCardNumber(cc, false);
			
			fail("Should have gotten CreditCardFopException");
	    } catch (CreditCardFopException expected) {
	        ; // Expected - intentional
	    }
	    
		try
		{
			/**
			 * cc number null
			 */
			cc = new CreditCardFop();
			cc.setCardNumber(null);
			
			getCreditCardFopService().decryptCreditCardNumber(cc, false);

			fail("Should have gotten CreditCardFopException. The cc number is null");
	    } catch (CreditCardFopException expected) {
	        ; // Expected - intentional
	    }
				

	    /**
	     **************************************
	     * To Show 
	     **************************************
	     */
	    
		cc = new CreditCardFop();
		cc.setCardNumber("4111111111111111");

		try
		{
			/**
			 * cc no encrypted and no masked
			 */
			getCreditCardFopService().decryptCreditCardNumber(cc, true);
			
			fail("Should have gotten CreditCardFopException");
	    } catch (CreditCardFopException expected) {
	        ; // Expected - intentional
	    }

	    
		/**
		 * cc encrypted and no masked
		 */
	    cc = new CreditCardFop();
		cc.setCardNumber("v+7gSuFUS4fmOIBhM1my5EhGSKNEpRrK");
		cc.setEncrypted(true);
		
		getCreditCardFopService().decryptCreditCardNumber(cc, true);
		
		assertEquals("v+7gSuFUS4fmOIBhM1my5EhGSKNEpRrK",cc.getCardNumber());
		assertEquals("4111111111111111",cc.getCardNumberToShow());
	    assertEquals(true,cc.isEncrypted());
		assertEquals(false,cc.isMasked());

		
	    try
		{
			/**
			 * cc no encrypted and masked
			 */
	    	cc = new CreditCardFop();
			cc.setCardNumber("4***********1111");
			cc.setMasked(true);
			
			getCreditCardFopService().decryptCreditCardNumber(cc, true);
			
			fail("Should have gotten CreditCardFopException");
	    } catch (CreditCardFopException expected) {
	        ; // Expected - intentional
	    }
	    
		try
		{
			/**
			 * cc number null
			 */
			cc = new CreditCardFop();
			cc.setCardNumber(null);
			
			getCreditCardFopService().decryptCreditCardNumber(cc, true);

			fail("Should have gotten CreditCardFopException. The cc number is null");
	    } catch (CreditCardFopException expected) {
	        ; // Expected - intentional
	    }
	}

	public void testMaskCreditCardNumber()
	{
		/**
		 * Test with this configuration on the CreditCardMask class
		 * 
		 * first = 1;
		 * last  = 4;
		 * mask  = '*';
		 */
		
		/**
		 * cc no encrypted and no masked
		 */
		CreditCardFop cc = new CreditCardFop();
		cc.setCardNumber("4111111111111111");
		cc.setCardNumberToShow("1212132132132");

		getCreditCardFopService().maskCreditCardNumber(cc, false);

		assertEquals("4***********1111", cc.getCardNumber());
		assertEquals(null, cc.getCardNumberToShow());
		assertEquals(true,cc.isMasked());
		assertEquals(false,cc.isEncrypted());
		
		/**
		 * cc encrypted and no masked
		 */
		cc = new CreditCardFop();
		cc.setCardNumber("v+7gSuFUS4fmOIBhM1my5EhGSKNEpRrK");
		cc.setCardNumberToShow("1212132132132");
		cc.setEncrypted(true);

		getCreditCardFopService().maskCreditCardNumber(cc, false);
		
		assertEquals("4***********1111", cc.getCardNumber());
		assertEquals(null, cc.getCardNumberToShow());
		assertEquals(true,cc.isMasked());
		assertEquals(false,cc.isEncrypted());
	    
	    try
		{
			/**
			 * cc no encrypted and masked
			 */
	    	cc = new CreditCardFop();
			cc.setCardNumber("4***********1111");
			cc.setMasked(true);
			
			getCreditCardFopService().maskCreditCardNumber(cc, false);
			
			fail("Should have gotten CreditCardFopException");
	    } catch (CreditCardFopException expected) {
	        ; // Expected - intentional
	    }
	    
		try
		{
			/**
			 * cc number null
			 */
			cc = new CreditCardFop();
			cc.setCardNumber(null);
			
			getCreditCardFopService().maskCreditCardNumber(cc, false);

			fail("Should have gotten CreditCardFopException. The cc number is null");
	    } catch (CreditCardFopException expected) {
	        ; // Expected - intentional
	    }
				

	    /**
	     **************************************
	     * To Show 
	     **************************************
	     */
	    
	    /**
		 * cc no encrypted and no masked
		 */
		cc = new CreditCardFop();
		cc.setCardNumber("4111111111111111");

		getCreditCardFopService().maskCreditCardNumber(cc, true);
		
		assertEquals("4111111111111111",cc.getCardNumber());
		assertEquals("4***********1111", cc.getCardNumberToShow());
		assertEquals(false,cc.isEncrypted());
		assertEquals(false,cc.isMasked());
		
		/**
		 * cc encrypted and no masked
		 */
		cc = new CreditCardFop();
		cc.setCardNumber("v+7gSuFUS4fmOIBhM1my5EhGSKNEpRrK");
		cc.setEncrypted(true);
		
		getCreditCardFopService().maskCreditCardNumber(cc, true);

		assertEquals("v+7gSuFUS4fmOIBhM1my5EhGSKNEpRrK",cc.getCardNumber());
		assertEquals("4***********1111", cc.getCardNumberToShow());
		assertEquals(true,cc.isEncrypted());
		assertEquals(false,cc.isMasked());
	    
		/**
		 * cc no encrypted and masked
		 */
    	cc = new CreditCardFop();
		cc.setCardNumber("4***********1111");
		cc.setMasked(true);
		
		getCreditCardFopService().maskCreditCardNumber(cc, true);
		
		assertEquals("4***********1111",cc.getCardNumber());
		assertEquals("4***********1111", cc.getCardNumberToShow());
		assertEquals(false,cc.isEncrypted());
		assertEquals(true,cc.isMasked());

		
	    
		try
		{
			/**
			 * cc number null
			 */
			cc = new CreditCardFop();
			cc.setCardNumber(null);
			
			getCreditCardFopService().maskCreditCardNumber(cc, true);

			fail("Should have gotten CreditCardFopException. The cc number is null");
	    } catch (CreditCardFopException expected) {
	        ; // Expected - intentional
	    }
	}
	
	
	public void testPrepareCreditCardToShow(){

		/**
	     **************************************
	     * With display cc masked equal to false
	     **************************************
	     */
		
		/**
		 * cc no encrypted and no masked
		 */
		CreditCardFop cc = new CreditCardFop();
		cc.setCardNumber("4111111111111111");
		cc.setCardNumberToShow("1212132132132");

		getCreditCardFopService().prepareCreditCardToShow(cc, false);

		assertEquals("4111111111111111", cc.getCardNumberToShow());
		
		/**
		 * cc encrypted and no masked
		 */
		cc = new CreditCardFop();
		cc.setCardNumber("v+7gSuFUS4fmOIBhM1my5EhGSKNEpRrK");
		cc.setCardNumberToShow("1212132132132");
		cc.setEncrypted(true);

		getCreditCardFopService().prepareCreditCardToShow(cc, false);
		
		assertEquals("4111111111111111", cc.getCardNumberToShow());

		/**
		 * cc no encrypted and masked
		 */
    	cc = new CreditCardFop();
		cc.setCardNumber("4***********1111");
		cc.setMasked(true);

		getCreditCardFopService().prepareCreditCardToShow(cc, false);
		
		assertEquals("4***********1111", cc.getCardNumberToShow());

	    
		try
		{
			/**
			 * cc number null
			 */
			cc = new CreditCardFop();
			cc.setCardNumber(null);
			
			getCreditCardFopService().prepareCreditCardToShow(cc, false);

			fail("Should have gotten CreditCardFopException. The cc number is null");
	    } catch (CreditCardFopException expected) {
	        ; // Expected - intentional
	    }
	    
	    /**
	     **************************************
	     * With display cc masked equal to true
	     **************************************
	     */
	    
		/**
		 * cc no encrypted and no masked
		 */
		cc = new CreditCardFop();
		cc.setCardNumber("4111111111111111");
		cc.setCardNumberToShow("1212132132132");

		getCreditCardFopService().prepareCreditCardToShow(cc, true);

		assertEquals("4***********1111", cc.getCardNumberToShow());
		
		/**
		 * cc encrypted and no masked
		 */
		cc = new CreditCardFop();
		cc.setCardNumber("v+7gSuFUS4fmOIBhM1my5EhGSKNEpRrK");
		cc.setCardNumberToShow("1212132132132");
		cc.setEncrypted(true);

		getCreditCardFopService().prepareCreditCardToShow(cc, true);
		
		assertEquals("4***********1111", cc.getCardNumberToShow());

		/**
		 * cc no encrypted and masked
		 */
    	cc = new CreditCardFop();
		cc.setCardNumber("4***********1111");
		cc.setMasked(true);

		getCreditCardFopService().prepareCreditCardToShow(cc, true);
		
		assertEquals("4***********1111", cc.getCardNumberToShow());

	    
		try
		{
			/**
			 * cc number null
			 */
			cc = new CreditCardFop();
			cc.setCardNumber(null);
			
			getCreditCardFopService().prepareCreditCardToShow(cc, true);

			fail("Should have gotten CreditCardFopException. The cc number is null");
	    } catch (CreditCardFopException expected) {
	        ; // Expected - intentional
	    }
	}
	
	/**
	 * @param creditCardFopService the creditCardFopService to set
	 */
	public void setCreditCardFopService(CreditCardFopService creditCardFopService) {
		this.creditCardFopService = creditCardFopService;
	}

	/**
	 * @return the creditCardFopService
	 */
	public CreditCardFopService getCreditCardFopService() {
		return creditCardFopService;
	}

	
	
}
