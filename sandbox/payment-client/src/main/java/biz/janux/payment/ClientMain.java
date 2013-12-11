package biz.janux.payment;

import java.text.SimpleDateFormat;

import org.janux.bus.search.SearchCriteria;
import org.json.JSONObject;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import biz.janux.payment.mock.*;


public class ClientMain {

	private static final int CARD_HOLDER_NAME_INDEX = 2;
	private static final int CARD_NUMBER_INDEX = 3;
	private static final int CARD_TYPE_INDEX = 4;
	private static final int CARD_EXPIRATION_DATE_INDEX = 5;
	private static final int UUID_INDEX = 2;
	private static final int REST_CALL_INDEX = 0;
	private static final int ACTION_NUMBER_INDEX = 1;
	private static PaymentGatewayRestClient paymentGatewayRestClient;
	private static CreditCardStorageService<SearchCriteria> creditCardRemotingService;
	private static ClassPathXmlApplicationContext ctx;
	private static CreditCardFactory creditCardFactory;

	public static void main(String[] args) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("MM/y");
		
		ctx = new ClassPathXmlApplicationContext("classpath:ApplicationContext.xml");
		creditCardFactory = (CreditCardFactory) ctx.getBean("CreditCardFactory");
		
		if (args.length<=REST_CALL_INDEX || args[REST_CALL_INDEX]==null){
	        System.out.println("Error- please type via option number (1:By Rest Call,2:By Remoting Service Call)");
	        return;
	    }
		
		if (args.length<=ACTION_NUMBER_INDEX || args[ACTION_NUMBER_INDEX]==null){
	        System.out.println("Error- please type action number (1:Save credit card,2:Load credit card,3:Delete credit card)");
	        return;
	    }
		
		int optionCall =  Integer.parseInt(args[0]);
		int optionAction = Integer.parseInt(args[1]);
		
		switch (optionCall) {
			case 1:{
				paymentGatewayRestClient = (PaymentGatewayRestClient) ctx.getBean("paymentGatewayRestClient");
				break;
			}
			case 2:{
				creditCardRemotingService = (CreditCardStorageService<SearchCriteria>) ctx.getBean("CreditCardRemotingService");
				break;
			}
			default:
				break;
		}
		
		
		switch (optionAction) {
			/**
			 * save a new credit card
			 */
			case 1:{
				if (args.length<=CARD_HOLDER_NAME_INDEX || args[CARD_HOLDER_NAME_INDEX]==null){
			        System.out.println("Error- please type card holder name (Ex. for test: "+CreditCardConstants.CARD_HOLDER_NAME+")");
			        return;
			    }
				if (args.length<=CARD_NUMBER_INDEX || args[CARD_NUMBER_INDEX]==null){
			        System.out.println("Error- please type credit card number (Ex. for test: "+CreditCardConstants.NUMBER_VISA+")");
			        return;
			    }
				if (args.length<=CARD_TYPE_INDEX || args[CARD_TYPE_INDEX]==null){
					System.out.println("Error- please type credit card type (Ex. for test: "+CreditCardConstants.TYPE_VISA_CC+")");
			        return;
			    }
				if (args.length<=CARD_EXPIRATION_DATE_INDEX || args[CARD_EXPIRATION_DATE_INDEX]==null){
					System.out.println("Error- please type credit card expiration date (Ex. for test: mm/yy "+CreditCardConstants.EXPIRATION+")");
			        return;
			    }
				String cardholderName=args[CARD_HOLDER_NAME_INDEX];
				String number=args[CARD_NUMBER_INDEX];
				String type=args[CARD_TYPE_INDEX];
				String expiration=args[CARD_EXPIRATION_DATE_INDEX];
				
				if (optionCall==1)
				{
					CreditCard creditCard = new CreditCardImpl();
					creditCard.setNumber(number);
					creditCard.setExpirationDate(formatter.parse(expiration));
					CreditCardType creditCardType = new CreditCardTypeImpl();
					creditCardType.setCode(type);
					creditCard.setType(creditCardType);
					creditCard.setCardholderName(cardholderName);
					
					CreditCard creditCardSaved = paymentGatewayRestClient.saveCreditCard(creditCard);
					System.out.println("the saved credit card: "+creditCardSaved);
				}
				else
				if (optionCall==2)
				{
					SimpleDateFormat sF = new SimpleDateFormat("MM/yy");
					CreditCard creditCard = getCreditCardFactory().getCreditCard(cardholderName, number,sF.parse(expiration),type,null);
					creditCard =  getCreditCardRemotingService().saveOrUpdate(creditCard);
					System.out.println("the saved credit card: "+creditCard.toString());
				}
				
				break;
			}
			/**
			 * load credit card
			 */
			case 2:{
				if (args.length<=UUID_INDEX || args[UUID_INDEX]==null){
			        System.out.println("Error- please type a UUID (Ex. for test: "+CreditCardConstants.UUID+")");
			        return;
			    }

				String uuidParam=args[UUID_INDEX];
				
				if (optionCall==1)
				{
					CreditCard creditCardSaved = getPaymentGatewayRestClient().loadCreditCard(uuidParam);
					System.out.println("loaded credit card:"+creditCardSaved);
				}
				else
				if (optionCall==2)
				{
					CreditCard creditCard = getCreditCardRemotingService().load(uuidParam,true);
					if (creditCard!=null)
						System.out.println("loaded credit card:"+creditCard.toString());
					else
						System.out.println("The credit card does not exist");
				}
				break;
			}
			/**
			 * delete credit card
			 */
			case 3:{
				if (args.length<=UUID_INDEX || args[UUID_INDEX]==null){
			        System.out.println("Error- please type a UUID (Ex. for test: "+CreditCardConstants.UUID+")");
			        return;
			    }

				String uuidParam=args[UUID_INDEX];
				boolean deleteOrDisable=false; 
				
				if (optionCall==1)
				{
					JSONObject jsonObject = getPaymentGatewayRestClient().deleteCreditCard(uuidParam);
					deleteOrDisable = jsonObject.getBoolean("deleteOrDisable");
				}
				else
				if (optionCall==2)
				{
					deleteOrDisable= getCreditCardRemotingService().deleteOrDisable(uuidParam);
				}

				if (deleteOrDisable)
					System.out.println("The credit card was removed");
				else
					System.out.println("The credit card was disabled");
				break;
			}
			default:
				break;
		} 
		

	}

	public static void setPaymentGatewayRestClient(PaymentGatewayRestClient paymentGatewayRestClient) {
		paymentGatewayRestClient = paymentGatewayRestClient;
	}


	public static PaymentGatewayRestClient getPaymentGatewayRestClient() {
		return paymentGatewayRestClient;
	}


	public static void setCreditCardRemotingService(CreditCardStorageService<SearchCriteria> creditCardRemotingService) {
		creditCardRemotingService = creditCardRemotingService;
	}


	public static CreditCardStorageService<SearchCriteria> getCreditCardRemotingService() {
		return creditCardRemotingService;
	}


	public static void setCreditCardFactory(CreditCardFactory creditCardFactory) {
		ClientMain.creditCardFactory = creditCardFactory;
	}


	public static CreditCardFactory getCreditCardFactory() {
		return creditCardFactory;
	}

}
