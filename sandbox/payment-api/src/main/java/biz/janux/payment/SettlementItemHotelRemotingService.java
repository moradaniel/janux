package biz.janux.payment;

public interface SettlementItemHotelRemotingService extends RemotingService{

	public SettlementItemHotel find(String uuid);
	
	public SettlementItemHotel delete(String uuid);
	
}
