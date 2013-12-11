package biz.janux.payment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.janux.bus.search.SearchCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.trg.search.Search;

public class BusinessUnitServiceMockImpl implements BusinessUnitService<SearchCriteria> {
	
	public Logger log = LoggerFactory.getLogger(this.getClass());
	private Map<String, BusinessUnit> savedBusinessUnitsByUuid = new HashMap<String, BusinessUnit>();
	private Map<String, BusinessUnit> savedBusinessUnitsByCode = new HashMap<String, BusinessUnit>();
	
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
				getSavedBusinessUnitsByCode().remove(businessUnit.getCode());
				getSavedBusinessUnitsByUuid().remove(businessUnit.getUuid());
				log.debug("Deleted businessUnit with uuid: "+ uuid);
			}
			else
			{
				businessUnit.setEnabled(false);
				this.save(businessUnit);
				log.debug("Disabled businessUnit with uuid: "+ uuid);
			}
		}
	}

	private boolean canBeDeleted(String uuid) {
		log.debug("Asking if the businessUnit can be deleted with uuid: "+ uuid);
		
		log.debug("Looking for transactions for this businessUnit");
		Search searchCriteria = new Search();
		searchCriteria.addFilterEqual("businessUnit.uuid", uuid);
		
		/*
		 * TODO ab:110920:This validation was commented to avoid the dependency
		 * with the janux-payment-processor So, we should analyze how implement
		 * it.
		 */
		/*
		List<Transaction<TransactionResponse>> list = getTransactionService().findAll(searchCriteria);
		if (!list.isEmpty())
		{
			log.debug("The businessUnit has transactions, can not be deleted");
			return false;
		}*/
		return true;
	}
	
	public BusinessUnit save(BusinessUnit businessUnit)
	{
		getSavedBusinessUnitsByUuid().put(businessUnit.getUuid(), businessUnit);
		getSavedBusinessUnitsByCode().put(businessUnit.getCode(), businessUnit);
		return businessUnit;
	}
	
	public List<BusinessUnit> findByCriteria(SearchCriteria searchCriteria) {
		Search search = ((Search)searchCriteria);
		if (search.getFilters().size()==1 && search.getFilters().get(0).getProperty().equalsIgnoreCase("code"))
		{
			BusinessUnit businessUnit= getSavedBusinessUnitsByCode().get(search.getFilters().get(0).getValue().toString());
			List<BusinessUnit> list = new ArrayList<BusinessUnit>();
			list.add(businessUnit);
			return list;
		}
		throw new UnsupportedOperationException();
	}

	public BusinessUnit load(String businessUnitUuid) {
		return getSavedBusinessUnitsByUuid().get(businessUnitUuid);
	}
	
	public BusinessUnit find(SearchCriteria searchCriteria) {
		List<BusinessUnit> list = findByCriteria(searchCriteria);
		BusinessUnit businessUnit = null; 
		if (!list.isEmpty())
			businessUnit = list.get(0);
		
		return businessUnit;
	}

	public void setSavedBusinessUnitsByUuid(Map<String, BusinessUnit> savedBusinessUnitsByUuid) {
		this.savedBusinessUnitsByUuid = savedBusinessUnitsByUuid;
	}

	public Map<String, BusinessUnit> getSavedBusinessUnitsByUuid() {
		return savedBusinessUnitsByUuid;
	}

	public void setSavedBusinessUnitsByCode(Map<String, BusinessUnit> savedBusinessUnitsByCode) {
		this.savedBusinessUnitsByCode = savedBusinessUnitsByCode;
	}

	public Map<String, BusinessUnit> getSavedBusinessUnitsByCode() {
		return savedBusinessUnitsByCode;
	}

	public BusinessUnit resolveBusinessUnit(String businessUnitCode, boolean autoCreateBusinessUnit, IndustryType industryType) {
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
					throw new RuntimeException("BusinessUnit not found with code "+BUSINESS_UNIT_CODE_BY_DEFAULT);
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
					businessUnit.setName(businessUnitCode);
					businessUnit.setCode(businessUnitCode);
					businessUnit.setIndustryType(industryType);
					businessUnit = save(businessUnit);
				}
				else
					throw new RuntimeException("BusinessUnit not found with code "+businessUnitCode);
			}	
		}
		return businessUnit;
	}

}
