package biz.janux.payment;

import java.math.BigDecimal;
import java.util.List;


public interface AuthorizationHotelRemotingService extends RemotingService  {

	/**
	 * Authorizations are not batched and not disabled
	 */
	public List<AuthorizationHotel> findAuthorizationsActive(String businessUnitUuid, String creditCardUuid);

	/**
	 * Check if exist some authorization without take into account if it is disabled or batched.
	 */
	public boolean isAuthorizationExist(String businessUnitUuid, String creditCardUuid);	
	
	/**
	 * Save a new authorization if it is approved.
	 * Calculates the authorization amount and creates the {@link AuthorizationModification}
	 * If the authorization is {@link AuthorizationType#INCREMENTAL}
	 * the @param authorization is a existing authorization and 
	 * a new {@link AuthorizationModification} will be added with the auth amount.
	 */
	public AuthorizationHotel saveNew(AuthorizationHotel authorization, String businessUnitUuid, String creditCardUuid,AuthorizationType authorizationType);

}
