-- \i geography/seed.sql

\i commerce/seed.sql;
\i people/seed.sql;

\copy geography_country (id,code,name,phoneCode,sortOrder,visible) from '../resources/geography/Country.txt' null as ''
\copy geography_state_province (id,countryId,code,name,sortOrder,visible) from '../resources/geography/StateProvince.txt' null as ''

-- when you insert rows in postgresql from the txt files, 
-- the hibernate_sequence is not updated and the first id value for object id generation remains as 1
-- and when you run the tests, hibernate uses this sequence for generating the id, 
-- and it gets the 1 value which was already inserted by the sample data. This obviously causes a unique constraint violation
-- so as a workaround, after loading the sample data we have to set the first value for the hibernate_sequence to a high value
ALTER SEQUENCE hibernate_sequence RESTART WITH 500;