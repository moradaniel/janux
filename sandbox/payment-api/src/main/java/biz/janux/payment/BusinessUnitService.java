package biz.janux.payment;

import java.util.List;

import org.janux.bus.search.SearchCriteria;


public interface BusinessUnitService <S extends SearchCriteria>{

	public static final String BUSINESS_UNIT_CODE_BY_DEFAULT = "DEFAULT";
	public static final IndustryType BUSINESS_UNIT_INDUSTRY_TYPE_BY_DEFAULT = IndustryType.HOTEL;

	public List<BusinessUnit> findByCriteria(S searchCriteria);
	
	public BusinessUnit find(S searchCriteria);
	
	/**
	 * If the business unit has transactions associated , it is disabled. 
	 * Otherwise the merchant account is removed. 
	 * @param uuid
	 */
	public void delete(String uuid);

	/**
	 * If the business unit has transactions associated , it is disabled. 
	 * Otherwise the merchant account is removed. 
	 */
	public void delete(BusinessUnit businessUnit);

	public BusinessUnit save(BusinessUnit businessUnit);

	public BusinessUnit load(String businessUnitUuid);
	
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
	 * @param industryType 
	 * @return
	 */
	public BusinessUnit resolveBusinessUnit(String businessUnitCode, boolean autoCreateBusinessUnit,IndustryType industryType);
}
