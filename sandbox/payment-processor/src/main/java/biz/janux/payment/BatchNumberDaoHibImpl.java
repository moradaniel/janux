package biz.janux.payment;

import java.io.Serializable;

import org.janux.bus.persistence.GenericDaoHibImpl;
import org.janux.bus.search.SearchCriteria;

public class BatchNumberDaoHibImpl<T extends BatchNumberImpl, ID extends Serializable, S extends SearchCriteria> extends GenericDaoHibImpl<T, ID, S> 
implements BatchNumberDao<T,ID,S> {
}
