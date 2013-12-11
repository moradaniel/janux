package biz.janux.payment;

import java.util.List;

import org.janux.bus.search.SearchCriteria;

/**
 * This is the main Service facade that mediates between an encrypted storage mechanism (such as a
 * storage appliance) and a janux-enabled application that is required to store credit cards in
 * encrypted fashion in a segregated area away of the main application, for example in order to
 * apply Best Practices in the area of credit card security and Payment Industry Compliance (PCI).
 * The expectation is that the credit card data may be stored on both the calling client-application
 * and the janux implementation.  This is so that:
 * <ul>
 * <li>The calling client-application can retrieved an instance of the credit card with a masked
 * Personal Account Number (PAN) from its own storage, and not have to depend on the availability of
 * the encrypted storage mechanism for operations that do not require the PAN in clear text.
 * </li><li>
 * The janux CreditCardProcessingService, if deployed, can have all the information that it requires
 * in order to perform Address Verifications, Authorizations, and Batch Settlements of Transactions.
 * </li>
 * </ul>
 *
 * @author <a href="mailto:alberto.buffagni@janux.org">alberto.buffagni@janux.org</a>
 * @author <a href="mailto:philippe.paravicini@janux.org">philippe.paravicini@janux.org</a>
 */
public interface CreditCardStorageService<S extends SearchCriteria> extends CreditCardValidator
{
	/** 
	 * This is the standard method for storing a Credit Card; unique records are created for the same
	 * credit card with the same Business Unit Code; this makes it possible to segregate credit cards
	 * among business units (such as separate stores, hotels, or departments); a one-way hash is
	 * created at the time of storage which makes it possible to detect that the same credit card has
	 * been stored in different business unit; this makes it possible to merge distinct
	 * customer accounts by matching credit cards across business unit accounts; read below for the
	 * exact sequence of events:
	 * <p>
	 * <ul>
	 * <li>A one-way hash is generated based on the credit card number, and the clear-text storage
	 * (database) is searched for a matching hash/businessUnit combination
	 * </li><li>
	 * If a matching creditCardNumberHash/businessUnit combination is found, that record is updated
	 * accordingly, and the CreditCard object returned will contain the existing Uuid, and any data as
	 * modified in the request.
	 * </li><li>
	 * If a matching creditCardNumberHash/businessUnit combination is not found, a new uuid is generated for the
	 * CreditCard, the number is sent for encryption to the encrypted storage back-end, the token or
	 * identifier returned by the encrypted storage back-end is stored in the token field, and all the
	 * CreditCard data, including the hash number and the association to the BusinessUnit, is stored
	 * in the clear-text database.
	 * </li>
	 * </ul>
	 *
	 * @param creditCard The CreditCard record to be saved/encrypted
	 * @param businessUnit The BusinessUnit with which to associate the CreditCard record
	 *
	 * @throws IllegalArgumentException 
	 * 	if the BusinessUnit passed is null or has not already been created in the database
	 */
	public CreditCard saveOrUpdate(CreditCard creditCard, BusinessUnit businessUnit);

	/** Synonym for {@link #saveOrUpdate(CreditCard, BusinessUnit)} */
	// public CreditCard encrypt(CreditCard creditCard, BusinessUnit businessUnit);
	
	/** 
	 * This method operates in the same way as the {@link #saveOrUpdate(CreditCard, BusinessUnit)} but
	 * an additional boolean flag is provided which indicates whether to create automatically a BusinessUnit 
	 * that does not already exist.
	 *
	 * @param creditCard the Credit Card to be saved
	 * @param businessUnit the business unit with which to associate the CreditCard
	 *
	 * @param autoCreateBusinessUnit 
	 * 	Used in the event that a BusinessUnit with the code provided does not exist: if this flag is
	 * 	true, creates the BusinessUnit automatically.
	 *
	 * @throws IllegalArgumentException 
	 * 	if the BusinessUnit does not match an existing BusinessUnit record, 
	 * 	and	autoCreateBusinessUnit is false
	 */
	public CreditCard saveOrUpdate(CreditCard creditCard, BusinessUnit businessUnit, boolean autoCreateBusinessUnit);


	/** Synonym for {@link #saveOrUpdate(CreditCard, BusinessUnit, boolean)} */
	// public CreditCard encrypt(CreditCard creditCard, BusinessUnit businessUnit, boolean autoCreateBusinessUnit);

	/**  
	 * This method can be used when the application operates within the context of a single
	 * BusinessUnit;  in this case, the CreditCard is internally associated with a pre-existing
	 * DEFAULT BusinessUnit.
	 */
	public CreditCard saveOrUpdate(CreditCard creditCard);

	/** Synonym for {@link #saveOrUpdate(CreditCard)} */
	public CreditCard encrypt(CreditCard creditCard);
	
	
	/** 
	 * This method is functionally equivalent exposes String parameters that make it suitable as a
	 * catch-all method for use via a text-based Web Services interface (such as json structure that
	 * passes a CreditCard structure and a text parameters)
	 *
	 * @param creditCard the Credit Card to be saved
	 *
	 * @param businessUnitCode 
	 * 	The business unit code with which to associate the CreditCard. If null or the empty String,
	 * 	the CreditCard will be associated with the Default BusinessUnit
	 *
	 * @param autoCreateBusinessUnit 
	 * 	Used in the event that a BusinessUnit with the code provided does not exist: if this flag is
	 * 	true, creates the BusinessUnit automatically, otherwise, throws an IllegalArgumentException 
	 *
	 * @param industryType 
	 * 	An optional parameter indicating the default type of Industry (HOTEL, RETAIL, etc..) of the
	 * 	BusinessUnit. Different industry types entail different credit card processing.  If the
	 * 	CreditCardProcessingService will not be used, this parameter can be null, and the default
	 * 	IndustryType for the deployment will be used.
	 *
	 * @throws IllegalArgumentException 
	 * 	if the businessUnitCode does not match an existing BusinessUnit record, 
	 * 	and	autoCreateBusinessUnit is false
	 */
	public CreditCard saveOrUpdate(CreditCard creditCard, String businessUnitCode, boolean autoCreateBusinessUnit, IndustryType industryType);

	/** Synonym for {@link #saveOrUpdate(CreditCard, String, boolean, IndustryType)} */
	// public CreditCard encrypt(CreditCard creditCard, String businessUnitCode, boolean autoCreateBusinessUnit, IndustryType industryType);
	

	/** 
	 * Equivalent to calling {@link #saveOrUpdate(CreditCard, String, boolean, IndustryType)} with
	 * autoCreateBusinessUnit = false and industryType = null.
	 *
	 * @throws IllegalArgumentException If the businessUnitCode does not match an existing BusinessUnit
	 */
	public CreditCard saveOrUpdate(CreditCard creditCard, String businessUnitCode);


	/** Loads the Credit Card based on the UUID passed, optionally decrypting the Personal Account Number */
	public CreditCard load(String uuid, boolean decrypt);

	/** Synonym for load(uuid, true) */
	public CreditCard decrypt(String uuid);

	/** 
	 * Decrypts the Personal Account Number (PAN) of the CreditCard passed; 
	 * the CreditCard instance must have been stored previously and contain a valid UUID 
	 */
	public CreditCard decrypt(CreditCard creditCard);


	/** 
	 * Returns a list of Credit Cards that match the Search Criteria, optionally decrypting the
	 * CreditCards' Personal Account Number (PAN); because this method can potentially return a large
	 * number credit cards unencrypted, it presents a great vulnerability, it should be highly
	 * protected and only deployed when the business case warrants 
	 */ 
	public List<CreditCard> findByCriteria(S searchCriteria, boolean decrypt);

	/** 
	 * Returns the first Credit Card found that matches the Search Criteria; presumably, this is
	 * intended to be used when the Search Criteria is expected to find a single Credit Card
	 */
	public CreditCard findFirstByCriteria(S searchCriteria, boolean decrypt);

	/** By default, a Search returns the CreditCard with a masked number; synonym for findByCriteria(searchCriteria, false) */
	public List<CreditCard> findByCriteria(S searchCriteria);

	/** By default, a Search returns the CreditCard with a masked number; synonym for findFirstByCriteria(searchCriteria, false) */
	public CreditCard findFirstByCriteria(S searchCriteria);

	/** 
	 * Attempts to remove the credit card record from the storage system; if the CreditCard has no other
	 * records associated to it, such as Transactions, it purges the record; otherwise, the CreditCard
	 * record as disabled; returns true if a record was deleted, and false if the record was disabled;
	 * throws an EntityNotFoundException if the CreditCard was not found.
	 */
	public boolean deleteOrDisable(CreditCard creditCard);

	/** @see #deleteOrDisable(CreditCard creditCard) */
	public boolean deleteOrDisable(String uuid);

	/** 
	 * Purges the CreditCard record and all related records, such as Transactions, from the system;
	 * this method should be highly protected, and be used with caution, as the data is irretrievably
	 * lost
	 */
	public boolean purge(CreditCard creditCard);

	/** @see #purge(CreditCard creditCard) */
	public boolean purge(String uuid);
	
}
