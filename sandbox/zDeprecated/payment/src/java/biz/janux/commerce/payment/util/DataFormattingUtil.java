package biz.janux.commerce.payment.util;

import java.math.BigDecimal;
//import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.log4j.Logger;
/**
 * @author Nilesh.
 * Util for Date formating.
 */
public class DataFormattingUtil {
	static Logger logger = Logger.getLogger(DataFormattingUtil.class);

    public static byte calculateLRC(byte[] array) {
        byte lrc = array[1];
        for (int i = 2; i < array.length; i++) {
            lrc ^= array[i];
        }
        return lrc;
    }
    public static byte setEvenParity(byte b) {
        int bitcount = 0;
        for (int i = 0; i < 8; i++) {
            if (((b >> i) & 0x01) == 1) {
                bitcount++;
            }
        }
        if (bitcount % 2 == 0) {
            return b;
        }
        return (byte) (b | 0x80);
    }

    public static void setEvenParityBytes(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = setEvenParity(bytes[i]);
        }
    }
    public static String pad(boolean left, char pad_char, String str, int length) {
        int str_length = str.length();
        if (str_length > length) {
            // Throw Exception, Log Warning?
            str = str.substring(0, length);
            str_length = length;
        }
        char[] pad_str = new char[length - str_length];
        for (int i = 0; i < pad_str.length; i++) {
            pad_str[i] = pad_char;
        }
        StringBuffer buffer = new StringBuffer(length);
        if (left) {
            buffer.append(str);
            buffer.append(pad_str);
        } else {
            buffer.append(pad_str);
            buffer.append(str);
        }
        return buffer.toString();
    }
    
    public static String leftJustifySpace(String str, int length) {
        return pad(true, ' ', str, length);
    }

    public static String leftJustifyZero(String str, int length) {
        return pad(true, '0', str, length);
    }

    public static String rightJustifySpace(String str, int length) {
        return pad(false, ' ', str, length);
    }

    public static String rightJustifyZero(String str, int length) {
        return pad(false, '0', str, length);
    }

    public static char stripMSB(byte b) {
        return (char) ((byte) b & 0x7F);
    }

    public static void removeParityBits(byte[] b) {
        for (int i = 0; i < b.length; i++) {
            b[i] = (byte) stripMSB(b[i]);
        }
    }
    

    //	Converts phone number to XXX-YYYYYYY format
    public static String convertToVitalPhoneFormat(String str) {
        StringBuffer buf = new StringBuffer();
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i);
                if (Character.isDigit(ch)) {
                    buf.append(ch);
                    if (buf.length() == 3)
                        buf.append('-');
                }
            }
        }
        // If string buffer isn't 11 characters long, return a bogus formatted
        // number
        if (buf.length() != 11) {
            return "999-9999999";
        }
        return buf.toString();
    }
    
    /*
     * This method should be always used to format the amount to get rid of any
     * decimals
     */
    public static String formatAmount(BigDecimal amount) {
        amount = amount.setScale(2, BigDecimal.ROUND_UP).movePointRight(2);
        return String.valueOf(amount.intValue());
    }
    public static String getTrack1(String str) {
        return str.substring(1, str.length() - 1);
    }

    public static String getTrack2(String str) {
        return str.substring(1, str.length() - 1);
    }
  
    public static byte calcLRC(byte[] array) {
	    byte lrc = array[1];
        for (int i = 2; i < array.length; i++) {
            lrc ^= array[i];            
        }
        return lrc;
    }
    
    // strips all parity bits from a byte array
  public static void fixBytes(byte[] b) {
		 
       for (int i = 0; i < b.length; i++)
          b[i] = (byte) stripMSB(b[i]);
  }
  public static String formatDateToYYMMDD(Date date) {
      return new SimpleDateFormat("yyMMdd").format(date);
  }

  public static String formatDateToMMDDYY(Date date) {
      return new SimpleDateFormat("MMddyy").format(date);
  }
  public static String formatDateToMMDD(Date date) {
      return new SimpleDateFormat("MMdd").format(date);
  }
  public static String formatTimestampToHHMMSS(java.sql.Timestamp ts) {
      return new SimpleDateFormat("HHmmss").format(new java.util.Date(ts
              .getTime()));
  }
  
  /**
	 * Returns the local time of a GMT date/time using given TimeZone
	 * 
	 * @param tz
	 *            Local time zone we are requesting
	 * @param date
	 *            The GMT date/time to convert
	 * @return the local date/time
	 */
	public static java.sql.Date getLocalDate(TimeZone tz, java.util.Date date) {
		if (logger.isDebugEnabled()) {
			logger.debug("getLocalDate(tz=" + tz + ", date=" + date + ")");
		}
		GregorianCalendar now = new GregorianCalendar();
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		TimeZone currentTZ = TimeZone.getDefault();
		long currentTZOffsetFromGMT = currentTZ.getOffset(
				now.get(Calendar.ERA), now.get(Calendar.YEAR), now
						.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH),
				now.get(Calendar.DAY_OF_WEEK), now.get(Calendar.MILLISECOND));
		long offsetFromGMT = tz.getOffset(cal.get(Calendar.ERA), cal
				.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal
				.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.DAY_OF_WEEK), cal
				.get(Calendar.MILLISECOND));
		// I think this is the wrong value
		// should be # milliseconds in current day
		java.sql.Date x = new java.sql.Date(date.getTime()
				+ (offsetFromGMT - currentTZOffsetFromGMT));
		return x;
	}
} 