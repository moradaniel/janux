alter table commerce_credit_card
    drop foreign key fk_CreditCard__FormOfPayment;

alter table commerce_form_of_payment
    drop foreign key fk_FormOfPayment__Party;
