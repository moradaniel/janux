package org.janux.help;

import java.util.Date;

public interface HelpNode
{
	public Date getCreated();

	public void setCreated(Date creationDate);

	public Date getModified();

	public void setModified(Date modificationDate);

	public int getSortOrder();

	public void setSortOrder(int sortOrder);

}