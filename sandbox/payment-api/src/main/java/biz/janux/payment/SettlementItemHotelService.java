package biz.janux.payment;

import java.util.List;

import org.janux.bus.search.SearchCriteria;


public interface SettlementItemHotelService <T extends SettlementItemHotel>{

	public List<T> findAll(SearchCriteria searchCriteria);
	
	public T find(SearchCriteria searchCriteria);
	
	public T load(T settlementItem);

	public T saveOrUpdate(T settlementItem);

	public T findByUuid(String uuid);

	public T delete(String uuid);
	
}
