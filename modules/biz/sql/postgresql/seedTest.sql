-- \i security/seedParty.sql;



\copy people_party (id,code) from '../test/resources/people/Party.txt' null as ''
\copy people_person (id,namePrefix,firstName,middleName,lastName,nameSuffix) from '../test/resources/people/Person.txt' null as ''
\copy people_organization (id,shortName,longName,legalName,numEmployees,isForProfit,descr,memo1,memo2) from '../test/resources/people/Organization.txt' null as ''
\copy people_contact_method (id,class,address) from '../test/resources/people/ContactMethod.txt' null as ''
\copy people_phone_number (id,countryCode,areaCode,number,extension) from '../test/resources/people/PhoneNumber.txt' null as ''
\copy people_party_contact_method (partyId,contactMethodId,kind) from '../test/resources/people/PartyContactMethod.txt' null as ''

\copy geography_city (id,stateProvinceId,code,name) from '../test/resources/geography/City.txt' null as ''
\copy geography_postal_address (id,cityId,countryId,line1,line2,line3,postalCode,city,state,country) from '../test/resources/geography/PostalAddress.txt' null as E'\\N'
 
-- when you insert rows in postgresql from the txt files, 
-- the hibernate_sequence is not updated and the first id value for object id generation remains as 1
-- and when you run the tests, hibernate uses this sequence for generating the id, 
-- and it gets the 1 value which was already inserted by the sample data. This obviously causes a unique constraint violation
-- so as a workaround, after loading the sample data we have to set the first value for the hibernate_sequence to a high value

ALTER SEQUENCE hibernate_sequence RESTART WITH 500;