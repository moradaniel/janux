package biz.janux.commerce.payment.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.log4j.Logger;

import biz.janux.commerce.payment.interfaces.model.PaymentInstrument;
/**
 * @author Nilesh
 * </br>
 * Class for Credit Card implements PaymentInstrument
 */
public class CreditCard implements PaymentInstrument{
	
	private static final Logger logger = Logger.getLogger(CreditCard.class);
		  	 
		   private long creditCardId;
		   private String cardNumber; 	 
		   private String expirationDate;
		   private CreditCardType creditCardType;	 
		   private String holdersName; 		 
		   private String address1; 		 
		   private String address2; 			 
		   private String city; 				 
		   private String state ;				 
		   private String zip; 			 
		   private Country country ;		 
		   private String phoneNumber;		 
		   private String track1;			 	 
		   private String  track2; 
		   private String UUID;
		   private String  loggableInfo  ;
		   private  SecretKey m_key = null;
		   private  String m_keyStr = "V4XIg0nOZGQ=";
		   private  String m_keyAlgorithm = "DES";
		   private  boolean m_initialized = false;
		   private  String m_encryptionAlgorithm = "DES/ECB/PKCS5Padding";
		   
			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		    sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		    
		   private boolean encrypted = false;

	   /**
		 * @return String 
		 * Address1
		 * */ 
		public String getAddress1() {
			return address1;
		}
		/**
		 * @param String 
		 * Sets the Address1
		 */
		public void setAddress1(String address1) {
			this.address1 = address1;
		}

		/**
		 * @return String 
		 * Address2
		 * */ 
		public String getAddress2() {
			return address2;
		}
		/**
		 * @param String 
		 * Sets the Address2
		 */
		public void setAddress2(String address2) {
			this.address2 = address2;
		}

		/**
		 * @return String 
		 * Card Number
		 * */ 
		public String getCardNumber() {
			return cardNumber;
			
		}
		/**
		 * @param String 
		 * Sets the Card Number
		 */
		public void setCardNumber(String cardNumber) {
	 		this.cardNumber = cardNumber;
	 	}

		/**
		 * @return String 
		 * City
		 * */ 
		public String getCity() {
			return city;
		}
		/**
		 * @param String 
		 * Sets the City
		 */
		public void setCity(String city) {
			this.city = city;
		}

		/**
		 * @return Country 
		 * country 
		 * */ 
		public Country getCountry() {
			return country;
		}
		
		/**
		 * @param Country 
		 * Sets the country 
		 */
		public void setCountry(Country country) {
			this.country = country;
		} 
		
		
  
		/**
		 * @return String 
		 * Credit Card Id
		 * */ 
		public long getCreditCardId() {
			return creditCardId;
		}
		/**
		 * @param String 
		 * Sets the Credit Card Id
		 */
		public void setCreditCardId(long creditCardId) {
			this.creditCardId = creditCardId;
		}

		

		public CreditCardType getCreditCardType() {
			return creditCardType;
		}
		public void setCreditCardType(CreditCardType creditCardType) {
			this.creditCardType = creditCardType;
		}
		
		/**
		 * @return String 
		 * Expiration Date
		 * */ 
		public String getExpirationDate() {
			try {
				return decrypt(expirationDate); 
			} catch (Exception e) {
				logger.error("Failed to decrypt", e);

			 return null;
			}
		}
		
		/**
		 * @param String 
		 * Sets the Expiration Date
		 */
		 public void setExpirationDate(String expirationDate) {
			Date expDate = parseExpiration(expirationDate);
			String expirationClearText = dateToMMYY(expDate);
			if (expirationClearText == null)
				expirationClearText = "";
		    try {
				this.expirationDate = encrypt(expirationClearText);
			}
			catch (Exception e){
				logger.error("Failed to encrypt", e);
			}
		 }

		/**
		 * @return String 
		 * Holders Name
		 * */ 
		public String getHoldersName() {
			return holdersName;
		}
		/**
		 * @param String 
		 * Sets the Holders Name
		 */
		public void setHoldersName(String holdersName) {
			this.holdersName = holdersName;
		}

		/**
		 * @return String 
		 * Agent  Loggable Info
		 * */ 
		public String getLoggableInfo() {
			return loggableInfo;
		}
		/**
		 * @param String 
		 * Sets the Loggable Info
		 */
		public void setLoggableInfo(String loggableInfo) {
			this.loggableInfo = loggableInfo;
		}

		/**
		 * @return String 
		 * Phone Number
		 * */ 
		public String getPhoneNumber() {
			return phoneNumber;
		}
		/**
		 * @param String 
		 * Sets the Phone Number
		 */
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}

		/**
		 * @return String 
		 * State
		 * */ 
		public String getState() {
			return state;
		}
		/**
		 * @param String 
		 * Sets the State
		 */
		public void setState(String state) {
			this.state = state;
		}

		/**
		 * @return String 
		 * Track1
		 * */ 
		public String getTrack1() {

			if (track1 == null)
				return null;

			try {
				String decrypted = decrypt(track1);
				if (decrypted != null)
					decrypted = decrypted.trim();

				return decrypted;
			} catch (Exception e) {
				return null;
			}
		}
		/**
		 * @param String 
		 * Sets the Track1
		 */
		public void setTrack1(String track1) {
			if (track1 != null)
				track1 = track1.trim();

			try {
				this.track1 = encrypt(track1);
			} catch (Exception e) {
				return;
			}
		}


		/**
		 * @return String 
		 * Track2
		 * */ 
		public String getTrack2() {
			if (track2 == null)
				return null;

			try {
				String decrypted = decrypt(track2);
				if (decrypted != null)
					decrypted = decrypted.trim();

				return decrypted;
			} catch (Exception e) {
				return null;
			}
		}
		/**
		 * @param String 
		 * Sets the Track2
		 */
		public void setTrack2(String track2) {
			if (track2 != null)
				track2 = track2.trim();

			try {
				this.track2 = encrypt(track2);
			} catch (Exception e) {
				return;
			}
		}
		/**
		 * @return String 
		 * Zip
		 * */ 
		public String getZip() {
			return zip;
		}
		/**
		 * @param String 
		 * Sets the Zip
		 */
		public void setZip(String zip) {
			this.zip = zip;
		}
		
		public boolean isEncrypted() {
			return encrypted;
		}
		public void setEncrypted(boolean encrypted) {
			this.encrypted = encrypted;
		}
		
	/**
	 * start Code for Encrypting and Decrypting 
	 * 
	 */
		public boolean encryptCardNumber(){
			try {
				if(!encrypted){
					this.cardNumber = this.encrypt(this.cardNumber);
					this.encrypted = true;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			return true;
		}		
		
		public boolean decryptCardNumber(){
			try {
				if(encrypted){
					this.cardNumber = this.decrypt(this.cardNumber);
					this.encrypted = false;
				}			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}			
			return true;
		}
		

		private  void init() throws Exception {
				if (m_initialized)
					return; 
				SecretKeyFactory skf = SecretKeyFactory.getInstance(m_keyAlgorithm);
				DESKeySpec desKS = new DESKeySpec(decoder.decodeBuffer(m_keyStr));
				m_key = skf.generateSecret(desKS);
				m_initialized = true;
		}
		 
		public String encrypt(String _str) throws Exception {
				String encryptedStr = "";
				if(_str == null || _str.trim().length() == 0) {
					return encryptedStr;
				}
				try{
					init();
					Cipher c = Cipher.getInstance(m_encryptionAlgorithm);
					c.init(Cipher.ENCRYPT_MODE, m_key);
					encryptedStr = encoder.encode(c.doFinal(_str.getBytes()));
				}catch(Exception e){
				System.out.println("Failed to encrypt" + e);
				}
				return encryptedStr;
		}

		public synchronized  String decrypt(String _str) throws Exception {
				if (_str == null || _str.trim().length() == 0){
					return _str;
					}
					String decryptedStr = "";
			
					try{
						init();
						Cipher c = Cipher.getInstance(m_encryptionAlgorithm);
						c.init(Cipher.DECRYPT_MODE, m_key);
						decryptedStr = new String(c.doFinal(decoder.decodeBuffer(_str)));
					}catch (Exception e){
						System.out.println("Failed to decrypt" + e);
					}
			
					return decryptedStr;
		}
		  /**
		  *
		  * @param exp String representing CC expiration date
		  * @return Date representing expiration, or null
		  */
		 public static Date parseExpiration(String exp)
		 {
				if (logger.isDebugEnabled()) {
					logger.debug("parseExpiration(exp=" + exp + ")");
				}

		 	// Sanity check
		     if (exp == null || exp.length() < 4 || exp.length() > 7)
		         return null;
		     // Expected to be in:
		     // MMyy (0405)
		     // MM/yy (04/05, 4/05)
		     // MM/yyyy (04/2004, 4/2004)
		     // Leading 0 is unnecessary if there is a '/' delimiter

		     // First check for MMyy
				Date expirationDate = parseDateString(exp, "MMyy");
				if(expirationDate == null)
				{
					// This catches both 2 digit and 4 digit years
					expirationDate = parseDateString(exp, "MM/yy");
				}

				// If expiration is still null, we will return that.

		     return expirationDate;
		 }
		  
		 /**
		  * Parses a String into a java.util.Date with the given pattern,
		  * or returns null if parsing fails.
		  *
		  * @param strDate the textual String that is supposed to represent a date
		  * @param pattern the java date pattern to parse against
		  * @return Date the date, or null if not able to parse
		  */
			public static Date parseDateString(String strDate, String pattern)
			{
				try
				{
					SimpleDateFormat sdf = new SimpleDateFormat(pattern);

					return sdf.parse(strDate);
				}
				catch(ParseException e)
				{
					return null;
				}
			}
			 /**
		    *
		    * @param exp Expiration date entered
		    * @return MMYY String representing exp
		    */
		   public static String dateToMMYY(Date exp)
		   {
				if (logger.isDebugEnabled()) {
					logger.debug("dateToMMYY(exp=" + exp + ")");
				}

		       if (exp == null)
		           return null;

		       // Get departure's date with day = 1st
		       GregorianCalendar cal = new GregorianCalendar();
		       cal.setTime(exp);

		       // Returns 0-11, 0 == January
		       int month = cal.get(Calendar.MONTH) + 1;
		       // Returns full year, i.e. 2004 returns (int)2004
		       int year = cal.get(Calendar.YEAR) - 2000;

		       StringBuffer buf = new StringBuffer(4);
		       // Month
		       buf.append((month < 10 ? "0" + month : Integer.toString(month)));
		       // Year
		       buf.append((year < 10 ? "0" + year : Integer.toString(year)));

		       return buf.toString();
		   }
		   /**
		    *  end expirationDate representing
		    */

		/**
		 * Checks to see if we have "swipe" information for a credit card. This is
		 * going to be used instead of the creditCard.swiped column because we have
		 * to keep track of both the swiped data, and if it was swiped. It's easier
		 * to just record the swipe information and make a (simple?) judgement call
		 * 
		 * JJM TODO: THIS IS HARD CODED TO USE TRACK2. IF WE EVER CHANGE BACK TO
		 * USING TRACK1, OR MAKE IT OPTIONAL WHICH TRACK IS SENT, THIS NEEDS TO BE
		 * REVISITED.
		 * 
		 * @return true if this object contains proper swiped data
		 */
		public boolean isSwiped() {
		
			// Call static helper method
			return isSwiped(getTrack2());
		}
		
		/**
		 * Checks to see if we have "swipe" information for a credit card. This is
		 * going to be used instead of the creditCard.swiped column because we have
		 * to keep track of both the swiped data, and if it was swiped. It's easier
		 * to just record the swipe information and make a (simple?) judgement call
		 * 
		 * JJM TODO: THIS IS HARD CODED TO USE TRACK2. IF WE EVER CHANGE BACK TO
		 * USING TRACK1, OR MAKE IT OPTIONAL WHICH TRACK IS SENT, THIS NEEDS TO BE
		 * REVISITED.
		 * 
		 * @param track2
		 *            the track2 data from the credit card.
		 * @return true if we want to use the argument as valid "swiped" data
		 */
		public static boolean isSwiped(String str) {

			// Sanity check
			if (str == null || str.trim().equals(""))
				return false;

			// Str is not null
			// Found a case where we received a new line before the initial ';'
			// NOTE: The setTrack?/getTrack? should now be trimmed, but just in
			// case...
			str = str.trim();

			// JJM TODO: Do we need to futher validate the field?
			return (str.startsWith(";") && str.endsWith("?"));
		}
		
		/**
		 * @return String UUID
		 * */
		public String getUUID() {
			return UUID;
		}
		
		/**
		 * @param String 
		 * Sets the UUID
		 */
		public void setUUID(String uuid) {
			UUID = uuid;
		}
}
		
