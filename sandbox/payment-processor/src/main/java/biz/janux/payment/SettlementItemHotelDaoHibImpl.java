package biz.janux.payment;

import java.io.Serializable;

import org.hibernate.Hibernate;
import org.janux.bus.persistence.GenericDaoWithFacetsHibImpl;
import org.janux.bus.search.SearchCriteria;

public class SettlementItemHotelDaoHibImpl<T extends SettlementItemHotelImpl, ID extends Serializable, S extends SearchCriteria,F extends Enum<F>> extends GenericDaoWithFacetsHibImpl<T, ID, S,F> 
implements SettlementItemHotelDao<T,ID,S,F> {

	public void initialize(T persistentObject, F facet) {
		if (facet.equals(SettlementItemFacet.AUTHORIZATION))
			Hibernate.initialize(persistentObject.getAuthorization());
		if (facet.equals(SettlementItemFacet.CREDIT_CARD))
			Hibernate.initialize(persistentObject.getCreditCard());
		if (facet.equals(SettlementItemFacet.BUSINESS_UNIT))
			Hibernate.initialize(persistentObject.getBusinessUnit());
	}

}
