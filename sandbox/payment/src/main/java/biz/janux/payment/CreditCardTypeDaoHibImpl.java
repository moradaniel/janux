package biz.janux.payment;

import java.io.Serializable;

import org.janux.bus.persistence.GenericDaoHibImpl;
import org.janux.bus.persistence.GenericDaoWithFacetsHibImpl;
import org.janux.bus.search.SearchCriteria;

public class CreditCardTypeDaoHibImpl<T extends CreditCardTypeImpl, ID extends Serializable, S extends SearchCriteria,F extends Enum<F>> extends GenericDaoHibImpl<T, ID, S> 
implements CreditCardTypeDao<T,ID,S> {

}
