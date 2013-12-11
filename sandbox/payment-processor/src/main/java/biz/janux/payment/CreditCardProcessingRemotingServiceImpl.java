package biz.janux.payment;

import java.math.BigDecimal;
import java.util.Date;

public class CreditCardProcessingRemotingServiceImpl extends RemotingServiceImpl implements CreditCardProcessingRemotingService {

	CreditCardProcessingService creditCardProcessingService;
	
	public SettlementItemHotel applyPayment(BigDecimal amount, Date date, String creditCardUuid, String businessUnitUuid, String purchaseIdentifier, BigDecimal averageRate, Date checkOutDate, Integer stayDuration,String externalSourceIdAuthorization,String externalSourceId) {
		SettlementItemHotel settlementItemHotel = getCreditCardProcessingService().applyPayment(amount, date, creditCardUuid, businessUnitUuid, purchaseIdentifier, averageRate, checkOutDate, stayDuration, externalSourceIdAuthorization, externalSourceId);
		settlementItemHotel = getTranslatorObjectService().translate(settlementItemHotel);
		return settlementItemHotel;
	}
	
	public SettlementItemHotel applyPayment(BigDecimal amount, Date date, String creditCardUuid, String businessUnitUuid, String purchaseIdentifier, BigDecimal averageRate, Date checkOutDate, Integer stayDuration,String externalSourceIdAuthorization,String externalSourceId,String authorizationUuid) {
		SettlementItemHotel settlementItemHotel = getCreditCardProcessingService().applyPayment(amount, date, creditCardUuid, businessUnitUuid, purchaseIdentifier, averageRate, checkOutDate, stayDuration, externalSourceIdAuthorization, externalSourceId,authorizationUuid);
		settlementItemHotel = getTranslatorObjectService().translate(settlementItemHotel);
		return settlementItemHotel;
	}

	public AuthorizationHotel authorize(String businessUnitUuid, String creditCardUuid, BigDecimal authAmount, Integer stayDuration,String externalSourceId) {
		AuthorizationHotel authorizationHotel = getCreditCardProcessingService().authorize(businessUnitUuid, creditCardUuid, authAmount, stayDuration, externalSourceId);
		authorizationHotel = getTranslatorObjectService().translate(authorizationHotel);
		return authorizationHotel;
	}

	public AuthorizationHotel authorizeOffline(String businessUnitUuid, String creditCardUuid, BigDecimal authAmount, Integer stayDuration, String approvalCode,String externalSourceId) {
		AuthorizationHotel authorizationHotel = getCreditCardProcessingService().authorizeOffline(businessUnitUuid, creditCardUuid, authAmount, stayDuration, approvalCode, externalSourceId);
		authorizationHotel = getTranslatorObjectService().translate(authorizationHotel);
		return authorizationHotel;
	}

	public AuthorizationHotel increment(String originalAuthorizationUuid, BigDecimal amount) {
		AuthorizationHotel authorizationHotel = getCreditCardProcessingService().increment(originalAuthorizationUuid, amount);
		authorizationHotel = getTranslatorObjectService().translate(authorizationHotel);
		return authorizationHotel;
	}

	public AuthorizationHotel modify(String originalAuthorizationUuid, BigDecimal amount) {
		AuthorizationHotel authorizationHotel = getCreditCardProcessingService().modify(originalAuthorizationUuid, amount);
		authorizationHotel = getTranslatorObjectService().translate(authorizationHotel);
		return authorizationHotel;
	}

	public AuthorizationHotel reverse(String originalAuthorizationUuid, BigDecimal amount) {
		AuthorizationHotel authorizationHotel = getCreditCardProcessingService().reverse(originalAuthorizationUuid, amount);
		authorizationHotel = getTranslatorObjectService().translate(authorizationHotel);
		return authorizationHotel;	}

	public Settlement settlement(String businessUnitUuid) {
		Settlement settlement = getCreditCardProcessingService().settlement(businessUnitUuid);
		settlement = getTranslatorObjectService().translate(settlement);
		return settlement;
	}

	public Settlement settlement(String businessUnitUuid, int batchNumber) {
		Settlement settlement = getCreditCardProcessingService().settlement(businessUnitUuid, batchNumber);
		settlement = getTranslatorObjectService().translate(settlement);
		return settlement;
	}
	
	public int initializeBatchNumber(String businessUnitUuid, int number) {
		return getCreditCardProcessingService().initializeBatchNumber(businessUnitUuid, number);
	}

	public void voidAuthorization(String originalAuthorizationUuid) {
		getCreditCardProcessingService().voidAuthorization(originalAuthorizationUuid);
	}

	public CreditCardProcessingService getCreditCardProcessingService() {
		return creditCardProcessingService;
	}

	public void setCreditCardProcessingService(CreditCardProcessingService creditCardProcessingService) {
		this.creditCardProcessingService = creditCardProcessingService;
	}
	
	public Object generic(Object[] object) {
		AuthorizationHotel authorizationHotel = authorize((String)object[0], (String)object[1], (BigDecimal)object[2], (Integer)object[3], (String)object[4]);
		//authorizationHotel = getAuthorizationService().load(authorizationHotel);
		//authorizationHotel.setCreditCard(null);
		//authorizationHotel.setBusinessUnit(null);
		//authorizationHotel.setTransactionResponse(null);
		//authorizationHotel.setModifications(null);
		return authorizationHotel;
	}
}
