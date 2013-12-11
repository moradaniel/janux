-- represents the Account of a user
create table sec_account (
	id int4 not null, 
	name varchar(255) not null unique, 
	password varchar(255), 
	enabled bool default true not null, 
	unlocked bool default true not null, 
	expire timestamp, 
	expirePassword timestamp, 
	primary key (id));

-- Roles may be assigned to an Account to represent the default Roles of the Account,
-- or may be assigned to the relationship of an entity and an account (specific Role for a specific entity)

create table sec_role (
	id int4 not null, 
	name varchar(255) not null unique, 
	description text, 
	sortOrder int4, 
	enabled bool default true not null, 
	primary key (id));

-- stores Roles assigned to an Account
create table sec_account_role (
	accountId int4 not null, 
	roleId int4 not null, 
	sortOrder int4 not null, 
	primary key (accountId, sortOrder),
	unique (accountId, sortOrder));
CREATE INDEX idx_accountRoleSortOrder ON sec_account_role ( sortOrder);

-- a Role may aggregate many other Roles,
-- and a Role may belong to zero or many role aggregations

create table sec_role_aggr_role (
	roleId int4 not null, 
	aggrRoleId int4 not null, 
	sortOrder int4 not null, 
	primary key (roleId, sortOrder));
CREATE INDEX idx_aggrRoleSortOrder ON sec_role_aggr_role ( sortOrder);	



-- a Permission Context (Bitmask) represents a set of permissions in a specific context,
-- for example, Create/Read/Update/Hide/Purge in the context of a Party;
-- a Permission Context has a one-to-many relationship with PermissionBits, which it owns
create table sec_permission_context (
	id int4 not null, 
	name varchar(255) not null unique, 
	description text, 
	sortOrder int4, 
	enabled bool default true not null, 
	primary key (id));
CREATE INDEX idx_permissionContextSortOrder ON sec_permission_context ( sortOrder);

-- a PermissionBit represents an individual bit in a bit mask
-- PermissionBits do not have meaning outside of the Permission Context that contains them
create table sec_permission_bit (
	contextId int4 not null,
	position int4 not null,
	name varchar(255), 
	description text, 
	sortOrder int4, 
	unique (contextId, name),
	primary key (contextId, position));
	
CREATE INDEX idx_permissionBitName ON sec_permission_bit ( name);
CREATE INDEX idx_permissionBitSortOrder ON sec_permission_bit ( sortOrder);
	
-- a Role is assigned permissions via the 'value' column, 
-- a numeric representation of the bits that are turned 'on' in the bit mask

create table sec_permission_granted (
	roleId int4 not null,
	contextId int4 not null,
	permissions int8 default 0 not null, 
	deny bool default false not null, 
	primary key (roleId, contextId, deny));

create table sec_account_setting (
	accountId int4 not null, 
	setting_value varchar(255), 
	setting_tag varchar(255),
	primary key (accountId, setting_tag));


