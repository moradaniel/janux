package org.janux.help;

public class HelpServiceImplGeneric implements HelpService
{
	private HelpEntryDaoGeneric helpEntryDaoGeneric;
	
	public HelpServiceImplGeneric(HelpEntryDaoGeneric helpEntryDao)
	{
		this.helpEntryDaoGeneric = helpEntryDao;
	}
}
