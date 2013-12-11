package biz.janux.payment;

import biz.janux.payment.Transaction;
import biz.janux.payment.TransactionResponse;

public class CreditCardProccesingException extends RuntimeException {
	Transaction<TransactionResponse> transaction;

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public CreditCardProccesingException(Transaction transaction) {
		super(transaction.getTransactionResponse().getErrorDescription());
		this.transaction = transaction;
	}
	
}
