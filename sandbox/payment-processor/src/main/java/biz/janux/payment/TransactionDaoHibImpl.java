package biz.janux.payment;

import java.io.Serializable;

import org.janux.bus.persistence.GenericDaoWithFacetsHibImpl;
import org.janux.bus.search.SearchCriteria;

public class TransactionDaoHibImpl<T extends TransactionImpl, ID extends Serializable, S extends SearchCriteria,F extends Enum<F>> extends GenericDaoWithFacetsHibImpl<T, ID, S,F> 
implements TransactionDao<T,ID,S,F> {

	@Override
	public void initialize(T entity, F facet) {
	}

}
