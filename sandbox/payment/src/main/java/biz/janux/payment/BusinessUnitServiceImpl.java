package biz.janux.payment;

import java.util.List;

import org.janux.bus.search.SearchCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.trg.search.Search;

public class BusinessUnitServiceImpl implements BusinessUnitService<SearchCriteria> {
	
	public Logger log = LoggerFactory.getLogger(this.getClass());

	private BusinessUnitDao<BusinessUnit, Integer, SearchCriteria> businessUnitDao;
	private TransactionService transactionService;
	
	public void delete(String uuid) {
		log.debug("Deleting businessUnit with uuid: "+ uuid);
		
		BusinessUnit businessUnit = load(uuid);

		delete(businessUnit);
	}

	public void delete(BusinessUnit businessUnit) {
		log.debug("Deleting businessUnit with uuid: "+ businessUnit);

		if (businessUnit!=null)
		{
			String uuid = businessUnit.getUuid();
			if (canBeDeleted(uuid))
			{
				getBusinessUnitDao().delete(businessUnit);
				log.debug("Deleted businessUnit with uuid: "+ uuid);
			}
			else
			{
				businessUnit.setEnabled(false);
				getBusinessUnitDao().saveOrUpdate(businessUnit);
				log.debug("Disabled businessUnit with uuid: "+ uuid);
			}
		}
	}

	private boolean canBeDeleted(String uuid) {
		log.debug("Asking if the businessUnit can be deleted with uuid: "+ uuid);
		
		if (getTransactionService()!=null)
		{
			log.debug("Looking for transactions for this businessUnit");
			
			if (uuid==null || uuid.equalsIgnoreCase(""))
				throw new RuntimeException("uuid null or empty");
			
			Search searchCriteria = new Search();
			searchCriteria.addFilterEqual("businessUnit.uuid", uuid);
			searchCriteria.setMaxResults(1);
			
			List list = getTransactionService().findAll(searchCriteria);
			if (!list.isEmpty())
			{
				log.debug("The businessUnit has transactions, can not be deleted");
				return false;
			}
		}

		return true;
	}
	
	/**
	 * 
	 * if the businessUnitCode is null, the default BusinessUnitId is used internally
	 * if the BusinessUnit Code is not null, the CreditCard Storage Service will
	 * search for a matching BusinessUnit record and, if found, associate it with the
	 * CreditCard
	 * 
	 * if a matching BusinessUnit is not found, the CreditCard Storage Service
	 * will:
	 * throw an exception and not store the credit card
	 * 
	 * if 'autoCreateBusinessUnit' is null, a zero-length string, or equal
	 * to 'false', '0', or 'no'.
	 * create a new business unit record automatically
	 * 
	 * if 'autoCreateBusinessUnit' is a non-zero-length string other
	 * than 'false', '0, or 'no'.
	 * 
	 * @param businessUnitCode
	 * @param autoCreateBusinessUnit
	 * @return
	 */
	public BusinessUnit resolveBusinessUnit(String businessUnitCode, boolean autoCreateBusinessUnit,IndustryType industryType) {
		BusinessUnit businessUnit = null;
		
		if (industryType==null)
			industryType = BUSINESS_UNIT_INDUSTRY_TYPE_BY_DEFAULT;
		
		if (businessUnitCode==null)
		{
			Search searchCriteria = new Search(BusinessUnit.class);
			searchCriteria.addFilterEqual("code",BUSINESS_UNIT_CODE_BY_DEFAULT);
			businessUnit = find(searchCriteria);

			if (businessUnit==null)
			{
				if (autoCreateBusinessUnit)
				{
					businessUnit = new BusinessUnitImpl();
					businessUnit.setCode(BUSINESS_UNIT_CODE_BY_DEFAULT);
					businessUnit.setName(BUSINESS_UNIT_CODE_BY_DEFAULT);
					businessUnit.setIndustryType(industryType);
					businessUnit.setEnabled(true);
					businessUnit = save(businessUnit);
				}
				else
					throw new RuntimeException("BusinessUnit not found with code: "+BUSINESS_UNIT_CODE_BY_DEFAULT);
			}
		}
		else
		{
			Search searchCriteria = new Search(BusinessUnit.class);
			searchCriteria.addFilterEqual("code",businessUnitCode);
			businessUnit = find(searchCriteria);
			if (businessUnit==null)
			{
				if (autoCreateBusinessUnit)
				{
					businessUnit = new BusinessUnitImpl();
					businessUnit.setCode(businessUnitCode);
					businessUnit.setName(businessUnitCode);
					businessUnit.setIndustryType(industryType);
					businessUnit.setEnabled(true);
					businessUnit = save(businessUnit);
				}
				else
					throw new RuntimeException("BusinessUnit not found with code: "+businessUnitCode);
			}	
		}
		return businessUnit;
	}
	
	public BusinessUnit save(BusinessUnit businessUnit)
	{
		return getBusinessUnitDao().saveOrUpdate(businessUnit);
	}
	
	public List<BusinessUnit> findByCriteria(SearchCriteria searchCriteria) {
		return getBusinessUnitDao().findByCriteria(searchCriteria);
	}

	public BusinessUnit load(String businessUnitUuid) {
		
		if (businessUnitUuid==null || businessUnitUuid.equalsIgnoreCase(""))
			throw new RuntimeException("businessUnitUuid with uuid null or empty");
		
		Search searchCriteria = new Search(BusinessUnitImpl.class);
		searchCriteria.addFilterEqual("uuid",businessUnitUuid);
		
		BusinessUnit businessUnit=null;
		List<BusinessUnit> list = getBusinessUnitDao().findByCriteria(searchCriteria);
		if (!list.isEmpty())
			businessUnit = list.get(0);
		
		return businessUnit;
	}
	
	public BusinessUnit find(SearchCriteria searchCriteria) {
		List<BusinessUnit> list = findByCriteria(searchCriteria);
		BusinessUnit businessUnit = null; 
		if (!list.isEmpty())
			businessUnit = list.get(0);
		
		return businessUnit;
	}


	public void setBusinessUnitDao(BusinessUnitDao<BusinessUnit, Integer, SearchCriteria> businessUnitDao) {
		this.businessUnitDao = businessUnitDao;
	}

	public BusinessUnitDao<BusinessUnit, Integer, SearchCriteria> getBusinessUnitDao() {
		return businessUnitDao;
	}

	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	public TransactionService getTransactionService() {
		return transactionService;
	}

}
