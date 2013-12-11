package biz.janux.payment;

import java.util.List;
import org.janux.bus.search.SearchCriteria;
import com.trg.search.Search;

public class CreditCardTypeServiceImpl implements CreditCardTypeService<SearchCriteria>  {

	private CreditCardTypeDao<CreditCardType, Integer, SearchCriteria> creditCardTypeDao;
	
	public CreditCardType findFirstByCriteria(SearchCriteria searchCriteria) {
		((Search) searchCriteria).setMaxResults(1);

		List<CreditCardType> list = getCreditCardTypeDao().findByCriteria(searchCriteria);

		if (!list.isEmpty()) {
			CreditCardType creditCardType = list.get(0);
			return creditCardType;
		}

		return null;
	}

	public CreditCardType findByCode(String code) {
		Search searchCriteria = new Search();
		searchCriteria.addFilterEqual("code", code);
		return findFirstByCriteria(searchCriteria);
	}

	public List<CreditCardType> findAll() {
		Search searchCriteria = new Search();
		return getCreditCardTypeDao().findByCriteria(searchCriteria);
	}

	public CreditCardTypeDao<CreditCardType, Integer, SearchCriteria> getCreditCardTypeDao() {
		return creditCardTypeDao;
	}

	public void setCreditCardTypeDao(CreditCardTypeDao<CreditCardType, Integer, SearchCriteria> creditCardTypeDao) {
		this.creditCardTypeDao = creditCardTypeDao;
	}

}
