package org.janux.bus.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.StandardToStringStyle;

import org.janux.bus.persistence.PersistentAbstract;
import org.janux.util.JanuxToStringStyle;

/**
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @version $Revision: 1.8 $ - $Date: 2007-01-11 23:13:10 $
 */
public class RoleImpl extends PersistentAbstract implements Role, java.io.Serializable
{
	private static final long serialVersionUID = 2012032701;
	// private Integer id;
	private String  name;
	private String  description;
	private Integer sortOrder;
	private boolean enabled;

	private List<PermissionGranted> permissionsGrantedList;
	private Map<PermissionGrantedKey, Long> permissionsGranted;
	private List<Role> roles;
	private PermissionsManager permsManager;

	/** plain vanilla constructor */
	public RoleImpl() {}


	/*
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	*/

	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	/** Human readable description of this Role */
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}


	public List<Role> getRoles() {
		if (this.roles == null) { this.roles = new ArrayList<Role>(); }
		return this.roles;
	}
	
	public void setRoles(List<Role> aggrRoles) {
		this.roles = aggrRoles;
	}


	/*
	protected List<PermissionGranted> getPermissionsGrantedList() {
		return this.permissionsGrantedList;
	}
	
	protected void setPermissionsGrantedList(List<PermissionGranted> permissionsGrantedList) {
		this.permissionsGrantedList = permissionsGrantedList;
	}
	*/

	protected Map<PermissionGrantedKey, Long> getPermissionsGranted() {

		if (this.permissionsGranted == null)
			this.permissionsGranted = new HashMap<PermissionGrantedKey, Long>();

		return this.permissionsGranted;
	}
	
	protected void setPermissionsGranted(Map<PermissionGrantedKey, Long> permissionsGranted) {
		this.permissionsGranted = permissionsGranted;
	}


	private PermissionsManager getPermissionsManager() 
	{
		if (this.permsManager == null)
			this.permsManager = new PermissionsManager(this.getName(), this.getRoles(), this.getPermissionsGranted());

		return this.permsManager;
	}


	/*
	public Set<String> getPermissionContextStrings() {
		return this.getPermissionsManager().getPermissionContextStrings();
	}
	*/

	public Map<String, PermissionContext> getPermissionContexts() {
		return this.getPermissionsManager().getPermissionContexts();
	}

	public boolean hasPermissions(String permissionContext, String[] permissionNames) {
		return this.getPermissionsManager().hasPermissions(permissionContext, permissionNames);
	}

	public boolean hasPermissions(String permissionContext, String permissionName) {
		return this.getPermissionsManager().hasPermissions(permissionContext, permissionName);
	}   

	public String[] getPermissions(String permissionContext) {
		return this.getPermissionsManager().getPermissions(permissionContext);
	}

	public void grantPermissions(String permissionContext, String[] permissionNames) {
		throw new UnsupportedOperationException("grantPermissions not implemented yet");
	}

	public boolean hasPermissions(String permissionContext, long requiredPerms) { 
		return this.getPermissionsManager().hasPermissions(permissionContext, requiredPerms);
	}

	public long getPermissionsValue(String permissionContext) {
		return this.getPermissionsManager().getPermissionsValue(permissionContext);
	}

	public void grantPermissions(PermissionContext permissionContext, long permissionsGranted) {
		this.getPermissionsManager().grantPermissions(permissionContext, permissionsGranted);
	}

	public void denyPermissions(PermissionContext permissionContext, long permissionsGranted) {
		this.getPermissionsManager().denyPermissions(permissionContext, permissionsGranted);
	}

	public Long getPermissionsGranted(String permissionSetName) {
		throw new UnsupportedOperationException("getPermissionsGranted(permissionSetName) has not yet been implemented");
	}
	
	public void setPermissionsGranted(String permissionSetName, Long permissions) {
		throw new UnsupportedOperationException(
				"setPermissionsGranted(permissionSetName, permissions) has not yet been implemented");
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
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public String toString() 
	{ 
		ToStringBuilder sb = new ToStringBuilder(this, JanuxToStringStyle.COMPACT);

		sb.append("id", getId())
			.append("name", getName())
			.append("enabled", isEnabled());

		if (getSortOrder() != null) sb.append("sortOrder", getSortOrder());

		sb.append("permsGranted", getPermissionsGranted());

		if (this.getRoles().size() > 0) sb.append("roles", getRoles());
		
		return sb.toString();
	}


	public boolean equals(Object other)
	{
		if ( (this == other ) ) return true;
		if ( !(other instanceof RoleImpl) ) return false;
		Role castOther = (Role)other; 

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


} // end class RoleImpl
