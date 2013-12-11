package org.janux.help;

import java.util.Set;

public interface HelpCategory extends HelpNode 
{
	String getTitle();
	void setTitle(String title);

	String getDescription();
	void setDescription(String description);

	Set<HelpEntry> getHelpEntries();
	void setHelpEntries(Set<HelpEntry> entries);
}
