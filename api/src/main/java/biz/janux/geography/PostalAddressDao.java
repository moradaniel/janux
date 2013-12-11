package biz.janux.geography;

import java.util.List;

import org.janux.bus.persistence.EntityNotFoundException;
import org.janux.bus.persistence.DataAccessObject;


/**
 ***************************************************************************************************
 * Data Access Class used to access and manage PostalAddress entities
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 2008-03-27
 * 
 * @deprecated use {@link PostalAddressDaoGeneric}
 ***************************************************************************************************
 */
public interface PostalAddressDao extends DataAccessObject
{
	/** 
	 * Loads a PostalAddress object from persistence using its id
	 *
	 * @param id the internal identifier of the PostalAddress
	 * @throws EntityNotFoundException if a PostalAddress object with that id is not found
	 */
	public PostalAddress load(Integer id) throws EntityNotFoundException;

	/** 
	 * Counts PostalAddress entities where the address.getCountryAsString is not null, that is, where
	 * the Country is not stored as a reference; this is mostly meant as a utility method used to
	 * clean up or convert data
	 *
	 * @see PostalAddress#getCountryAsString
	 * @see GeographyService#setCityStateCountry
	 */
	public int countByCountryAsString();

	/** 
	 * Retrieves PostalAddress entities where address.getCountryAsString is not null; this is
	 * mostly meant as a utility method that can be used to clean up or convert data
	 *
	 * @see PostalAddress#getCountryAsString
	 * @see GeographyService#setCityStateCountry
	 */
	public List<PostalAddress> findByCountryAsString();

	/** 
	 * Retrieves PostalAddress entities where address.getCountryAsString is not null;
	 * this is mostly meant as a utility method that can be used to clean up or convert data
	 *
	 * @see PostalAddress#getCountryAsString
	 * @see GeographyService#setCityStateCountry
	 *
	 * @param numRecords the maximum number of PostalAddresses to return (equivalent to LIMIT modifier in SQL Selects)
	 * @param offset when limiting the number of records returned, the 
	 */
	public List<PostalAddress> findByCountryAsString(Integer numRecords, Integer offset);

	/** 
	 * Count PostalAddress entities where the address.getCountryAsString field matches the country
	 * string passed; this is mostly meant as a utility method that can be used to clean up or convert data
	 *
	 * @see PostalAddress#getCountryAsString
	 * @see GeographyService#setCityStateCountry
	 *
	 * @param country a string used to match against the PostalAddress.getCountryAsString
	 */
	public int countByCountryAsString(String country);

	/** 
	 * Retrieves PostalAddress entities where the address.getCountryAsString field matches the country
	 * string passed; this is mostly meant as a utility method that can be used to clean up or convert data
	 *
	 * @see PostalAddress#getCountryAsString
	 * @see GeographyService#setCityStateCountry
	 *
	 * @param country a string used to match against the PostalAddress.getCountryAsString
	 */
	public List<PostalAddress> findByCountryAsString(String country);

	/** 
	 * Retrieves PostalAddress entities where the address.getCountryAsString field matches the country
	 * string passed; this is mostly meant as a utility method that can be used to clean up or convert data
	 *
	 * @see PostalAddress#getCountryAsString
	 * @see GeographyService#setCityStateCountry
	 *
	 * @param country a string used to match against the PostalAddress.getCountryAsString
	 * @param numRecords the maximum number of PostalAddresses to return (equivalent to LIMIT modifier
	 * in SQL Selects)
	 * @param offset when limiting the number of records returned, the 
	 */
	public List<PostalAddress> findByCountryAsString(String country, Integer numRecords, Integer offset);


}
