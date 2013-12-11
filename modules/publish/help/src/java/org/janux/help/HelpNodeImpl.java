package org.janux.help;

import java.util.Date;

import org.janux.bus.persistence.PersistentAbstract;

public class HelpNodeImpl extends PersistentAbstract implements HelpNode
{
	Date creationDate;
	Date modificationDate;
	int sortOrder;
	
	public HelpNodeImpl() {}
	
	public Date getCreated() {
		return creationDate;
	}
	public void setCreated(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getModified() {
		return modificationDate;
	}
	public void setModified(Date modificationDate) {
		this.modificationDate = modificationDate;
	}
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
}
