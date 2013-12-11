package biz.janux.payment;

import java.io.Serializable;

import org.janux.bus.persistence.GenericDaoReadOnly;
import org.janux.bus.persistence.GenericDaoWrite;
import org.janux.bus.search.SearchCriteria;

public interface BatchNumberDao<T extends BatchNumber,ID extends Serializable,S extends SearchCriteria> 
extends GenericDaoWrite<T, ID>, 
GenericDaoReadOnly<T, ID, S>
{
}
