package biz.janux.commerce;

import java.util.List;

import org.janux.bus.persistence.HibernateDataAccessObject;

import biz.janux.commerce.CreditCardFop;

/**
 * Used to search for credit cards. This DAO may eventually be deprecated or
 * modified when we build a Credit Card vault.  In that implementations, we will
 * store on the Party side a token used to identify the credit card, and the
 * credit card will subsequently be retrieved from the vault
 *
 * @author <a href="mailto:alberto.buffagni@janux.org">Alberto Buffagni</a>
 * @author <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.4
 */
public interface CreditCardFopDao  extends HibernateDataAccessObject 
{
	public List<CreditCardFop> findAll(CreditCardFopCriteria creditCardFopCriteria) throws Exception;
}
