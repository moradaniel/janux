alter table sec_account_role 
	add constraint fk_AccountRole__Account foreign key (accountId) references sec_account;
alter table sec_account_role add constraint fk_AccountRole__Role foreign key (roleId) references sec_role;
alter table sec_account_setting add constraint fk_AccountSetting__Account foreign key (accountId) references sec_account;
alter table sec_permission_bit add constraint fk_PermissionBit__PermissionContext foreign key (contextId) references sec_permission_context;
alter table sec_permission_granted add constraint fk_permission_granted__permission_context foreign key (contextId) references sec_permission_context;
alter table sec_permission_granted add constraint fk_role__permission_granted foreign key (roleId) references sec_role;
alter table sec_role_aggr_role add constraint fk_RoleAggrRole__Role foreign key (roleId) references sec_role;
alter table sec_role_aggr_role add constraint fk_RoleAggrRole__AggrRole foreign key (aggrRoleId) references sec_role;
