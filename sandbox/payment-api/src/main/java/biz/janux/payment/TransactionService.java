package biz.janux.payment;

import java.util.List;

import org.janux.bus.search.SearchCriteria;


public interface TransactionService <T extends Transaction<TransactionResponse>>{

	public List<T> findAll(SearchCriteria searchCriteria);
	
	public T find(SearchCriteria searchCriteria);
}
