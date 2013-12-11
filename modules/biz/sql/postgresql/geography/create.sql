
create table geography_country(
	id int4 not null, 
	code varchar(255) not null unique, 
	name varchar(255) not null unique,
	phoneCode int4,
	sortOrder int4, 
	visible bool default true not null, 
	primary key (id));

	
create table geography_state_province (
	id int4 not null,
	countryId int4 not null,
	code varchar(255) not null, 
	name varchar(255), 
	sortOrder int4, 
	visible bool default true not null, 
 	unique (countryId,code),
	primary key (id));

-- each City must be assigned to a StateProvince;  in those cases where the
-- StateProvince is not known but the Country is known, a default 'Unknown'
-- StateProvince exists for each Country, that is used to relate City to Country
create table geography_city (
	id int4 not null,
	stateProvinceId int4 not null,
	code varchar(255), 
	name varchar(255) not null, 
	primary key (id));


create table geography_postal_address (
	id int4 not null,
	cityId int4, 
	countryId int4, 
	line1 varchar(255), 
	line2 varchar(255), 
	line3 varchar(255), 
	postalCode varchar(255), 
	city varchar(255), 
	state varchar(255), 
	country varchar(255), 
	primary key (id));
