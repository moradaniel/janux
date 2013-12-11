package biz.janux.payment;

import java.io.Serializable;
import java.util.List;

import org.janux.bus.persistence.Persistent;
import org.janux.bus.search.SearchCriteria;

import com.trg.search.Search;

import api.org.janux.bus.service.GenericServiceImpl;

public class SettlementServiceImpl <T extends SettlementImpl,ID extends Serializable,DW extends SettlementDao<T, ID, SearchCriteria,SettlementFacet>, DR extends SettlementDao<T, ID, SearchCriteria,SettlementFacet>>
extends GenericServiceImpl<T,ID,SearchCriteria,DW,DR> 
implements SettlementService<T>  {

	AuthorizationHotelService<AuthorizationHotel> authorizationHotelService;
	BatchNumberDao<BatchNumber, Serializable, SearchCriteria> batchNumberDao;
	
	public T create(T settlement) {
		log.debug("Creating settlement "+settlement);
		
		if (settlement.getId()!=Persistent.UNSAVED_ID)
			throw new IllegalArgumentException ("the settlement is not new: "+settlement);
		
		if (settlement.getTransactionResponse()==null)
			throw new IllegalArgumentException ("the settlement has not a response: "+settlement);

		if (settlement.getTransactionResponse().isApproved())
		{
			settlement.setEnabled(true);
			settlement = super.saveOrUpdate(settlement);
		}
		
		return settlement;
	}

	public int findLastBatchNumber(MerchantAccount merchantAccount) {
		
		if (merchantAccount==null)
			throw new RuntimeException("merchantAccount null ");
		
		Search searchCriteria =  new Search(BatchNumber.class);
		searchCriteria.addFilterEqual("merchantAccount", merchantAccount);
		searchCriteria.setMaxResults(1);
		
		List<BatchNumber> list = getBatchNumberDao().findByCriteria(searchCriteria);
		if (list!=null && !list.isEmpty())
		{
			int batchNumber = list.get(0).getNumber();
			return batchNumber;
		}

		BatchNumber batchNumber = initializeBatchNumber(merchantAccount,0);
		return batchNumber.getNumber();
	}

	private BatchNumber initializeBatchNumber(MerchantAccount merchantAccount,int number) {
		BatchNumber batchNumber = new BatchNumberImpl();
		batchNumber.setNumber(number);
		batchNumber.setMerchantAccount(merchantAccount);
		batchNumber = getBatchNumberDao().saveOrUpdate(batchNumber);
		return batchNumber;
	}
	
	public void saveOrUpdateBatchNumber(MerchantAccount merchantAccount,int number) {
		
		if (merchantAccount==null)
			throw new RuntimeException("merchantAccount null ");
		
		Search searchCriteria =  new Search(BatchNumber.class);
		searchCriteria.addFilterEqual("merchantAccount", merchantAccount);
		searchCriteria.setMaxResults(1);
		
		List<BatchNumber> list = getBatchNumberDao().findByCriteria(searchCriteria);
		if (list!=null && !list.isEmpty())
		{
			BatchNumber batchNumber = list.get(0);
			batchNumber.setNumber(number);
			getBatchNumberDao().saveOrUpdate(batchNumber);
		}
		else
		{
			initializeBatchNumber(merchantAccount,number);
		}
	}

	public int getLastTransactionSequenceNumber(BusinessUnit businessUnit) {

		if (businessUnit==null)
			throw new RuntimeException("businessUnit null ");
		
		Search searchCriteria =  new Search(AuthorizationHotel.class);
		searchCriteria.addFilterEqual("businessUnit", businessUnit);
		searchCriteria.addSortDesc("date");
		searchCriteria.setMaxResults(1);
		
		AuthorizationHotel authorizationHotel = getAuthorizationHotelService().find(searchCriteria);
		if (authorizationHotel!=null)
		{
			String transactionSequenceNumber = authorizationHotel.getTransactionResponse().getTransactionSequenceNumber();
			return Integer.valueOf(transactionSequenceNumber);
		}
			
		return 0;
	}

	public AuthorizationHotelService<AuthorizationHotel> getAuthorizationHotelService() {
		return authorizationHotelService;
	}

	public void setAuthorizationHotelService(AuthorizationHotelService<AuthorizationHotel> authorizationHotelService) {
		this.authorizationHotelService = authorizationHotelService;
	}

	public BatchNumberDao<BatchNumber, Serializable, SearchCriteria> getBatchNumberDao() {
		return batchNumberDao;
	}

	public void setBatchNumberDao(BatchNumberDao<BatchNumber, Serializable, SearchCriteria> batchNumberDao) {
		this.batchNumberDao = batchNumberDao;
	}

}
