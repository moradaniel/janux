package org.janux.help;

public class HelpServiceImpl implements HelpService
{
	private HelpEntryDao helpEntryDao;
	
	public HelpServiceImpl(HelpEntryDao helpEntryDao)
	{
		this.helpEntryDao = helpEntryDao;
	}
}
