package biz.janux.payment;

import static junit.framework.Assert.assertEquals;
import junit.framework.Assert;

import org.janux.bus.search.SearchCriteria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.trg.search.Search;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:ApplicationContext.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
public class MerchantAccountServiceTest {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	MerchantAccountService<SearchCriteria> merchantAccountService;
	
	@Autowired
	MerchantAccountFactory merchantAccountFactory;
	
	@Test
	public void crudMerchantAccount() throws Exception{
		String uuid = null;
		try {
			uuid = createMerchantAccount();
			findMerchantAccount(uuid);
			deleteMerchantAccount(uuid);
			findDeletedMerchantAccount(uuid);
		} catch (RuntimeException e) {
			throw e;
		}
		finally
		{
			/**
			 * clean the database
			 */
			getMerchantAccountService().delete(uuid);
		}
	}

	
	public String createMerchantAccount() throws Exception{
		MerchantAccount merchant = getMerchantAccountFactory().getMerchantAccount();
		MerchantAccount merchantAccount = getMerchantAccountService().save(merchant);
		return merchantAccount.getUuid();
	}

	public void findMerchantAccount(String uuid) throws Exception{
		Search searchCriteria = new Search();
		searchCriteria.addFilterEqual("uuid",uuid);
		
		MerchantAccount merchantAccount = getMerchantAccountService().find(searchCriteria);
		
		Assert.assertNotNull(merchantAccount);
		assertEquals(MerchantAccountConstants.NAME,merchantAccount.getName());
		assertEquals(MerchantAccountConstants.NUMBER,merchantAccount.getNumber());
		assertEquals(MerchantAccountConstants.STORE_NUM,merchantAccount.getStoreNum());
		assertEquals(MerchantAccountConstants.TERMINAL_NUM,merchantAccount.getTerminalNum());
		assertEquals(MerchantAccountConstants.ACQUIRING_BANK_BIN,merchantAccount.getAcquiringBankBin());
		assertEquals(uuid,merchantAccount.getUuid());
		assertEquals(MerchantAccountConstants.CITY_CODE,merchantAccount.getMerchantAddress().getPostalCode());
		assertEquals(MerchantAccountConstants.MERCHANT_STATE,merchantAccount.getMerchantAddress().getStateProvinceAsString());
	}

	public void deleteMerchantAccount(String uuid) throws Exception{
		
		Search searchCriteria = new Search();
		searchCriteria.addFilterEqual("uuid",uuid);
		
		MerchantAccount merchantAccount = getMerchantAccountService().find(searchCriteria);
		
		getMerchantAccountService().delete(merchantAccount);
	}
	

	public void findDeletedMerchantAccount(String uuid) throws Exception{
		Search searchCriteria = new Search();
		searchCriteria.addFilterEqual("uuid",uuid);
		
		MerchantAccount merchantAccount = getMerchantAccountService().find(searchCriteria);
		Assert.assertNull(merchantAccount);	
	}

	public MerchantAccountService<SearchCriteria> getMerchantAccountService() {
		return merchantAccountService;
	}

	public void setMerchantAccountService(
			MerchantAccountService<SearchCriteria> merchantAccountService) {
		this.merchantAccountService = merchantAccountService;
	}


	public MerchantAccountFactory getMerchantAccountFactory() {
		return merchantAccountFactory;
	}


	public void setMerchantAccountFactory(
			MerchantAccountFactory merchantAccountFactory) {
		this.merchantAccountFactory = merchantAccountFactory;
	}

}


