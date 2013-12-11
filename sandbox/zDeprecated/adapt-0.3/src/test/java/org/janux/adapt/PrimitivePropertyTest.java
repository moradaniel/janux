package org.janux.adapt;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class PrimitivePropertyTest extends TestCase
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
    public PrimitivePropertyTest(String testName)
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
    //	tSuite.addTest( new PrimitivePropertyTest("testIntProperty") );
    //	tSuite.addTest( new PrimitivePropertyTest("testStringProperty") );
    //	tSuite.addTest( new PrimitivePropertyTest("testBooleanProperty") );
    //	tSuite.addTest( new PrimitivePropertyTest("testBigDecimalProperty") );
    	
    	// normally, this is run
    	tSuite.addTestSuite(PrimitivePropertyTest.class);
        
        return tSuite;
    }
    
	
	public void testIntProperty() throws PropertyValidationException
	{
		// create the property
		final PrimitiveProperty property = new PrimitivePropertyImpl("Property1", PropertyMetadata.DataType.NUMERIC);
		final IntegerValidator validator = new IntegerValidator(11, 19);
	//	validator.setMinValue(new Integer(11));
	//	validator.setMaxValue(new Integer(19));
		property.getMetadata().setValidator(validator);
		
		
    	// wrong object type
    	try
    	{
    	    property.setValidValue("TEST");
    	    fail("Failed to throw an illegal argument exception when passed a non-integer object");
    	}
    	catch (PropertyValidationException e)
    	{
    		// this is the expected response
    	}
    	
    	
    	
    	// min value
    	try
    	{
    		property.setValidValue(new Integer(10));
    	    fail("Failed to throw an illegal argument exception when passed a integer object with invalid minimum value");
    	}
    	catch (PropertyValidationException e)
    	{
    		// this is the expected response
    	}
    	
    	
    	// max value
    	try
    	{
    		property.setValidValue(new Integer(20));
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
    		pickList.add(new Integer(11));
    		pickList.add(new Integer(13));
    		pickList.add(new Integer(15));
    		pickList.add(new Integer(17));
    		pickList.add(new Integer(19));
    		validator.setPicklist(pickList);
    		validator.setPicklistOnly(true);
    	    property.setValidValue(new Integer(14));
    	    fail("Failed to throw an illegal argument exception when passed a integer object not in the picklist");
    	}
    	catch (PropertyValidationException e)
    	{
    		// this is the expected response
    	}
		
		
		
		
		property.setValue(new Integer(17));
		
		// check the values
		assertEquals(new Integer(17), property.getIntegerValue());
		assertEquals("Property1", property.getMetadata().getName());
		assertEquals(PropertyMetadata.DataType.NUMERIC, property.getMetadata().getDataType());
	}

	
	public void testStringProperty() throws PropertyValidationException
	{
		// create the property
		final PrimitiveProperty property = new PrimitivePropertyImpl("Property2", PropertyMetadata.DataType.STRING);
		final StringValidator validator = new StringValidator(4, 10);
		property.getMetadata().setValidator(validator);
		

    	// wrong object type
    	try
    	{
    	    property.setValidValue(new Integer(17));
    	    fail("Failed to throw an illegal argument exception when passed a non-string object");
    	}
    	catch (PropertyValidationException e)
    	{
    		// this is the expected response
    	}
    	
    	
    	
    	// min value
    	try
    	{
    	    property.setValidValue(new String("ABC"));
    	    fail("Failed to throw an illegal argument exception when passed a string object with invalid minimum length value");
    	}
    	catch (PropertyValidationException e)
    	{
    		// this is the expected response
    	}
    	
    	
    	// max value
    	try
    	{
    	    property.setValidValue(new String("THIS STRING IS TOO LONG"));
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
    	    property.setValidValue(new String("PETE"));
    	    fail("Failed to throw an illegal argument exception when passed a string object not in the picklist");
    	}
    	catch (PropertyValidationException e)
    	{
    		// this is the expected response
    	}
		
		
		property.setValue(new String("JOHN"));
		
		// check the values
		assertEquals("Property2", property.getMetadata().getName());
		assertEquals(PropertyMetadata.DataType.STRING, property.getMetadata().getDataType());
		assertEquals("JOHN", property.getStringValue());
	}
	
	
	public void testBooleanProperty() throws PropertyValidationException
	{
		// create the property
		final PrimitiveProperty property = new PrimitivePropertyImpl("Property3", PropertyMetadata.DataType.BOOLEAN);
		property.setValue(new Boolean(false));
		
		// check the values
		assertEquals(new Boolean(false), property.getBooleanValue());
		assertEquals("Property3", property.getMetadata().getName());
		assertEquals(PropertyMetadata.DataType.BOOLEAN, property.getMetadata().getDataType());
	}
	
	
	public void testBigDecimalProperty() throws PropertyValidationException
	{
		// create the property
		final PrimitiveProperty property = new PrimitivePropertyImpl("Property4", PropertyMetadata.DataType.NUMERIC);
		property.setValue(new BigDecimal("61.37"));
		
		// check the values
		assertEquals(new BigDecimal("61.37"), property.getBigDecimalValue());
		assertEquals("Property4", property.getMetadata().getName());
		assertEquals(PropertyMetadata.DataType.NUMERIC, property.getMetadata().getDataType());
	}
	
}
