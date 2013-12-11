alter table commerce_form_of_payment
  add constraint fk_FormOfPayment__Party foreign key (partyId) references people_party (id);

alter table commerce_credit_card
  add constraint fk_CreditCard__FormOfPayment foreign key (formOfPaymentId) references commerce_form_of_payment (id),
  add constraint fk_CreditCard__CardType foreign key (cardTypeId) references commerce_payment_method (id);
  