package org.janux.help;

import java.util.List;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;



public class HelpEntryDaoTest extends TransactionalHelpTestAbstract
{
	protected HelpEntryDao helpEntryDao;
	protected HelpCategoryDao helpCategoryDao;

	private static final int HELP_CAT_ID1 = 1;
	private static final int HELP_ID1 = 1;
	private static final String HELP_LABEL1 = "Hello Help!";
	private static final String newCatLabel = "New Cat Guy";
	private static final String newLabel = "New Guy";
	private static final String newText = "I've got some text!";
	private static final String newLabel2 = "New Guy2";
	private static final String newCode1 = "TESTGUY1";
	private static final String newCode2 = "TESTGUY2";

	public HelpEntryDaoTest()
	{
	}

	public HelpEntryDaoTest(String name)
	{
		super(name);
	}


	public void testLoad() 
	{
		HelpCategory category = helpCategoryDao.load(new Integer(HELP_CAT_ID1));
		HelpEntry entry1 = helpEntryDao.load(new Integer(HELP_ID1));
		HelpCategory category1 = entry1.getCategory();

		assertNotNull("Help Category", category1);
		assertNotNull("Help Entry", entry1);
		assertEquals("label", HELP_LABEL1, entry1.getLabel());
	}

	public void testSave()
	{
		HelpCategoryImpl newCategory = new HelpCategoryImpl(newCatLabel);
		helpCategoryDao.save(newCategory);
		HelpEntryImpl newEntry = new HelpEntryImpl(newCode1,newLabel,newCategory);
		newEntry.setText(newText);
		helpEntryDao.save(newEntry);

		HelpEntry retrievedEntry = helpEntryDao.load(newEntry.getId());

		assertNotNull("New Help Entry", retrievedEntry);
		assertEquals("label", newLabel, retrievedEntry.getLabel());

		HelpEntryImpl newEntry2 = new HelpEntryImpl(newCode2,newLabel2,newCategory);
		helpEntryDao.save(newEntry2);

		HelpEntry retrievedEntry2 = helpEntryDao.loadByCode(newCode2);

		assertNotNull("New Help Entry2", retrievedEntry2);
		assertEquals("label2", newLabel2, retrievedEntry2.getLabel());
	}

	public void testLoadAll()
	{
		List<HelpEntry> allEntries = helpEntryDao.loadAll();

		for (HelpEntry entry : allEntries)
		{
			// for now, all no test data has help text, except the one we added!
			if (newLabel.equals(entry.getLabel()))
			{
				assertTrue("Help Entry with text we added", entry.getText().equals(newText));
			}
			else if (newLabel2.equals(entry.getLabel()))
			{
				assertNull("Help Entry we added with no text", entry.getText());
			}
			else
			{
				assertTrue("All other Help Entry Texts", entry.getText().startsWith("Some"));
			}
		}
	}

	public void testDelete() 
	{
		HelpEntry newEntry = helpEntryDao.findByLabel(newLabel);
		assertNotNull("Help Entry", newEntry);

		int newEntryId = ((HelpEntryImpl) newEntry).getId();
		boolean found = false;
		helpEntryDao.delete(newEntry);

		try
		{
			helpEntryDao.load(newEntryId);
			found = true;

		}
		catch(Exception ex)
		{
		}

		assertFalse("Help Entry Deleted", found);
	}

	public void testDeleteCategory() 
	{
		HelpCategory newCategory = helpCategoryDao.findByTitle(newCatLabel);
		assertNotNull("Help Category", newCategory);

		int newCategoryId = ((HelpCategoryImpl) newCategory).getId();

		HelpEntry newEntry2 = helpEntryDao.findByLabel(newLabel2);
		int newEntryCategoryId = ((HelpCategoryImpl) newEntry2.getCategory()).getId();
		assertTrue("Category IDs", (newCategoryId == newEntryCategoryId));

		boolean found = false;
		helpCategoryDao.delete(newCategory);

		try
		{
			helpCategoryDao.load(newCategoryId);
			found = true;

		}
		catch(Exception ex)
		{
		}

		assertFalse("Help Category Deleted", found);

		newEntry2 = helpEntryDao.findByLabel(newLabel2);
		assertNotNull("Help Entry2", newEntry2);
		assertNull("Help Entry2 Category", newEntry2.getCategory());

	}

	/**
	 * Main function for unit tests
	 * @param args command line args
	 */
	public static void main(String[] args)
	{
		TestRunner.run(suite());
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite()
	{
		final TestSuite tSuite = new TestSuite();

		tSuite.addTestSuite(HelpEntryDaoTest.class);

		return tSuite;
	}

}
