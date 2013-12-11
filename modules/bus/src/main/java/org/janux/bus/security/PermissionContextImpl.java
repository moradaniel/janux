package org.janux.bus.security;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class PermissionContextImpl implements PermissionContext, java.io.Serializable
{
	protected transient Log log = LogFactory.getLog(this.getClass());
	
	private static final long serialVersionUID = 1;

	private Integer id;
	private String name;
	private String description;
	private Integer sortOrder;
	private boolean enabled;
	private List<PermissionBit> bits;
	private Map<String, PermissionBit> bitMap;


	/** plain vanilla constructor */
	public PermissionContextImpl() {}

	public PermissionContextImpl(String name) {
		this.name = name;
	}
 
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}


	public List<PermissionBit> getPermissionBits() 
	{
		if (this.bits == null)
			this.bits = new ArrayList<PermissionBit>();

		return this.bits;
	}
	
	protected void setPermissionBits(List<PermissionBit> permissionBits) {
		this.bits = permissionBits;
	}


	public PermissionBit getPermissionBit(String name) {
		return this.getBitMap().get(name);
	}


	public void addPermissionBit(PermissionBit permBit) 
	{
		if (this.getPermissionBit(permBit.getName()) != null) 
		{
			String msg = "A permission bit with name: '" + permBit.getName() + "' already exists in PermissionContext: " + this;
			log.error(msg);
			throw new IllegalArgumentException(msg);
		}

		if (this.getMaxBitPosition() > 63) 
		{
			String msg = "This implementation of a PermissionContext does not accept more than 64 PermissionBits; you are trying to add 65 PermissionBits to PermissionContext: " + this;
			log.error(msg);
			throw new UnsupportedOperationException(msg);
		}

		permBit.setPosition( (short)(this.getMaxBitPosition() + 1) );
		permBit.setPermissionContext(this);
		this.getPermissionBits().add(permBit);

		// force refresh of bitMap on next invocation of getBitMap
		this.bitMap = null;
	}


	public long getValue(String[] permBitNames) 
	{
		long permsValue = 0;

		for (String permName : permBitNames)
		{
			if ( this.getPermissionBit(permName) != null ) {
				permsValue += this.getPermissionBit(permName).getValue();
			} else {
				String msg = "The permission '" + permName + "' is not defined in the Permission Context " + this;
				log.error(msg);
				throw new IllegalArgumentException(msg);
			}
		}

		return permsValue;
	}

	public long getMaxValue() {
		return (long)Math.pow(2.0, this.getPermissionBits().size()) - 1;
	}


	/* Human readable description of this PermissionBit Set */
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}


	public boolean isEnabled() {
		return this.enabled;
	}
	
	public void setEnabled(boolean visible) {
		this.enabled = visible;
	}


	/**	   
	 * The set of permissions that this PermissionBit Set defines; note that this represents meta
	 * information of what sort of Permissions are available to be assigned within the context of a
	 * Busines Context and a Role, but that a PermissionBit Set does not confer any of these Permissions
	 * per-se to any entity.
	 */
	protected Map<String, PermissionBit> getBitMap() 
	{
		if (this.bitMap == null) {
			this.bitMap = new HashMap<String, PermissionBit>();
			for (PermissionBit bit : this.getPermissionBits())
				this.bitMap.put(bit.getName(), bit);
		}
		return this.bitMap;
	}

	/** 
	 * returns the highest sequential bit position of all the bits in the permissionBit List, 
	 * or -1 if this PermissionContext has no PermissionBits assigned to it; the
	 * value returned by this method should be equal to * (getPermissionBits().size() - 1)
	 * but we expressly iterate through the permission bits and assert that fact
	 */
	private short getMaxBitPosition()
	{
		int maxBitPos = -1;
		for (PermissionBit permBit : this.getPermissionBits())
			maxBitPos = Math.max(permBit.getPosition(), maxBitPos);

		if ( maxBitPos != (this.getPermissionBits().size() - 1) )
		{
			String msg = "The highest bit position is not equal to (permissionBits.size - 1) in PermissionContext: " + this; log.error(msg);
			throw new IllegalStateException(msg);
		}
		
		return (short)maxBitPos;
	}


	public String toString() 
	{
		return new ToStringBuilder(this)
			.append("id", getId())
			.append("name", getName())
			.append("enabled", isEnabled())
			.append("sortOrder", getSortOrder())
			.append("bits", getPermissionBits())
			.toString();
	}


	/** 
	 * Implements business identity equality: two PermissionContexts are equal if they have the same name 
	 */
	public boolean equals(Object other)
	{
		if ( (this == other ) ) return true;
		if ( !(other instanceof PermissionContextImpl) ) return false;
		PermissionContext castOther = (PermissionContext)other; 

		return new EqualsBuilder()
			.append(this.getName(), castOther.getName())
			.isEquals();
	}


	public int hashCode() 
	{
		return new HashCodeBuilder()
		.append(this.getName())
		.toHashCode();
	}   

} // end class PermissionContextImpl
