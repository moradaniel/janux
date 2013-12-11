package biz.janux.payment;

import java.io.Serializable;

import org.hibernate.Hibernate;
import org.janux.bus.persistence.GenericDaoWithFacetsHibImpl;
import org.janux.bus.search.SearchCriteria;

public class SettlementDaoHibImpl<T extends SettlementImpl, ID extends Serializable, S extends SearchCriteria,F extends Enum<F>> extends GenericDaoWithFacetsHibImpl<T, ID, S,F> 
implements SettlementDao<T,ID,S,F> {

	public void initialize(T persistentObject, F facet) {
		if (facet.equals(SettlementFacet.BUSINESS_UNIT))
			Hibernate.initialize(persistentObject.getBusinessUnit());
		if (facet.equals(SettlementFacet.SETTLEMENT_ITEMS))
			Hibernate.initialize(persistentObject.getSettlementItems());
		if (facet.equals(SettlementFacet.TRANSACTION_RESPONSE))
			Hibernate.initialize(persistentObject.getTransactionResponse());
	}

	public int getLastBatchNumber() {
		
		return 0;
	}

}
