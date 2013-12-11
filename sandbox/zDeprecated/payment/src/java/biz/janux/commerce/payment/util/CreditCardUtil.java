package biz.janux.commerce.payment.util;

import java.io.Serializable;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
/**
 * 
 * @author Nilesh
 * Util class for credit card implements Serializable
 *
 */
public class CreditCardUtil implements Serializable {

		private static final long serialVersionUID = 5809109931272495024L;
		public static final int UNKNOWN = 1;
	    public static final int AMERICAN_EXPRESS = 3;
	    public static final int DINERS_CLUB = 5;
	    public static final int DISCOVER = 6;
	    public static final int JAPAN_CREDIT_BUREAU = 9;
	    public static final int MASTERCARD = 10;
	    public static final int VISA = 12;
	    private  SecretKey m_key = null;
        private  String m_keyStr = "V4XIg0nOZGQ=";
        private  String m_keyAlgorithm = "DES";
        private  boolean m_initialized = false;
        private  String m_encryptionAlgorithm = "DES/ECB/PKCS5Padding";

        sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
	    sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
	    /**
	     * Method to get Credit Card Type
	     * @param number
	     * @return int as Credit Card Type
	     */
	 public static int getCreditCardType(String number) {
//        number = parseCreditCardNumber(number);
        int length = number.length();
        // Minimum length should be 13 (Visa), Maximum length 16 (most cards
        // use
        // 16)
        if (length < 13 || length > 16)
            return UNKNOWN;

        // BIN being the first 6 digits
        String sBin = number.substring(0, 6);
        int bin = Integer.parseInt(sBin);

        // VISA Range 400000 - 4999999 length 13 or 16
        if ((bin >= 400000 && bin <= 499999) && (length == 13 || length == 16)) {
            return VISA;
        }
        // MASTERCARD Range 510000 - 559999 length 16
        else if ((bin >= 510000 && bin <= 559999) && length == 16) {
            return MASTERCARD;
        }
        // AMEX Range 340000 - 349999 OR 370000 - 379999 length 15
        else if ((bin >= 340000 && bin <= 349999) && length == 15) {
            return AMERICAN_EXPRESS;
        } else if ((bin >= 370000 && bin <= 379999) && length == 15) {
            return AMERICAN_EXPRESS;
        }
        // NOVUS/DISCOVER Range 601100 - 601199 length 16
        else if ((bin >= 601100 && bin <= 601199) && length == 16) {
            return DISCOVER;
        }
        // DINERS CLUB Range 300000 - 305999 OR 360000 - 369999 OR 380000 -
        // 389999
        // length 14 OR 16
        else if ((bin >= 300000 && bin <= 305999)
                && (length == 14 || length == 16)) {
            return DINERS_CLUB;
        } else if ((bin >= 300000 && bin <= 305999)
                && (length == 14 || length == 16)) {
            return DINERS_CLUB;
        } else if ((bin >= 360000 && bin <= 369999)
                && (length == 14 || length == 16)) {
            return DINERS_CLUB;
        } else if ((bin >= 380000 && bin <= 389999)
                && (length == 14 || length == 16)) {
            return DINERS_CLUB;
        }
        // JAPAN CREDIT BUREAU Range 352800 - 358999 length 16
        else if ((bin >= 352800 && bin <= 358999) && length == 16) {
            return JAPAN_CREDIT_BUREAU;
        }
        return UNKNOWN;
    }

    /**
     * Returns credit card number with no field seperators (' ', '-', etc)
     * 
     * @param number
     *            The unparsed credit card number (5105-1051 0510*5100)
     * @return credit card number with no field seperators (5105105105105100)
     */
    public static String parseCreditCardNumber(String number) {

        StringBuffer buf = new StringBuffer(16);
        for (int i = 0; i < number.length(); i++) {
            char ch = number.charAt(i);
            // todo: This may pick up non 0-9 digits
            if (Character.isDigit(ch)) {
                buf.append(ch);
            }
        }
        return buf.toString();
    }
    /**
     * Method to get masked credit card number (***********3456) 
     * @param num
     * @return string 
     */
    public static String getMaskedNumber(String num) {
		// TODO: add TYPE to String
		// sanity check
		if (num.length() < 4) {
			return "";
		}
		// hash out number
		String lastDigits = num.substring(num.length() - 4);
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < num.length() - 4; i++) {
			buf.append("*");
		}
		buf.append(lastDigits);
		return buf.toString();
	}
    /**
     * 
     * @throws Exception
     */
    private  void init() throws Exception {
		if (m_initialized)
			return; 
		SecretKeyFactory skf = SecretKeyFactory.getInstance(m_keyAlgorithm);
		DESKeySpec desKS = new DESKeySpec(decoder.decodeBuffer(m_keyStr));
		m_key = skf.generateSecret(desKS);
		m_initialized = true;
    }
    /**
     * Method to Encrypt String
     * @param _str
     * @return encrypted String
     * @throws Exception
     */
    public String encrypt(String _str) throws Exception {
		String encryptedStr = "";
		if (_str == null || _str.trim().length() == 0) {
			return encryptedStr;
		}
		try {
			init();
			Cipher c = Cipher.getInstance(m_encryptionAlgorithm);
			c.init(Cipher.ENCRYPT_MODE, m_key);
			encryptedStr = encoder.encode(c.doFinal(_str.getBytes()));
		}
		catch (Exception e){
		System.out.println("Failed to encrypt" + e);
		}
		return encryptedStr;
    }
    /**
     * Method to Decrypt String 
     * @param _str
     * @return Decrypted String
     * @throws Exception
     */
    public synchronized  String decrypt(String _str) throws Exception {
		if (_str == null || _str.trim().length() == 0){
			return _str;
			}
			String decryptedStr = "";
			try {
				init();
				Cipher c = Cipher.getInstance(m_encryptionAlgorithm);
				c.init(Cipher.DECRYPT_MODE, m_key);
				decryptedStr = new String(c.doFinal(decoder.decodeBuffer(_str)));
			}
			catch (Exception e){
				System.out.println("Failed to decrypt" + e);
			}
			return decryptedStr;
    	}
   }