package org.janux.help;

public interface HelpEntry extends HelpNode
{
	String getCode();
	void setCode(String code);

	String getLabel();
	void setLabel(String label);

	String getText();
	void setText(String text);

	HelpCategory getCategory();
	void setCategory(HelpCategory category);
}
