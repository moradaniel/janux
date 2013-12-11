package biz.janux.payment;

import java.io.Serializable;

import org.janux.bus.persistence.GenericDaoReadOnlyWithFacets;
import org.janux.bus.persistence.GenericDaoWrite;
import org.janux.bus.search.SearchCriteria;

public interface SettlementDao<T extends Settlement,ID extends Serializable,S extends SearchCriteria,F extends Enum<F>> 
extends GenericDaoWrite<T, ID>, 
GenericDaoReadOnlyWithFacets<T, ID, S, F>
{
	/**
	 * Return the last batch number used checking the approved settlement responses
	 */
	public int getLastBatchNumber();
}
