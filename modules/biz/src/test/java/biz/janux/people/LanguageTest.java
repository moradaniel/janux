package biz.janux.people;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class LanguageTest extends TestCase
{
	public LanguageTest() {
		super();
	}

	public LanguageTest(String name) {
		super(name);
	}

	/** define the tests to be run in this class */
	public static Test suite() throws Exception
	{
		final TestSuite suite = new TestSuite();

		// run all tests
		suite.addTestSuite(LanguageTest.class);

		// or a subset thereoff
	//	suite.addTest(new LagnuageTest("testClone"));

		return suite;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
        TestRunner.run(suite());
	}

	
	public void testClone()
	{
		final Language source = new LanguageImpl();
		source.setCode("TEST_LANG");
        source.setDescription("This is a test language");
        
        final Language checkLanguage = (Language )source.clone();
        assertNotNull(checkLanguage);
        assertNotSame(source, checkLanguage);
        assertEquals(source, checkLanguage);
        assertEquals(source.getCode(), checkLanguage.getCode());
        assertEquals(source.getDescription(), checkLanguage.getDescription());
	}
	
	public void testPersonNameClone()
	{
		final PersonName source = new PersonNameImpl();
		source.setFirst("Michael");
		source.setMiddle("Frank");
		source.setLast("Smith");
		source.setHonorificPrefix("Mr.");
		source.setHonorificSuffix("M.D.");
		
        final PersonName checkName = (PersonName )source.clone();
        assertNotNull(checkName);
        assertNotSame(source, checkName);
        assertEquals(source.getFirst(), checkName.getFirst());
        assertEquals(source.getMiddle(), checkName.getMiddle());
        assertEquals(source.getLast(), checkName.getLast());
        assertEquals(source.getHonorificPrefix(), checkName.getHonorificPrefix());
        assertEquals(source.getHonorificSuffix(), checkName.getHonorificSuffix());
	}
	
	
	public void testOrganizationNameClone()
	{
		final OrganizationName source = new OrganizationNameImpl();
		source.setShort("Janux");
		source.setLong("The Janux Corporation");
		source.setLegal("The Official Janux Corporation, Inc.");
		
        final OrganizationName checkName = (OrganizationName )source.clone();
        assertNotNull(checkName);
        assertNotSame(source, checkName);
        assertEquals(source.getShort(), checkName.getShort());
        assertEquals(source.getLong(), checkName.getLong());
        assertEquals(source.getLegal(), checkName.getLegal());
	}
	
}
