package biz.janux.payment;

import java.io.Serializable;

import org.janux.bus.search.SearchCriteria;

import api.org.janux.bus.service.GenericServiceImpl;

import com.trg.search.Search;

public class SettlementItemHotelServiceImpl <T extends SettlementItemHotelImpl,ID extends Serializable,DW extends SettlementItemHotelDao<T, ID, SearchCriteria,SettlementItemFacet>, DR extends SettlementItemHotelDao<T, ID, SearchCriteria,SettlementItemFacet>>
extends GenericServiceImpl<T,ID,SearchCriteria,DW,DR> 
implements SettlementItemHotelService<T>  {

	private
	AuthorizationHotelService<AuthorizationHotel> authorizationHotelService;
	
	public T findByUuid(String uuid) {
		
		if (uuid==null)
			throw new RuntimeException("uuid null or empty");
		
		Search searchCriteria = new Search();
		searchCriteria.addFilterEqual("uuid", uuid);
		
		return find(searchCriteria);
	}

	public T delete(String uuid) {
		T settlementItemHotel = findByUuid(uuid);
		
		if (settlementItemHotel.getSettlement()!=null)
			throw new IllegalStateException ("The settement item can not be deleted because it was captured");
		
		if (settlementItemHotel.getAuthorization()!=null)
		{
			getAuthorizationHotelService().unBatched((AuthorizationHotel)settlementItemHotel.getAuthorization());
		}
		
		super.delete(settlementItemHotel);
		return settlementItemHotel;
	}

	public void setAuthorizationHotelService(AuthorizationHotelService<AuthorizationHotel> authorizationHotelService) {
		this.authorizationHotelService = authorizationHotelService;
	}

	public AuthorizationHotelService<AuthorizationHotel> getAuthorizationHotelService() {
		return authorizationHotelService;
	}

}
