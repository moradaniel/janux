package biz.janux.payment;

import java.io.Serializable;

import org.hibernate.Hibernate;
import org.janux.bus.persistence.GenericDaoWithFacetsHibImpl;
import org.janux.bus.search.SearchCriteria;

public class AuthorizationHotelDaoHibImpl<T extends AuthorizationHotelImpl, ID extends Serializable, S extends SearchCriteria,F extends Enum<F>> extends GenericDaoWithFacetsHibImpl<T, ID, S,F> 
implements AuthorizationHotelDao<T,ID,S,F> {

	public void initialize(T persistentObject, F facet) {
		if (facet.equals(AuthorizationFacet.TRANSACTION_RESPONSE))
			Hibernate.initialize(persistentObject.getTransactionResponse());
		if (facet.equals(AuthorizationFacet.CREDIT_CARD))
			Hibernate.initialize(persistentObject.getCreditCard());
		if (facet.equals(AuthorizationFacet.MODIFICATIONS))
			Hibernate.initialize(persistentObject.getModifications());
		if (facet.equals(AuthorizationFacet.BUSINESS_UNIT))
			Hibernate.initialize(persistentObject.getBusinessUnit());
	}

}
