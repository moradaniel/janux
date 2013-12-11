package biz.janux.commerce.payment.implementation.common;

import java.util.ArrayList;

import biz.janux.commerce.payment.interfaces.service.TransactiontHistoryService;
import biz.janux.commerce.payment.interfaces.transaction.Settlement;
import biz.janux.commerce.payment.interfaces.transaction.Transaction;
/**
 * @author Nilesh
 * TODO need to implements methods in future 
 * for getting records of  Settlements and Transactions
 * 
 **/
public class HistoryServiceImpl implements TransactiontHistoryService {

	public ArrayList<Settlement> getSettlements(long merchantId,
			long instrumentId) {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<Transaction> getTransactions(long merchantId,
			long instrumentId) {
		// TODO Auto-generated method stub
		return null;
	}
}
