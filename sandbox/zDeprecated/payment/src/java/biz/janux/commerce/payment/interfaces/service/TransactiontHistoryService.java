package biz.janux.commerce.payment.interfaces.service;

import java.util.ArrayList;

import biz.janux.commerce.payment.interfaces.transaction.*;
/**
 * 
 * @author Nilesh
 * </br>
 * Interfac for Transactiont History Service
 *
 */
public interface TransactiontHistoryService {
	/**
	 * 
	 * @param merchantId
	 * @param instrumentId
	 * @return ArrayList of Settlement
	 */
	public ArrayList<Settlement> getSettlements(long merchantId , long instrumentId);
	/**
	 * 
	 * @param merchantId
	 * @param instrumentId
	 * @return ArrayList of Transaction
	 */
	public ArrayList<Transaction> getTransactions(long merchantId , long instrumentId);
	
}
