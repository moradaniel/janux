package org.janux.adapt;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class PropertyValidatorTest extends TestCase
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
    public PropertyValidatorTest(String testName)
    {
        super(testName);
    }

    
    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
    	final TestSuite tSuite = new TestSuite();
    	
    	// do this to debug a particular problem
    //	tSuite.addTest( new PropertyValidatorTest("testIntegerValidator") );
    //	tSuite.addTest( new PropertyValidatorTest("testStringValidator") );
    	
    	// normally, this is run
    	tSuite.addTestSuite(PropertyValidatorTest.class);
        
        return tSuite;
    }

    /**
     * Test the integer validator
     * @throws PropertyValidationException 
     */
    public void testIntegerValidator() throws PropertyValidationException
    {
    	final IntegerValidator validator = new IntegerValidator(0, 100);
    	
    	// wrong object type
    	try
    	{
    	    validator.validate(new String("TEST"));
    	    fail("Failed to throw an illegal argument exception when passed a non-integer object");
    	}
    	catch (PropertyValidationException e)
    	{
    		// this is the expected response
    	}
    	
    	
    	
    	// min value
    	try
    	{
    		validator.setMinValue(new Integer(15));
    	    validator.validate(new Integer(14));
    	    fail("Failed to throw an illegal argument exception when passed a integer object with invalid minimum value");
    	}
    	catch (PropertyValidationException e)
    	{
    		// this is the expected response
    	}
    	
    	
    	// max value
    	try
    	{
    		validator.setMaxValue(new Integer(35));
    	    validator.validate(new Integer(36));
    	    fail("Failed to throw an illegal argument exception when passed a integer object with invalid maximum value");
    	}
    	catch (PropertyValidationException e)
    	{
    		// this is the expected response
    	}
    	
    	
    	// not in the picklist
    	try
    	{
    		final Set<Integer> pickList = new HashSet<Integer>();
    		pickList.add(new Integer(18));
    		pickList.add(new Integer(21));
    		pickList.add(new Integer(28));
    		pickList.add(new Integer(30));
    		pickList.add(new Integer(33));
    		validator.setPicklist(pickList);
    		validator.setPicklistOnly(true);
    	    validator.validate(new Integer(25));
    	    fail("Failed to throw an illegal argument exception when passed a integer object not in the picklist");
    	}
    	catch (PropertyValidationException e)
    	{
    		// this is the expected response
    	}
    	
    	
    	// these values should be ok
    	validator.validate(new Integer(18));
    	validator.validate(new Integer(21));
    	validator.validate(new Integer(28));
    	validator.validate(new Integer(30));
    	validator.validate(new Integer(33));
    	
    	validator.setPicklistOnly(false);
	    validator.validate(new Integer(25));
    }

    
    
    /**
     * Test the String validator
     * @throws PropertyValidationException 
     */
    public void testStringValidator() throws PropertyValidationException
    {
    	final StringValidator validator = new StringValidator(1, 100);
    	
    	// wrong object type
    	try
    	{
    	    validator.validate(new Integer(17));
    	    fail("Failed to throw an illegal argument exception when passed a non-string object");
    	}
    	catch (PropertyValidationException e)
    	{
    		// this is the expected response
    	}
    	
    	
    	
    	// min value
    	try
    	{
    		validator.setMinLength(new Integer(4));
    	    validator.validate(new String("ABC"));
    	    fail("Failed to throw an illegal argument exception when passed a string object with invalid minimum length value");
    	}
    	catch (PropertyValidationException e)
    	{
    		// this is the expected response
    	}
    	
    	
    	// max value
    	try
    	{
    		validator.setMaxLength(new Integer(10));
    	    validator.validate(new String("THIS STRING IS TOO LONG"));
    	    fail("Failed to throw an illegal argument exception when passed a string object with invalid maximum length value");
    	}
    	catch (PropertyValidationException e)
    	{
    		// this is the expected response
    	}
    	
    	
    	// not in the picklist
    	try
    	{
    		final Set<String> pickList = new HashSet<String>();
    		pickList.add(new String("JOHN"));
    		pickList.add(new String("PAUL"));
    		pickList.add(new String("GEORGE"));
    		pickList.add(new String("RINGO"));
    		validator.setPicklist(pickList);
    		validator.setPicklistOnly(true);
    	    validator.validate(new String("PETE"));
    	    fail("Failed to throw an illegal argument exception when passed a string object not in the picklist");
    	}
    	catch (PropertyValidationException e)
    	{
    		// this is the expected response
    	}
    	
    	
    	// these values should be ok
    	validator.validate(new String("JOHN"));
    	validator.validate(new String("PAUL"));
    	validator.validate(new String("GEORGE"));
    	validator.validate(new String("RINGO"));
    	
    	validator.setPicklistOnly(false);
	    validator.validate(new String("PETE"));
    }
}
