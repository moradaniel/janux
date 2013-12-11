package biz.janux.payment;

import java.io.Serializable;

/**
 ***************************************************************************************************
 * The BusinessUnit entity was created as a means to distinguish the Credit Cards and transactions
 * stored or processed by different lines of business within one or more Organizations; it represents
 * a useage of a MerchantAccount by an Organization; a {@link MerchantAccount} may be associated
 * with one-to-many BusinessUnits, and a BusinessUnit may have at most one {@link MerchantAccount};
 * A CreditCard Number is unique within a BusinessUnit.
 * <p>
 * For example, this may be the case where it is desirable to distinguish between the payments to a
 * Gift Shop from those to a Ticket Counter within the same Organization, but still process the
 * payments within the same MerchantAccount. 
 * </p><p>
 * Another case may be where a Hotel Chain wants to allow multiple hotels from the chain to process
 * credit cards through the same MerchantAccount, but where the CreditCard records, Transactions and
 * Settlements must be segregated by Hotel.  This would represent the case where multiple
 * Organizations share the same BusinessUnit.
 * </p><p>
 * In its simplest form, a BusinessUnit can simply be represented as a text code, which must be
 * unique across the application.  It is left to the calling application to generate unique
 * BusinessUnit codes.
 * </p>
 * You may be asking "Why not make the relationship between BusinessUnit and MerchantAccount a
 * many-to-many relationship? is it not possible that a given Business Unit may want to use multiple
 * Merchant Accounts?".  This is sensible, because a BusinessUnit appears to have many of the same
 * semantics as an {@link biz.janux.people.Organization}, or even of a more generic {@link
 * biz.janux.people.Party}, and it is quite conceivable that an Organization may use multiple
 * MerchantAccounts.  
 * </p><p>
 * Indeed, there exists a conceptual many-to-many relationship between Organization (or Party) and
 * MerchantAccount. The BusinessUnit is different from an Organization in that it represents and
 * qualifies the MerchantAccount side of the many-to-many relationship between Organization and
 * MerchantAccount.  In the scope of the biz.janux.payment package, the BusinessUnit is thus
 * implemented as a one-to-many relationship with MerchantAccount.  In ascii UML:
 * </p>
 * <pre>
 *                           0:1      0:*              0:*      0:1
 * | Organization (or Party) | -------- | BusinessUnit | -------- | MerchantAccount |
 *                                      |
 *             stored in calling app.   |         stored in biz.janux.payment                    
 * </pre>
 * </p><p>
 * In the business case where it is desirable to represent the case where an Organization has
 * multiple MerchantAccount records, we recommend that a different Business Unit be created for each
 * such Merchant Account, and that the relationship between the Organization (or Party) and the
 * BusinessUnit codes be maintained in the calling system, outside of the scope of the biz.janux.payment
 * services. The expectation is thus that an application using the payment services will represent
 * and store the one-to-many relationship between Organization (or Party) and BusinessUnit as it
 * sees fit.
 * </p>
 *
 * 
 * @author  <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @author  <a href="mailto:alberto.buffagni@janux.org">Alberto Buffagni</a>
 *
 * @since 0.4.0 - 2011-11-15
 ***************************************************************************************************
 */
public interface BusinessUnit extends Serializable{

	/**       
	 * A random alphanumeric string that uniquely identifies this Credit Card in the Janux Payment
	 * Service. This is the code that external clients must use to reference the Credit Card when
	 * calling the Janux Payment Service.
	 */
	public String getUuid();
	
	public String getCode();
	public void setCode(String code);
	
	public String getName();
	public void setName(String name);
	
	public MerchantAccount getMerchantAccount();
	public void setMerchantAccount(MerchantAccount merchantAccount);
	
	public IndustryType getIndustryType();
	public void setIndustryType(IndustryType industryType);
	
	/** Provides a way to disable a merchant account without deleting it from the system */
	public boolean isEnabled();
	public void setEnabled(boolean b);

	
}
