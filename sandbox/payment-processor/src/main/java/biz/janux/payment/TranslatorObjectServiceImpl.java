package biz.janux.payment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class TranslatorObjectServiceImpl implements TranslatorObjectService {

	public Authorization translate(Authorization authorization) {
		if (authorization!=null)
			authorization.setModifications((List)translate(authorization.getModifications()));
		
		return authorization;
	}
	
	public AuthorizationHotel translate(AuthorizationHotel authorizationHotel) {
		if (authorizationHotel!=null)
			authorizationHotel = (AuthorizationHotel) translate((Authorization)authorizationHotel);
		
		return authorizationHotel;
	}

	public List<Object> translate(List<Object> list) {
		
		List listJava = new ArrayList<Object>();
		for (Iterator<Object> iterator = list.iterator(); iterator.hasNext();) {
			Object object = (Object) iterator.next();
			listJava.add(translate(object));
		}
		return listJava;
	}

	public Object translate(Object object) {
		if (object instanceof List)
			return translate((List<Object>)object);
		/*if (object instanceof SettlementItemHotel)
			return translate((SettlementItemHotel)object);
		if (object instanceof Settlement)
			return translate((Settlement)object);
		if (object instanceof Authorization)
			return translate((Authorization)object);
		if (object instanceof AuthorizationHotel)
			return translate((AuthorizationHotel)object);*/
		
		return object;
	}

	public SettlementItemHotel translate(SettlementItemHotel settlementItemHotel) {
		if (settlementItemHotel!=null)
		{
			//settlementItemHotel.setSettlement(translate(settlementItemHotel.getSettlement()));
			settlementItemHotel.setAuthorization(translate(settlementItemHotel.getAuthorization()));
		}
		return settlementItemHotel;
	}

	public Settlement translate(Settlement settlement) {
		if (settlement!=null){
			settlement.setSettlementItems((List)translate(settlement.getSettlementItems()));
			
			/**
			 * avoid lazy exception
			 */
			for (Iterator iterator = settlement.getSettlementItems().iterator(); iterator.hasNext();) {
				SettlementItemHotel settlementItemHotel = (SettlementItemHotel) iterator.next();
				if (settlementItemHotel.getAuthorization()!=null)
					settlementItemHotel.setAuthorization(null);
			}
		}
		return settlement;
	}
}
