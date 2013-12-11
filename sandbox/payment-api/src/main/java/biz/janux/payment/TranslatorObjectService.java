package biz.janux.payment;

import java.util.List;


/**
 * This service translates objects that have to returned to client. 
 * For example: Translates hibernate collections by java collections for a serialization in the future
 * 
 * @author albertobuffagni@gmail.com
 *
 */
public interface TranslatorObjectService {

	public AuthorizationHotel translate(AuthorizationHotel authorizationHotel);
	
	public Authorization translate(Authorization authorization);
	
	public SettlementItemHotel translate(SettlementItemHotel settlementItemHotel);
	
	public Settlement translate(Settlement settlement);

	public List<Object> translate(List<Object> list);

	public Object translate(Object object);
}
