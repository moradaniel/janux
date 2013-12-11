-- Party is the parent type of Organization and Person
-- the 'id' field is unique accross Organization and Person
create table people_party (
	id integer unsigned not null auto_increment
	,code varchar(255)
	,primary key (id)
	,index idx_code (code)
) default charset=utf8;

-- the 'id' field is shared with the people_party table
create table people_person (
	id integer unsigned not null
	,namePrefix varchar(255)
	,firstName varchar(255)
	,middleName varchar(255)
	,lastName varchar(255)
	,nameSuffix varchar(255)
	,primary key (id)
) default charset=utf8;

-- the 'id' field is shared with the people_party table
create table people_organization (
	id integer unsigned not null
	,shortName varchar(255)
	,longName varchar(255)
	,legalName varchar(255)
	-- sample extensions - this implementation will eventually be deprecated
	,numEmployees integer unsigned
	,isForProfit bit
	,descr text
	,memo1 text
	,memo2 text
	--
	,primary key (id)
) default charset=utf8;


-- externalizes the one-to-many relationship between Party and ContactMethod so
-- that we do not have to embed the PartyId key in the ContactMethod table, and
-- we can relate other entities to ContactMethods (such as Orders)
create table people_party_contact_method (
   partyId integer unsigned not null
  ,contactMethodId integer unsigned not null
  ,kind varchar(255) not null default '' -- a user-defined string such as WORK, MAILING, EMAIL1, MAIN_SITE etc...
  ,primary key (partyId, contactMethodId, kind)
) default charset=utf8;


create table people_contact_method (
	id integer unsigned not null auto_increment
	,class varchar(255) not null default '' -- one of: 'email', 'url', 'phone', 'postal'
	,address text -- stores email or url address
	,primary key (id)
	,index idx_class (class)
) default charset=utf8;


-- the 'id' field is shared with the people_ContactMethod table
create table people_phone_number (
	id integer unsigned not null
	,countryCode integer
	,areaCode integer
	,number varchar(255)
	,extension varchar(255)
	,primary key (id)
) default charset=utf8;


-- a language table
create table people_language (
	id integer unsigned not null auto_increment,
	code varchar(10) not null default '',
	descr varchar(255),
	primary key (id),
	unique language_key (code)
) default charset=utf8;
