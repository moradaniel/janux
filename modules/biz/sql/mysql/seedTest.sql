-- source ../test/mysql/extensions.sql;

LOAD DATA LOCAL INFILE '../test/resources/people/Party.txt'              INTO TABLE people_party;
LOAD DATA LOCAL INFILE '../test/resources/people/Person.txt'             INTO TABLE people_person;
LOAD DATA LOCAL INFILE '../test/resources/people/Organization.txt'       INTO TABLE people_organization;
LOAD DATA LOCAL INFILE '../test/resources/people/ContactMethod.txt'      INTO TABLE people_contact_method;
LOAD DATA LOCAL INFILE '../test/resources/people/PhoneNumber.txt'        INTO TABLE people_phone_number (id, countryCode, areaCode, number);
LOAD DATA LOCAL INFILE '../test/resources/people/PartyContactMethod.txt' INTO TABLE people_party_contact_method;

LOAD DATA LOCAL INFILE '../test/resources/geography/City.txt'            INTO TABLE geography_city;
LOAD DATA LOCAL INFILE '../test/resources/geography/PostalAddress.txt'   INTO TABLE geography_postal_address;
