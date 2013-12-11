package biz.janux.payment;

public class SettlementItemHotelRemotingServiceImpl extends RemotingServiceImpl implements SettlementItemHotelRemotingService {

	SettlementItemHotelService<SettlementItemHotel> settlementItemHotelService;
	
	public SettlementItemHotel find(String uuid) {
		return getTranslatorObjectService().translate(getSettlementItemHotelService().findByUuid(uuid));
	}
	
	public SettlementItemHotel delete(String uuid) {
		return getTranslatorObjectService().translate(getSettlementItemHotelService().delete(uuid));
	}


	public SettlementItemHotelService<SettlementItemHotel> getSettlementItemHotelService() {
		return settlementItemHotelService;
	}

	public void setSettlementItemHotelService(SettlementItemHotelService<SettlementItemHotel> settlementItemHotelService) {
		this.settlementItemHotelService = settlementItemHotelService;
	}
	
}
