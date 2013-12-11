alter table people_organization add constraint fk_Organization__Party foreign key (id) references people_party;
alter table people_party_contact_method add constraint fk_PartyContactMethod__Party foreign key (partyId) references people_party;
alter table people_party_contact_method add constraint fk_PartyContactMethod__ContactMethod foreign key (contactMethodId) references people_contact_method;
alter table people_person add constraint fk_Person__Party foreign key (id) references people_party;
alter table people_phone_number add constraint fk_PhoneNumber__ContactMethod foreign key (id) references people_contact_method;
