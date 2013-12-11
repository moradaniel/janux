create table geography_country (
	id integer unsigned not null auto_increment
	,code varchar(255) not null unique
	,name varchar(255) not null
	,phoneCode smallint default null
	,sortOrder smallint default 999
	,visible tinyint(1) not null default 1
	,primary key (id)
) default charset=utf8;

create table geography_state_province (
	id integer unsigned not null auto_increment
	,countryId integer unsigned not null
	,code varchar(255) not null
	,name varchar(255) not null
	,primary key (id)
	,sortOrder smallint default 9999
	,visible tinyint(1) not null default 1
	,constraint key_Country__StateProvince unique (countryId,code)
) default charset=utf8;

-- each City must be assigned to a StateProvince;  in those cases where the
-- StateProvince is not known but the Country is known, a default 'Unknown'
-- StateProvince exists for each Country, that is used to relate City to Country
create table geography_city (
	id integer unsigned not null auto_increment
	,stateProvinceId integer unsigned not null
	,code varchar(255) default null
	,name varchar(255) not null
	,primary key (id)
) default charset=utf8;

-- a postal address should contain either 
-- (i) references to a City and Country, or
-- (ii) a reference to a Country and text city/state fields, or 
-- (iii) text city/state/country fields
create table geography_postal_address (
	id integer unsigned not null auto_increment
  ,cityId integer unsigned
  ,countryId integer unsigned
	,line1 varchar(255)
	,line2 varchar(255)
	,line3 varchar(255)
	,postalCode varchar(255)
	,city varchar(255)
	,state varchar(255) 
	,country varchar(255)
	,primary key (id)
) default charset=utf8;
