package biz.janux.payment;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class CreditCardValidatorDefaultTest extends TestCase
{
    /**
     * Main function for unit tests
     * @param args command line args
     */
    public static void main(String[] args)
    {
        TestRunner.run(suite());
    }

    
    /**
     * Create the test case
     * @param testName name of the test case
     */
    public CreditCardValidatorDefaultTest(String testName)
    {
        super(testName);
    }

    
    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        final TestSuite tSuite = new TestSuite();
        
     //   tSuite.addTest(new CreditCardValidatorDefaultTest("testCardType"));
     //   tSuite.addTest(new CreditCardValidatorDefaultTest("testCardExpiration"));
     //   tSuite.addTest(new CreditCardValidatorDefaultTest("testCardDigits"));
     //   tSuite.addTest(new CreditCardValidatorDefaultTest("testCardValidationMethod"));

          // normally, this is run
        tSuite.addTestSuite(CreditCardValidatorDefaultTest.class);
     
        return (tSuite);
    }


    public void testCardType()
    {
    	// create the validator and load in the card type patterns
    	final CreditCardValidatorDefaultImpl validator = new CreditCardValidatorDefaultImpl();
    	validator.getCardTypePatterns().put("VI", "4(\\d{12}|\\d{15})");    // VISA - starts with 4 followed by 12 or 15 digits
    	validator.getCardTypePatterns().put("MC", "5[12345]\\d{14}");       // Mastercard - starts with 51 - 55 followed by 14 digits
    	validator.getCardTypePatterns().put("CA", "5[12345]\\d{14}");       // Mastercard - starts with 51 - 55 followed by 14 digits
    	validator.getCardTypePatterns().put("EU", "5[12345]\\d{14}");       // Mastercard - starts with 51 - 55 followed by 14 digits
    	validator.getCardTypePatterns().put("AX", "3[47]\\d{13}");          // Amex - starts with 34 or 37 followed by 13 digits
    	validator.getCardTypePatterns().put("DS", "6011\\d{12}");           // Discover - starts with 6011 followed by 12 digits
    	validator.getCardTypePatterns().put("DI", "3[068]\\d{12}");         // Diners Club - starts with 30 followed by 12 digits
    	validator.getCardTypePatterns().put("CB", "3[068]\\d{12}");         // Carte Blanche - same as Diners Club
    	validator.getCardTypePatterns().put("JC", "(3088|3096|3112|3158|3337|3528)\\d{12}");         // JC - has one of the prefixes followed by 12 digits

    	
    	
    	// test Visa  (starts with a 4 and has 13 or 16 digits)
    	assertFalse( validator.isValidCardType("VI", "3234567890123") );      // doesnt start with 4
    	assertFalse( validator.isValidCardType("VI", "423456789012") );       // too short with 12 digits
    	assertTrue(  validator.isValidCardType("VI", "4234567890123") );      // ok with 13 digits
    	assertFalse( validator.isValidCardType("VI", "42345678901234") );     // invalid with 14 digits
    	assertFalse( validator.isValidCardType("VI", "423456789012345") );    // invalid with 15 digits
    	assertTrue(  validator.isValidCardType("VI", "4234567890123456") );   // ok with 16 digits
    	assertFalse( validator.isValidCardType("VI", "42345678901234567") );  // too long with 17 digits
    	
    	// test Mastercard (starts with 51-55 and has 16 digits )
    	assertFalse( validator.isValidCardType("MC", "5034567890123456") );   // doesnt start with 51 - 55
    	assertTrue(  validator.isValidCardType("MC", "5134567890123456") );   // ok
    	assertTrue(  validator.isValidCardType("MC", "5234567890123456") );   // ok
    	assertTrue(  validator.isValidCardType("MC", "5334567890123456") );   // ok
    	assertTrue(  validator.isValidCardType("MC", "5434567890123456") );   // ok
    	assertTrue(  validator.isValidCardType("MC", "5534567890123456") );   // ok
    	assertFalse( validator.isValidCardType("MC", "5634567890123456") );   // doesnt start with 51 - 55
     	assertFalse( validator.isValidCardType("MC", "513456789012345") );    // too short with 15 digits
       	assertFalse( validator.isValidCardType("MC", "51345678901234567") );  // too long with 17 digits
        // same tests for mastercard code "CA"    
    	assertFalse( validator.isValidCardType("CA", "5034567890123456") );   // doesnt start with 51 - 55
    	assertTrue(  validator.isValidCardType("CA", "5134567890123456") );   // ok
    	assertTrue(  validator.isValidCardType("CA", "5234567890123456") );   // ok
    	assertTrue(  validator.isValidCardType("CA", "5334567890123456") );   // ok
    	assertTrue(  validator.isValidCardType("CA", "5434567890123456") );   // ok
    	assertTrue(  validator.isValidCardType("CA", "5534567890123456") );   // ok
    	assertFalse( validator.isValidCardType("CA", "5634567890123456") );   // doesnt start with 51 - 55
     	assertFalse( validator.isValidCardType("CA", "513456789012345") );    // too short with 15 digits
       	assertFalse( validator.isValidCardType("CA", "51345678901234567") );  // too long with 17 digits
       	// same tests for mastercard code "EU"
    	assertFalse( validator.isValidCardType("EU", "5034567890123456") );   // doesnt start with 51 - 55
    	assertTrue(  validator.isValidCardType("EU", "5134567890123456") );   // ok
    	assertTrue(  validator.isValidCardType("EU", "5234567890123456") );   // ok
    	assertTrue(  validator.isValidCardType("EU", "5334567890123456") );   // ok
    	assertTrue(  validator.isValidCardType("EU", "5434567890123456") );   // ok
    	assertTrue(  validator.isValidCardType("EU", "5534567890123456") );   // ok
    	assertFalse( validator.isValidCardType("EU", "5634567890123456") );   // doesnt start with 51 - 55
     	assertFalse( validator.isValidCardType("EU", "513456789012345") );    // too short with 15 digits
       	assertFalse( validator.isValidCardType("EU", "51345678901234567") );  // too long with 17 digits
       	
    	// test American Express (starts with 34 or 37 and has 15 digits)
    	assertFalse( validator.isValidCardType("AX", "333456789012345") );   // doesnt start with 34 or 37
    	assertTrue(  validator.isValidCardType("AX", "343456789012345") );   // ok
    	assertFalse( validator.isValidCardType("AX", "353456789012345") );   // doesnt start with 34 or 37
    	assertTrue(  validator.isValidCardType("AX", "373456789012345") );   // ok
     	assertFalse( validator.isValidCardType("AX", "383456789012345") );   // doesnt start with 34 or 37
     	assertFalse( validator.isValidCardType("AX", "34345678901234") );    // too short with 14 digits
       	assertFalse( validator.isValidCardType("AX", "3434567890123456") );  // too long with 16 digits
    	
    	// test diners club/carte blanche (starts with 30, 36, or 38 and has 14 digits)
    	assertFalse( validator.isValidCardType("DI", "33345678901234") );   // doesnt start with 30, 36, or 38
    	assertTrue(  validator.isValidCardType("DI", "30345678901234") );   // ok
    	assertFalse( validator.isValidCardType("DI", "35345678901234") );   // doesnt start with 30, 36, or 38
    	assertTrue(  validator.isValidCardType("DI", "36345678901234") );   // ok
     	assertFalse( validator.isValidCardType("DI", "37345678901234") );   // doesnt start with 30, 36, or 38
       	assertTrue(  validator.isValidCardType("DI", "38345678901234") );   // ok
     	assertFalse( validator.isValidCardType("DI", "3034567890123") );    // too short with 13 digits
       	assertFalse( validator.isValidCardType("DI", "303456789012345") );  // too long with 15 digits
    	
    	assertFalse( validator.isValidCardType("CB", "33345678901234") );   // doesnt start with 30, 36, or 38
    	assertTrue(  validator.isValidCardType("CB", "30345678901234") );   // ok
    	assertFalse( validator.isValidCardType("CB", "35345678901234") );   // doesnt start with 30, 36, or 38
    	assertTrue(  validator.isValidCardType("CB", "36345678901234") );   // ok
     	assertFalse( validator.isValidCardType("CB", "37345678901234") );   // doesnt start with 30, 36, or 38
       	assertTrue(  validator.isValidCardType("CB", "38345678901234") );   // ok
     	assertFalse( validator.isValidCardType("CB", "3034567890123") );    // too short with 13 digits
       	assertFalse( validator.isValidCardType("CB", "303456789012345") );  // too long with 15 digits
       	
    	// test discover (starts with 6011 and has 16 digits)
    	assertFalse( validator.isValidCardType("DS", "6010567890123456") );   // doesnt start with 6011 
    	assertTrue(  validator.isValidCardType("DS", "6011567890123456") );   // ok
    	assertFalse( validator.isValidCardType("DS", "6012567890123456") );   // doesnt start with 6011
     	assertFalse( validator.isValidCardType("DS", "601156789012345") );    // too short with 15 digits
       	assertFalse( validator.isValidCardType("DS", "60115678901234567") );  // too long with 17 digits
    }
    
    
    
    public void testCardExpiration()
    {
    	final GregorianCalendar calExpiration = new GregorianCalendar();
    	
    	calExpiration.add(Calendar.MONTH, 1);    // 1 month from today
    	assertTrue ( CreditCardValidatorDefaultImpl.isValidExpiration( calExpiration.getTime() ) );   
    	
    	calExpiration.add(Calendar.MONTH, -3);    // 2 months ago
    	assertFalse ( CreditCardValidatorDefaultImpl.isValidExpiration( calExpiration.getTime() ) );    
    	
    	
    	// make sure we handle end of month issues
    	final GregorianCalendar calCompare = new GregorianCalendar();
    	
    	calCompare.set(2016, 1, 14);      // Feb 14th 2016
    	calExpiration.set(2016, 1, 1);    // expires 2/2016
    	assertTrue ( CreditCardValidatorDefaultImpl.isValidExpiration( calExpiration.getTime(), calCompare.getTime() ) );    
    	
    	calCompare.set(2016, 2, 1);       // Mar 1st 2016
    	calExpiration.set(2016, 1, 1);    // expires 2/2016
    	assertFalse ( CreditCardValidatorDefaultImpl.isValidExpiration( calExpiration.getTime(), calCompare.getTime() ) );    
    }
    
    
    public void testCardDigits()
    {
    	// some sample numbers that are not active 
    	assertTrue( CreditCardValidatorDefaultImpl.isValidCheckDigit("4408 0412 3456 7893") );     // ok
    	assertTrue( CreditCardValidatorDefaultImpl.isValidCheckDigit("4417 1234 5678 9113") );     // ok
    	assertTrue( CreditCardValidatorDefaultImpl.isValidCheckDigit("4111 1111 1111 1111") );     // ok
    	assertTrue( CreditCardValidatorDefaultImpl.isValidCheckDigit("5413 5413 5413 5413") );     // ok
    	
    	// some invalid numbers
    	assertFalse( CreditCardValidatorDefaultImpl.isValidCheckDigit("4408 0412 3456 7897") );     // not ok
    	assertFalse( CreditCardValidatorDefaultImpl.isValidCheckDigit("4417 1234 5678 9111") );     // not ok
    	assertFalse( CreditCardValidatorDefaultImpl.isValidCheckDigit("4111 1111 1111 1117") );     // not ok
    	assertFalse( CreditCardValidatorDefaultImpl.isValidCheckDigit("5413 5413 5413 5419") );     // not ok
    }
    
    
    public void testCardValidationMethod()
    {
    	final CreditCardValidatorDefaultImpl validator = new CreditCardValidatorDefaultImpl();
    	validator.getCardTypePatterns().put("VI", "4(\\d{12}|\\d{15})");    // VISA - starts with 4 followed by 12 or 15 digits
    	validator.getCardTypePatterns().put("MC", "5[12345]\\d{14}");       // Mastercard - starts with 51 - 55 followed by 14 digits
    	validator.getCardTypePatterns().put("CA", "5[12345]\\d{14}");       // Mastercard - starts with 51 - 55 followed by 14 digits
    	validator.getCardTypePatterns().put("EU", "5[12345]\\d{14}");       // Mastercard - starts with 51 - 55 followed by 14 digits
    	validator.getCardTypePatterns().put("AX", "3[47]\\d{13}");          // Amex - starts with 34 or 37 followed by 13 digits
    	validator.getCardTypePatterns().put("DS", "6011\\d{12}");           // Discover - starts with 6011 followed by 12 digits
    	validator.getCardTypePatterns().put("DI", "3[068]\\d{12}");         // Diners Club - starts with 30 followed by 12 digits
    	validator.getCardTypePatterns().put("CB", "3[068]\\d{12}");         // Carte Blanche - same as Diners Club
    	validator.getCardTypePatterns().put("JC", "(3088|3096|3112|3158|3337|3528)\\d{12}");         // JC - has one of the prefixes followed by 12 digits

    	
    	final GregorianCalendar cal = new GregorianCalendar();
    	cal.add(Calendar.MONTH, 3);
    	
    	// create a test card
    	final CreditCard cc = new CreditCardImpl();
    	cc.setNumber("4408 0412 3456 7893");
    	cc.setTypeCode("VI");
    	cc.setExpirationDate(cal.getTime());
    	
    	
    	// test a good card
    	assertTrue( validator.isValid(cc) );
    	Map<String, String> errors = validator.validate(cc);
    	assertNotNull(errors);
    	assertEquals(0, errors.size());
    	
    	
    	// test a card with a bunch of errors
    	cal.add(Calendar.MONTH, -6);
    	
    	cc.setNumber("4408 0412 3456 7896");
    	cc.setTypeCode("AX");
    	cc.setExpirationDate(cal.getTime());
    	
    	assertFalse( validator.isValid(cc) );
    	errors = validator.validate(cc);
    	assertNotNull(errors);
    	assertEquals(3, errors.size());
    }
    
}
