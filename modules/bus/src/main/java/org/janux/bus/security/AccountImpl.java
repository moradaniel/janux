package org.janux.bus.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import org.janux.bus.persistence.PersistentAbstract;
import org.janux.util.JanuxToStringStyle;

public class AccountImpl extends PersistentAbstract  implements Account, java.io.Serializable
{
	private static final long serialVersionUID = 2012032001;
	private String    name;
	private String    password;
	private Date      expire;
	private Date      expirePassword;
	private boolean   nonLocked;
	private boolean   enabled = true;

	private List<Role> roles;
	private Set<AccountSetting> settings;
	private List<PermissionGranted> permissionsGrantedList;
	private PermissionsManager permsManager;


	/** plain vanilla constructor */
	public AccountImpl() {}

	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}


	public String getUsername() {
		return this.getName();
	}
	
	public void setUsername(String name) {
		this.setName(name);
	}


	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}


	public Date getExpiration() {
		return this.expire;
	}

	public void setExpiration(Date date) {
		this.expire = date;
	}


	public Date getPasswordExpiration() {
		return expirePassword;
	}

	public void setPasswordExpiration(Date date) {
		this.expirePassword = date;
	}


	public boolean isEnabled() {
		return this.enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public boolean isAccountNonLocked() {
		return this.nonLocked;
	}

	public void setAccountNonLocked(boolean b) {
		this.nonLocked = b;
	}


	public boolean isAccountNonExpired() 
	{
		if (this.getExpiration() != null)
			return this.getExpiration().after(new Date());
		else
			return true;
	}


	public boolean isCredentialsNonExpired()
	{
		if (this.getPasswordExpiration() != null)
			return this.getPasswordExpiration().after(new Date());
		else
			return true;
	}


	public List<Role> getRoles() {
		if (this.roles == null) { this.roles = new ArrayList<Role>(); }
		return this.roles;
	}
	
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}



	private PermissionsManager getPermissionsManager() 
	{
		if (this.permsManager == null)
			this.permsManager = new PermissionsManager(this.getName(), this.getRoles(), null);

		return this.permsManager;
	}



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
	

	public String toString() 
	{
		ToStringBuilder sb = new ToStringBuilder(this, JanuxToStringStyle.COMPACT);

		sb.append("id", getId())
			.append("name", getName())
			.append("enabled", isEnabled())
			.append("nonLocked", isAccountNonLocked())
			.append("expire", getExpiration())
			.append("expirePass", getPasswordExpiration());

		if (this.getRoles().size() > 0) sb.append("roles", getRoles());

		return sb.toString();
	}


	/** two accounts are equal if they have the same name */
	public boolean equals(Object other)
	{
		if ( (this == other ) ) return true;
		if ( !(other instanceof AccountImpl) ) return false;
		Account castOther = (Account)other; 

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


	public int compareTo(Object other) {
		if ( (this == other ) ) return 0;
		if ( !(other instanceof AccountImpl) ) return 1;
		Account castOther = (Account)other; 

		return new CompareToBuilder()
			.append(this.getName(), castOther.getName())
			.toComparison();
	}


	public Set<AccountSetting> getSettings() {
		if (settings == null)
		{
			settings = new HashSet<AccountSetting>();
		}
		
		return settings;
	}


	public void setSettings(Set<AccountSetting> settings) {
		this.settings = settings;
	}


} // end class AccountImpl
