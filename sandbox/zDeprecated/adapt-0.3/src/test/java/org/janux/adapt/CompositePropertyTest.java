package org.janux.adapt;

import java.math.BigDecimal;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class CompositePropertyTest extends TestCase
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
    public CompositePropertyTest(String testName)
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
    //	tSuite.addTest( new CompositePropertyTest("testProperties") );
    //   	tSuite.addTest( new CompositePropertyTest("testMultiLevel") );
    	
    	// normally, this is run
    	tSuite.addTestSuite(CompositePropertyTest.class);
        
        return tSuite;
    }

    
	
	public void testProperties() throws PropertyValidationException
	{
		final CompositeProperty props = new CompositePropertyImpl("TEST");
		
		final IntegerValidator intValidator = new IntegerValidator(11, 19);
		// intValidator.setMinValue(new Integer(11));
		// intValidator.setMaxValue(new Integer(19));
		
		final StringValidator stringValidator = new StringValidator(2, 20);
		
		props.setMetadata("Property1", "Property1", PropertyMetadata.DataType.NUMERIC, intValidator);
		props.setMetadata("Property2", "Property2", PropertyMetadata.DataType.STRING, stringValidator);
		props.setMetadata("Property3", "Property3", PropertyMetadata.DataType.BOOLEAN);
		props.setMetadata("Property4", "Property4", PropertyMetadata.DataType.NUMERIC);
		
		props.setValue("Property1", new Integer(17) );
		props.setValue("Property2", new String("Test String 2") );
		props.setValue("Property3", new Boolean(false) );
		props.setValue("Property4", new BigDecimal("61.37") );
		
		
		
		assertEquals(4, props.getProperties().size());
		final PropertyMetadata metadata2 = props.getProperty("Property2").getMetadata();
		assertEquals("Property2", metadata2.getName());
		assertEquals(PropertyMetadata.DataType.STRING, metadata2.getDataType());
		
		final String testString = props.getPrimitiveProperty("Property2").getStringValue();
		assertEquals("Test String 2", testString);
		
		assertEquals(new Integer(17), props.getPrimitiveProperty("Property1").getIntegerValue());
		assertEquals(new String("Test String 2"), props.getPrimitiveProperty("Property2").getStringValue());
		assertEquals(new Boolean(false), props.getPrimitiveProperty("Property3").getBooleanValue());
		assertEquals(new BigDecimal("61.37"), props.getPrimitiveProperty("Property4").getBigDecimalValue());
		
		// copy to a second PropertyMap instance
		/*
		final CompositeProperty props2 = new CompositePropertyImpl("TEST2");
//		props2.setPropertyMetadata(props.getPropertyMetadata());
		props2.setPropertyValues(props.getPropertyValues());
		
		assertEquals(new Integer(17), props2.getPrimitiveChildProperty("Property1").getIntPropertyValue());
		assertEquals(new String("Test String 2"), props2.getPrimitiveChildProperty("Property2").getStringPropertyValue());
		assertEquals(new Boolean(false), props2.getPrimitiveChildProperty("Property3").getBooleanPropertyValue());
		assertEquals(new BigDecimal("61.37"), props2.getPrimitiveChildProperty("Property4").getBigDecimalPropertyValue());
		*/
		
		// print the composite
		props.accept(new PropertyPrinter()); 
		
		props.clearProperties();
		assertEquals(0, props.getProperties().size());
	}
	
	
	public void testMultiLevel() throws PropertyValidationException
	{
		final CompositeProperty prop1 = new CompositePropertyImpl("Level1");
		final CompositeProperty prop2 = prop1.createCompositeProperty("Level2");
		final CompositeProperty prop3 = prop2.createCompositeProperty("Level3");
		final CompositeProperty prop4 = prop3.createCompositeProperty("Level4");
		
		/*
		prop1.setMetadata(prop2.getMetadata().getName(), prop2.getMetadata().getDataType());
		prop1.setValue(prop2.getMetadata().getName(), prop2);
		
		prop2.setChildPropertyMetadata(prop3.getMetadata());
		prop2.setValue(prop3.getMetadata().getName(), prop3);
		
		prop3.setChildPropertyMetadata(prop4.getMetadata());
		prop3.setValue(prop4.getMetadata().getName(), prop4);
		*/
		
		prop4.setMetadata("Property1", "Property1", PropertyMetadata.DataType.NUMERIC, new IntegerValidator(1, 20));
		prop4.setMetadata("Property2", "Property2", PropertyMetadata.DataType.STRING, new StringValidator(1, 50));
		prop4.setMetadata("Property3", PropertyMetadata.DataType.BOOLEAN);
		prop4.setMetadata("Property4", PropertyMetadata.DataType.NUMERIC);
		
		prop4.setValue("Property1", new Integer(17) );
		prop4.setValue("Property2", new String("Test String 2") );
		prop4.setValue("Property3", new Boolean(false) );
		prop4.setValue("Property4", new BigDecimal("61.37") );
		
		{
		final PrimitiveProperty retProp1 = prop1.getCompositeProperty("Level2")
                                    .getCompositeProperty("Level3")
                                    .getCompositeProperty("Level4")
                                    .getPrimitiveProperty("Property1");
		
		final PrimitiveProperty retProp2 = prop1.getCompositeProperty("Level2")
        .getCompositeProperty("Level3")
        .getCompositeProperty("Level4")
        .getPrimitiveProperty("Property2");
		
		final PrimitiveProperty retProp3 = prop1.getCompositeProperty("Level2")
        .getCompositeProperty("Level3")
        .getCompositeProperty("Level4")
        .getPrimitiveProperty("Property3");
		
		final PrimitiveProperty retProp4 = prop1.getCompositeProperty("Level2")
        .getCompositeProperty("Level3")
        .getCompositeProperty("Level4")
        .getPrimitiveProperty("Property4");
		
		
		assertEquals(new Integer(17), retProp1.getIntegerValue());
		assertEquals(new String("Test String 2"), retProp2.getStringValue());
		assertEquals(new Boolean(false), retProp3.getBooleanValue());
		assertEquals(new BigDecimal("61.37"), retProp4.getBigDecimalValue());
		}
		
		prop1.validate();
		
		// check the recursive lookup
		{
		final PrimitiveProperty retProp1 = prop1.getPrimitiveProperty("Property1", 5);
		final PrimitiveProperty retProp2 = prop1.getPrimitiveProperty("Property2", 5);
		final PrimitiveProperty retProp3 = prop1.getPrimitiveProperty("Property3", 5);
		final PrimitiveProperty retProp4 = prop1.getPrimitiveProperty("Property4", 5);
		
		
		assertEquals(new Integer(17), retProp1.getIntegerValue());
		assertEquals(new String("Test String 2"), retProp2.getStringValue());
		assertEquals(new Boolean(false), retProp3.getBooleanValue());
		assertEquals(new BigDecimal("61.37"), retProp4.getBigDecimalValue());
		}
		
		// check the recursive lookup
		{
		final PrimitiveProperty retProp1 = prop1.getPrimitiveProperty("Property1", 2);
		final PrimitiveProperty retProp2 = prop1.getPrimitiveProperty("Property2", 2);
		final PrimitiveProperty retProp3 = prop1.getPrimitiveProperty("Property3", 2);
		final PrimitiveProperty retProp4 = prop1.getPrimitiveProperty("Property4", 2);
		
		
		assertNull("Property 1", retProp1);
		assertNull("Property 2", retProp2);
		assertNull("Property 3", retProp3);
		assertNull("Property 4", retProp4);
		}
	}
	
}
