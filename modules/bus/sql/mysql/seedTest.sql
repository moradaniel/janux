LOAD DATA LOCAL INFILE '../test/resources/security/Account.txt'           INTO TABLE sec_account;
LOAD DATA LOCAL INFILE '../test/resources/security/Role.txt'              INTO TABLE sec_role;
LOAD DATA LOCAL INFILE '../test/resources/security/PermissionContext.txt' INTO TABLE sec_permission_context;
LOAD DATA LOCAL INFILE '../test/resources/security/PermissionBit.txt'     INTO TABLE sec_permission_bit;

LOAD DATA LOCAL INFILE '../test/resources/security/AccountRole.txt'       INTO TABLE sec_account_role;
LOAD DATA LOCAL INFILE '../test/resources/security/RoleAggrRole.txt'      INTO TABLE sec_role_aggr_role;
LOAD DATA LOCAL INFILE '../test/resources/security/PermissionGranted.txt' INTO TABLE sec_permission_granted;
