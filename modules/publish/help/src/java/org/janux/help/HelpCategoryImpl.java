package org.janux.help;

import java.util.HashSet;
import java.util.Set;


public class HelpCategoryImpl extends HelpNodeImpl implements HelpCategory, java.io.Serializable
{
	private static final long serialVersionUID = -5693578080066840951L;
	private String title;
	private String description;
	private Set<HelpEntry> helpEntries = new HashSet<HelpEntry>();
	
	public HelpCategoryImpl() {}
	
	public HelpCategoryImpl(String title)
	{
		this.title = title;
	}
	
	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public Set<HelpEntry> getHelpEntries()
	{
		return helpEntries;
	}

	public void setHelpEntries(Set<HelpEntry> entries)
	{
		this.helpEntries = entries;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
}
