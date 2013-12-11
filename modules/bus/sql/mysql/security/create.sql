-- represents the Account of a user
create table sec_account (
	id integer unsigned not null auto_increment
	,name varchar(255) not null unique default ''
	,password varchar(255)
	,enabled tinyint(1) default 1 not null
	,unlocked tinyint(1) default 1 not null
	,expire datetime
	,expirePassword datetime
	,primary key (id)
) default charset=utf8;

-- Roles may be assigned to an Account to represent the default Roles of the Account,
-- or may be assigned to the relationship of an entity and an account (specific Role for a specific entity)
create table sec_role (
	id integer unsigned not null auto_increment
	,name varchar(255) not null unique default ''
	,description text
	,sortOrder integer
	,enabled tinyint(1) default 1 not null
	,primary key (id)
	,index idx_roleSortOrder (sortOrder)
) default charset=utf8;

-- stores Roles assigned to an Account
create table sec_account_role (
	accountId integer unsigned not null
	,roleId integer unsigned not null
	,sortOrder integer not null
	,primary key (accountId, roleId)
	,index idx_accountRoleSortOrder (sortOrder)
	,unique (accountId, sortOrder)
);

-- a Role may aggregate many other Roles,
-- and a Role may belong to zero or many role aggregations
create table sec_role_aggr_role (
	roleId integer unsigned not null
	,aggrRoleId integer unsigned not null
	,sortOrder integer not null
	,primary key (roleId, aggrRoleId)
	,index idx_aggrRoleSortOrder (sortOrder)
);

-- a Permission Context (Bitmask) represents a set of permissions in a specific context,
-- for example, Create/Read/Update/Hide/Purge in the context of a Party;
-- a Permission Context has a one-to-many relationship with PermissionBits, which it owns
create table sec_permission_context (
	id integer unsigned not null auto_increment
	,name varchar(255) not null unique default ''
	,description text
	,sortOrder integer
	,enabled tinyint(1) default 1 not null
	,primary key (id)
	,index idx_permissionContextSortOrder (sortOrder)
) default charset=utf8;

-- a PermissionBit represents an individual bit in a bit mask
-- PermissionBits do not have meaning outside of the Permission Context that contains them
create table sec_permission_bit (
	 contextId integer unsigned not null
	,position integer not null
	,name varchar(255)
	,description text
	,sortOrder integer
	,primary key (contextId, position)
	,unique (contextId, name)
	,index idx_permissionBitName (name)
	,index idx_permissionBitSortOrder (sortOrder)
) default charset=utf8;


-- a Role is assigned permissions via the 'value' column, 
-- a numeric representation of the bits that are turned 'on' in the bit mask
create table sec_permission_granted (
	roleId integer unsigned not null
	,contextId integer unsigned not null
	,permissions bigint default 0 not null
	,deny tinyint(1) default 0 not null
	,primary key (roleId, contextId, deny)
);

create table sec_account_setting (
	accountId integer unsigned not null
	,setting_tag   varchar(128) not null
	,setting_value text
	,primary key (accountId, setting_tag)
);
