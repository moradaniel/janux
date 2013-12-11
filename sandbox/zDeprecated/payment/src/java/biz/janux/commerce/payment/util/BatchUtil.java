package biz.janux.commerce.payment.util;

import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import biz.janux.commerce.payment.implementation.common.dao.hibernate.HibernateDao;
import biz.janux.commerce.payment.model.BatchNumber;
import biz.janux.commerce.payment.model.TransectionSeqNum;
import biz.janux.commerce.payment.model.VitalMerchantAccount;
/**
 * @author Nilesh
 * 
 * A util Class for all Batch Related Operations.
 * 
 * */
public class BatchUtil {
	static Logger logger = Logger.getLogger(BatchUtil.class);
	/**
	 * Get the next batch number for the hotel.
	 * 
	 * @param hotelId the hotel
	 * @return the next batch number
	 */
	public static synchronized int getBatchNumber(long hotelId){
		
		if (logger.isDebugEnabled()) {
			logger.debug("generateBatchNumber(hotel=" + hotelId + ")");
		}
		BatchNumber batchnumbere=new BatchNumber();
		int bchnum=1;
		try{
			HibernateDao hibernateDao = HibernateDao.getInstance();
			Criteria crit = hibernateDao.getCriteria(BatchNumber.class);
			List insurances = crit.list();
			for(Iterator it = insurances.iterator() ; it.hasNext() ;){
			batchnumbere = (BatchNumber) it.next();
			if((int)hotelId == batchnumbere.getHotelId()){
				bchnum=batchnumbere.getNumber();
				bchnum++;
				batchnumbere.setNumber(bchnum);
				hibernateDao.save(batchnumbere);
			break;
			}
		}
		if(bchnum == 1){
			bchnum++;
			batchnumbere.setNumber(bchnum);
			batchnumbere.setHotelId((int)hotelId);
			hibernateDao.save(batchnumbere);
		}
	} catch (Exception e) {
		System.out.println(e.getMessage());
	}	
	return bchnum++;
	}
	/**
	 * Get the next transaction sequence number for the hotel.
	 * @param hotelId the hotel
	 * @return the next transaction Sequence number
	 */
	public synchronized static String transactionSequenceNumber(long hotelId)
	{
		if (logger.isDebugEnabled()) {
			logger.debug("generateTransactionSequenceNumber(hotel=" + hotelId + ")");
		}
		TransectionSeqNum transectionSeqNum=null;
		int transnum =1;
		HibernateDao hibernateDao = HibernateDao.getInstance();
		VitalMerchantAccount merchent= (VitalMerchantAccount)hibernateDao.loadfromDB(VitalMerchantAccount.class , hotelId);
		Criteria crit = hibernateDao.getCriteria(TransectionSeqNum.class);
		List list = crit.list();
		for(Iterator it = list.iterator() ; it.hasNext() ;){
			transectionSeqNum = (TransectionSeqNum) it.next(); 
			if((int)hotelId == transectionSeqNum.getMerchentId()){
				transnum=transectionSeqNum.getTransnum();
				transnum++;
				transectionSeqNum.setTransnum(transnum);
				hibernateDao.save(transectionSeqNum);
				break;
			}
		}
		if(transnum == 1){
			transectionSeqNum = new TransectionSeqNum();
			transectionSeqNum.setMerchentId((int)hotelId);
			transnum++;
			transectionSeqNum.setTransnum(transnum);
			hibernateDao.save(transectionSeqNum);
		}
		return String.valueOf(transnum);
	}
}
