package org.janux.help;


public class HelpEntryImpl extends HelpNodeImpl implements HelpEntry, java.io.Serializable
{
	private static final long serialVersionUID = 5110867069644860623L;
	private String code;
	private String label;
	private String text;
	private HelpCategory category;

	public HelpEntryImpl() {}
	
	public HelpEntryImpl(String code,String label,HelpCategory category)
	{
		super();
		
		this.code = code;
		this.label = label;
		this.category = category;
	}
	
	public String getLabel()
	{
		return label;
	}

	public void setLabel(String name)
	{
		this.label = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public HelpCategory getCategory()
	{
		return category;
	}

	public void setCategory(HelpCategory category)
	{
		this.category = category;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
