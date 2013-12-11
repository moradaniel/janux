-- Party is the parent type of Organization and Person
-- the 'id' field is unique accross Organization and Person

create table people_party (
	id int4 not null, 
	code varchar(255), 
	primary key (id));
CREATE INDEX idx_code ON people_party ( code);	

-- the 'id' field is shared with the people_party table
create table people_person (
	id int4 not null, 
	namePrefix varchar(255), 
	firstName varchar(255), 
	middleName varchar(255), 
	lastName varchar(255), 
	nameSuffix varchar(255), 
	primary key (id));

-- the 'id' field is shared with the people_party table
create table people_organization (
	id int4 not null, 
	shortName varchar(255), 
	longName varchar(255), 
	legalName varchar(255), 
	numEmployees int4, 
	isForProfit bool, 
	descr text, 
	memo1 text, 
	memo2 text,
	primary key (id));


-- externalizes the one-to-many relationship between Party and ContactMethod so
-- that we do not have to embed the PartyId key in the ContactMethod table, and
-- we can relate other entities to ContactMethods (such as Orders)

create table people_party_contact_method (
	partyId int4 not null, 
	contactMethodId int4 not null, 
	kind varchar(255) not null, 
	primary key (partyId, contactMethodId, kind));


create table people_contact_method (
	id int4 not null, 
	class varchar(255) not null, -- one of: 'email', 'url', 'phone', 'postal'
	address varchar(255), -- stores email or url address 
	primary key (id));
CREATE INDEX idx_class ON people_contact_method ( class);

create table people_phone_number (
	id int4 not null, 
	countryCode int4, 
	areaCode int4, 
	number varchar(255), 
	extension varchar(255), 
	primary key (id));

-- a language table

create table people_language (
	id int4 not null, 
	code varchar(255) not null unique, 
	descr varchar(255), 
	primary key (id));