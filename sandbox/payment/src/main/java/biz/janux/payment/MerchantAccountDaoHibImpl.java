package biz.janux.payment;

import java.io.Serializable;

import org.janux.bus.persistence.GenericDaoHibImpl;
import org.janux.bus.search.SearchCriteria;

public class MerchantAccountDaoHibImpl<T extends MerchantAccountImpl, ID extends Serializable, S extends SearchCriteria> extends GenericDaoHibImpl<T, ID, S> 
implements MerchantAccountDao<T,ID,S> {

}

