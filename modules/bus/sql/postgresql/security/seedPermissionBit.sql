INSERT INTO sec_permission_bit VALUES (1,0,'CREATE','Create Account',0);
INSERT INTO sec_permission_bit VALUES (1,1,'READ','Read Account',1);
INSERT INTO sec_permission_bit VALUES (1,2,'UPDATE','Update Account: modify account name and password',2);
INSERT INTO sec_permission_bit VALUES (1,3,'DISABLE','Disable an Account: hide it from view, and make it inactive without purging it',3);
INSERT INTO sec_permission_bit VALUES (1,4,'PURGE','Purge an Account from the system, along with any associated records',4);
INSERT INTO sec_permission_bit VALUES (1,5,'ASSIGN_ROLE','Assign Roles to this Account',5);
INSERT INTO sec_permission_bit VALUES (2,0,'CREATE','Create Role',0);
INSERT INTO sec_permission_bit VALUES (2,1,'READ','Read Role',1);
INSERT INTO sec_permission_bit VALUES (2,2,'UPDATE','Update Role: change name and description, and associated Permissions',2);
INSERT INTO sec_permission_bit VALUES (2,3,'DISABLE','Disable a Role: hide it from view',3);
INSERT INTO sec_permission_bit VALUES (2,4,'PURGE','Purge a Role from the system, and any associated records',4);
INSERT INTO sec_permission_bit VALUES (3,0,'DECLARE','Declare holidays',1);
INSERT INTO sec_permission_bit VALUES (3,1,'APPROVE','Approve holidays for others',0);
INSERT INTO sec_permission_bit VALUES (3,2,'TAKE','Take holidays',2);
INSERT INTO sec_permission_bit VALUES (4,0,'CREATE','Create work',0);
INSERT INTO sec_permission_bit VALUES (4,1,'DO','Perform work',1);
INSERT INTO sec_permission_bit VALUES (4,2,'ASSIGN','Assign work',2);
INSERT INTO sec_permission_bit VALUES (4,3,'SKIP','Skip work',3);

