package biz.janux.payment;

import java.util.List;

import org.janux.bus.search.SearchCriteria;

import biz.janux.payment.CreditCardType;

public interface CreditCardTypeService<S extends SearchCriteria> {

	public CreditCardType findFirstByCriteria(SearchCriteria searchCriteria);
	public CreditCardType findByCode(String code);
	public List<CreditCardType> findAll();

}
