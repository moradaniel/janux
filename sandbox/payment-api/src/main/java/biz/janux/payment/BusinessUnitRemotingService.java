package biz.janux.payment;



/**
 * This service is a facade of the {@link BusinessUnitService}. 
 *    
 * @author albertobuffagni@gmail.com
 *
 */
public interface BusinessUnitRemotingService extends RemotingService{

	/**
	 * save or update a {@link BusinessUnit}
	 * If the uuid is null the entity is new
	 * @param businessUnit
	 * @param uuid
	 */
	public BusinessUnit save(BusinessUnit businessUnit,String uuid);
	
	/**
	 * If the business unit has transactions associated , it is disabled. 
	 * Otherwise the merchant account is removed. 
	 * @param uuid
	 */
	public void delete(String uuid);

}
