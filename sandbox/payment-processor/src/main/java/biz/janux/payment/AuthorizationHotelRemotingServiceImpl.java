package biz.janux.payment;

import java.util.Iterator;
import java.util.List;

import org.janux.bus.search.SearchCriteria;

public class AuthorizationHotelRemotingServiceImpl extends RemotingServiceImpl implements AuthorizationHotelRemotingService{

	private AuthorizationHotelService<AuthorizationHotel> authorizationHotelService;
	
	private CreditCardStorageService  creditCardStorageService;
	
	private BusinessUnitService<SearchCriteria>  businessUnitService;
	
	public List<AuthorizationHotel> findAuthorizationsActive(String businessUnitUuid, String creditCardUuid) {
		List<AuthorizationHotel> list = getAuthorizationHotelService().findAuthorizationsActive(businessUnitUuid, creditCardUuid);
		for (Iterator<AuthorizationHotel> iterator = list.iterator(); iterator.hasNext();) {
			AuthorizationHotel authorizationHotel = (AuthorizationHotel) iterator.next();
			getTranslatorObjectService().translate(authorizationHotel);
		}
		return list; 
	}

	public boolean isAuthorizationExist(String businessUnitUuid, String creditCardUuid) {
		return getAuthorizationHotelService().isAuthorizationExist(businessUnitUuid, creditCardUuid);
	}
	
	public AuthorizationHotel saveNew(AuthorizationHotel authorization, String businessUnitUuid, String creditCardUuid,AuthorizationType authorizationType) {
		CreditCard creditCard = getCreditCardStorageService().load(creditCardUuid,false);
		BusinessUnit businessUnit = getBusinessUnitService().load(businessUnitUuid);
		
		authorization.setCreditCard(creditCard);
		authorization.setBusinessUnit(businessUnit);
		
		authorization = getAuthorizationHotelService().create(authorization, authorization.getAmount(), authorization.getTransactionResponse(), authorizationType);
		return getTranslatorObjectService().translate(authorization);
	}

	public void setAuthorizationHotelService(AuthorizationHotelService<AuthorizationHotel> authorizationHotelService) {
		this.authorizationHotelService = authorizationHotelService;
	}

	public AuthorizationHotelService<AuthorizationHotel> getAuthorizationHotelService() {
		return authorizationHotelService;
	}

	public void setCreditCardStorageService(CreditCardStorageService creditCardStorageService) {
		this.creditCardStorageService = creditCardStorageService;
	}

	public CreditCardStorageService getCreditCardStorageService() {
		return creditCardStorageService;
	}

	public void setBusinessUnitService(BusinessUnitService<SearchCriteria> businessUnitService) {
		this.businessUnitService = businessUnitService;
	}

	public BusinessUnitService<SearchCriteria> getBusinessUnitService() {
		return businessUnitService;
	}

}
