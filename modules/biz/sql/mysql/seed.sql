-- source geography/seed.sql;
source commerce/seed.sql;
source people/seed.sql;
LOAD DATA LOCAL INFILE '../resources/geography/Country.txt'       INTO TABLE geography_country;
LOAD DATA LOCAL INFILE '../resources/geography/StateProvince.txt' INTO TABLE geography_state_province;
