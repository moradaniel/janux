package biz.janux.commerce.payment.Settlement;

import java.math.BigDecimal;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import biz.janux.commerce.payment.implementation.common.dao.hibernate.HibernateDao;
import biz.janux.commerce.payment.implementation.vendor.vital.TransactionServiceImpl;
import biz.janux.commerce.payment.implementation.vendor.vital.response.VitalSettlementResponse;
import biz.janux.commerce.payment.model.SettlementModel;
import biz.janux.commerce.payment.model.VitalAuthorizationResponseModel;
import junit.framework.TestCase;

public class TestCaseSettlement extends TestCase{
	
	public void testSettlement(){
		
		
		HibernateDao hibernateDao = HibernateDao.getInstance();
		VitalAuthorizationResponseModel vitalAuthorizationResponseModel = (VitalAuthorizationResponseModel )hibernateDao.loadfromDB(VitalAuthorizationResponseModel.class,2L);	

		SettlementModel settlement = new SettlementModel(vitalAuthorizationResponseModel,new BigDecimal(383.2));
		
		
		TransactionServiceImpl tsi=new TransactionServiceImpl();
		VitalSettlementResponse vitalSettlementResponse = (VitalSettlementResponse) tsi.process(settlement);
//		System.out.println("resposne ::"+vitalSettlementResponse.getBatchNumber());
		
		assertNotNull(vitalSettlementResponse);
		assertTrue(vitalSettlementResponse.isApproved());
		assertFalse(vitalSettlementResponse.isDeclined());
	}
	
}
