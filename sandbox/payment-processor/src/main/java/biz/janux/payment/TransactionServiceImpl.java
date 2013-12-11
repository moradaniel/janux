package biz.janux.payment;

import java.io.Serializable;

import org.janux.bus.search.SearchCriteria;

import api.org.janux.bus.service.GenericServiceImpl;

public class TransactionServiceImpl <T extends TransactionImpl<TransactionResponse>,ID extends Serializable,DW extends TransactionDao<T, ID, SearchCriteria,AuthorizationFacet>, DR extends TransactionDao<T, ID, SearchCriteria,TransactionFacet>>
extends GenericServiceImpl<T,ID,SearchCriteria,DW,DR> 
implements TransactionService<T>  {
}
