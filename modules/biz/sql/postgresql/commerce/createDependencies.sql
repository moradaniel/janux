alter table commerce_credit_card add constraint fk_CreditCard__CardType foreign key (cardTypeId) references commerce_payment_method;
alter table commerce_credit_card add constraint FK8705532EB6F3BAB foreign key (formOfPaymentId) references commerce_form_of_payment;
alter table commerce_form_of_payment add constraint fk_FormOfPayment__Party foreign key (partyId) references people_party;
