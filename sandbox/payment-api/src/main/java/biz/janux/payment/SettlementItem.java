package biz.janux.payment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import biz.janux.payment.CreditCard;

/**
 * A settlement item is each payment applied by a {@link CreditCard}
 * They are grouped into a settlement batch {@link Settlement}
 * 
 * @author albertobuffagni@gmail.com
 *
 */
public interface SettlementItem  extends Serializable{

	/**       
	 * A random alphanumeric string that uniquely identifies this Credit Card in the Janux Payment
	 * Service. This is the code that external clients must use to reference the Credit Card when
	 * calling the Janux Payment Service.
	 */
	public String getUuid();
	
	/**
	 * It is null when the settlement item is a {@link SettlementItemType#CREDIT}.
	 * So a authorization is not needed
	 */
	public Authorization getAuthorization();
	public void setAuthorization(Authorization authorization);
	
	/**
	 * This information is used when the settlement item is a {@link SettlementItemType#CREDIT} 
	 * So a authorization is not needed.
	 * It is null if {@link SettlementItemType#PURCHASE}
	 */
	public CreditCard getCreditCard();
	public void setCreditCard(CreditCard creditCard);

	/**
	 * This information is used when the settlement item is a {@link SettlementItemType#CREDIT} 
	 * So a authorization is not needed
	 * It is null if {@link SettlementItemType#PURCHASE}
	 */
	public BusinessUnit getBusinessUnit();
	public void setBusinessUnit(BusinessUnit businessUnit);
	
	public BigDecimal getAmount();
	public void setAmount(BigDecimal amount);
	
	public SettlementItemType getType();
	public void setType(SettlementItemType type);
	
	public Date getDate();
	public void setDate(Date date);
	
	/**
	 * Indicates the batch that it belongs 
	 * If is not null the settlement item has been settled and included into settlement batch.
	 */
	public Settlement getSettlement();
	public void setSettlement(Settlement settlement);
	
	/**
	 * 4.156 Purchase identifier This field contains a 25-character identifier
	 * assigned by the merchant. 
	 * 
	 * 4.156.1 Purchase identifier (direct
	 * marketing/commercial card) This field contains a Purchase Order Number
	 * assigned by the merchant. This field entry must be left-justified and
	 * space-filled to 25 positions. 
	 * 
	 * 4.156.2 Rental agreement number (auto
	 * rental) This field contains a Rental Agreement Number assigned by the
	 * merchant. This field entry must be left-justified and space-filled to 25
	 * positions. 
	 * 
	 * 4.156.3 Folio number (hotel) This field contains a Hotel Folio
	 * Number assigned by the merchant. This field entry must be left-justified
	 * and space-filled to 25 positions.
	 * 
	 */
	public String getPurchaseIdentifier();
	public void setPurchaseIdentifier(String purchaseIdentifier);
	

	/**
	 * Used to filter the settlement items done by a specific external entity such as Folios, FolioLines, etc from the external app such as PMS, CRS, etc.
	 * For ex: <external app>_<entity name>_<id> myms_folioLine_1234
	 */
	public String getExternalSourceId();
	public void setExternalSourceId(String externalSourceId);
}
