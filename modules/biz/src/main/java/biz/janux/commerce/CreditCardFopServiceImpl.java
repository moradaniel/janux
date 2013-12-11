package biz.janux.commerce;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author <a href="mailto:alberto.buffagni@janux.org">Alberto Buffagni</a>
 * @author <a href="mailto:philippe.paravicini@janux.org">Philippe Paravicini</a>
 * @since 0.3.03
 */
public class CreditCardFopServiceImpl implements CreditCardFopService 
{
	private static Log log = LogFactory.getLog(CreditCardFopServiceImpl.class);

	private CreditCardFopDao    creditCardFopDao;
	private CreditCardEncryptor creditCardEncryptor;
	private CreditCardMask      creditCardMask;


	public void setCreditCardFopDao(CreditCardFopDao creditCardFopDao) {
		this.creditCardFopDao = creditCardFopDao;
	}

	public CreditCardFopDao getCreditCardFopDao() {
		return creditCardFopDao;
	}
	

	public void setCreditCardEncryptor(CreditCardEncryptor creditCardEncryptor) {
		this.creditCardEncryptor = creditCardEncryptor;
	}

	public CreditCardEncryptor getCreditCardEncryptor() {
		return creditCardEncryptor;
	}

	
	public void setCreditCardMask(CreditCardMask creditCardMask) {
		this.creditCardMask = creditCardMask;
	}

	public CreditCardMask getCreditCardMask() {
		return creditCardMask;
	}

	
	/** convenience method that calls getCreditCardMask().maskNumber() */
	public String maskNumber(String num)
	{
		if (this.getCreditCardMask() == null) {
			throw new IllegalStateException ("CreditCardMask has not been instantiated");
		}

		return this.getCreditCardMask().maskNumber(num);
	}
	
	public void saveOrUpdate(CreditCardFop creditCardFop) throws CreditCardFopException
	{
		try{
			getCreditCardFopDao().saveOrUpdate(creditCardFop);
		} catch (Exception e) {
			 throw new CreditCardFopException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<CreditCardFop> findByCriteria( CreditCardFopCriteria creditCardFopCriteria) throws CreditCardFopException 
	{
		List<CreditCardFop> listCreditCard;
		try {
			listCreditCard = getCreditCardFopDao().findAll(creditCardFopCriteria);
		} catch (Exception e) {
			throw new CreditCardFopException(e);
		}
		return listCreditCard;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void encryptCreditCardNumber(CreditCardFop cc, boolean toShow) throws CreditCardFopException 
	{
		try {
			if (cc.getCardNumber()==null)
				throw new Exception("The cc number is null");
			
			if (cc.isMasked())
				throw new Exception("This cc was already masked");

			if (toShow){
				if (cc.isEncrypted())
					cc.setCardNumberToShow(cc.getCardNumber());
				else
					cc.setCardNumberToShow(getCreditCardEncryptor().encrypt(cc.getCardNumber()));
			}
			else {
				
				if (cc.isEncrypted())
					throw new Exception("This cc was already encrypted");
				
				cc.setCardNumber(getCreditCardEncryptor().encrypt(cc.getCardNumber()));
				cc.setCardNumberToShow(null);
				cc.setEncrypted(true);
			}
		} catch (Exception e) {
			throw new CreditCardFopException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void decryptCreditCardNumber(CreditCardFop cc, boolean toShow) throws CreditCardFopException 
	{
		try {
			if (cc.getCardNumber()==null)
				throw new Exception("The cc number is null");
			
			if (!cc.isEncrypted())
				throw new Exception("This cc is not encrypted");
			
			if (cc.isMasked())
				throw new Exception("This cc was already masked");
			
			if (toShow)
				cc.setCardNumberToShow(getCreditCardEncryptor().decrypt(cc.getCardNumber()));
			else {
				cc.setCardNumber(getCreditCardEncryptor().decrypt(cc.getCardNumber()));
				cc.setCardNumberToShow(null);
				cc.setEncrypted(false);
			}
		} catch (Exception e) {
			throw new CreditCardFopException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void maskCreditCardNumber(CreditCardFop cc, boolean toShow) throws CreditCardFopException 
		{
		try {
			
			if (cc.getCardNumber()==null)
				throw new Exception("The cc number is null");
			
			if (!toShow && cc.isMasked())
				throw new Exception("This cc was already masked");
			
			if (cc.isEncrypted()) {
				if (toShow)
					cc.setCardNumberToShow(getCreditCardEncryptor().decrypt(cc.getCardNumber()));
				else {
					cc.setCardNumber(getCreditCardEncryptor().decrypt(cc.getCardNumber()));
					cc.setCardNumberToShow(null);
					cc.setEncrypted(false);
				}
			} else if (toShow) {
				cc.setCardNumberToShow(cc.getCardNumber());
			}

			if (!cc.isMasked()) {
				if (toShow)
					cc.setCardNumberToShow(maskNumber(cc.getCardNumberToShow()));
				else {
					cc.setCardNumber(maskNumber(cc.getCardNumber()));
					cc.setCardNumberToShow(null);
					cc.setMasked(true);
				}
			}
		} catch (Exception e) {
			throw new CreditCardFopException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void prepareCreditCardToShow(CreditCardFop cc, boolean displayCreditCardMasked) throws CreditCardFopException 
	{
		if (log.isDebugEnabled()) log.debug("cc num before: " + cc.getCardNumber());
		try {
			if (cc.getCardNumber()==null)
				throw new Exception("The cc number is null");
			
			if (!displayCreditCardMasked) {
				if (cc.isEncrypted())
					decryptCreditCardNumber(cc, true);
				else
					cc.setCardNumberToShow(cc.getCardNumber());
			} else {
				maskCreditCardNumber(cc, true);
			}
		} catch (Exception e) {
			throw new CreditCardFopException(e);
		}
	}

}
