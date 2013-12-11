alter table geography_state_province
	add constraint fk_StateProvince__Country foreign key (countryId) references geography_country (id);

alter table geography_city
	add constraint fk_City__StateProvince foreign key (stateProvinceId) references geography_state_province (id);

alter table geography_postal_address
	add constraint fk_PostalAddress__City    foreign key (cityId)    references geography_city (id),
	add constraint fk_PostalAddress__Country foreign key (countryId) references geography_country (id),
	add constraint fk_PostalAddress__ContactMethod foreign key (id) references people_contact_method (id);

