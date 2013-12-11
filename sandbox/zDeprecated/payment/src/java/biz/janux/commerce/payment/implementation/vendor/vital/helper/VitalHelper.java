package biz.janux.commerce.payment.implementation.vendor.vital.helper;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.TimeZone;

//import javax.mail.Message;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
 
import biz.janux.commerce.payment.util.Constants;
import biz.janux.commerce.payment.util.DataFormattingUtil;

import HTTPClient.HTTPConnection;
import HTTPClient.HTTPResponse;
import HTTPClient.NVPair;
  /**
   * 
   * @author Nilesh
   *
   */
public class VitalHelper {
 
	    static Logger logger = Logger.getLogger(VitalHelper.class);
	    
	    public static final String SMTP_HOST_KEY = "mail.smtp.host";
	    public static final boolean JAVAMAIL_DEBUG = false;
	    public static final String SMTP_SERVER = "localhost";
	    public static final String AUTHORIZATION_CONTENT_TYPE = "x-Visa-II/x-auth";
	    public static final String SETTLEMENT_CONTENT_TYPE = "x-Visa-II/x-settle";
	    public static final String ERROR_CONTENT_TYPE_RESPONSE = "x-data/xact-error";
	    public static final String CONTENT_TYPE_HEADER = "Content-Type";
	    public static final String VITAL_GATEWAY_URL = "/scripts/gateway.dll?Transact";
	    public static final String VITAL_GATEWAY_HOST = "ssl.pgs.wcom.net";
	    public static final String VITAL_GATEWAY_PROTOCOL = "https";
	    public static final int VITAL_GATEWAY_PORT = 443;
	    private byte[] resBytes;
	    /**
	     * Method to send Transaction for Authorization
	     * @param message
	     * @return byte[]
	     */
	    public byte[] sendTransaction(byte[] message){
	    	return resBytes = processAuthorization(message);
	     }
	    /*	
	     *   @return byte[]
	     *   Method to Process Authorization  and Address Verification for credit crad
	     */
	    public byte[] processAuthorization(byte[] message){
	    	
	    	if (logger.isDebugEnabled()) {
				logger.debug("processAuthorization(message=" + message + ")");
			}
	     	try {
	            HTTPConnection con = new HTTPConnection(VITAL_GATEWAY_PROTOCOL,
	                    VITAL_GATEWAY_HOST, VITAL_GATEWAY_PORT);

	            NVPair[] nvPair = new NVPair[1];
	            nvPair[0] = new NVPair(CONTENT_TYPE_HEADER,
	                    AUTHORIZATION_CONTENT_TYPE);
                
                  // Do parity flag stuff
	             DataFormattingUtil.setEvenParityBytes(message);
 	            // Frame STX/ETX?
 	            // Add LRC
	            byte[] bytemsgArray = new byte[message.length + 1];
	            
	            System.arraycopy(message, 0, bytemsgArray, 0, message.length);
	            bytemsgArray[bytemsgArray.length - 1] = DataFormattingUtil.calcLRC(message);

	            HTTPResponse rsp = con.Post(VITAL_GATEWAY_URL, bytemsgArray, nvPair);

	            String contentTypeHeader = rsp.getHeader(CONTENT_TYPE_HEADER);
	            
	            if (contentTypeHeader.equals(ERROR_CONTENT_TYPE_RESPONSE)) {
//	                throw new VitalCommunicationException(new String(rsp.getData())
//	                        .trim());
	            } else // contentTypeHeader == "x-data/xact-response"
	            {
	            	resBytes =  rsp.getData() ;
	                          
	            }
	        } catch (Throwable throwable) {
	        	
	        	notifyPaymentProcessingError(throwable, "Authorization");
	        }	    	
	       return resBytes;
	     }//End processAuthorization function
	    /**
	     * Method to send Transaction for Settlement
	     * @param message
	     * @return byte[]
	     */
	    public byte[] sendTransactionSettlement(byte[] message) {
	    	
	    	return resBytes = processSettlement(message);
	     }
		  /**
		   * @param message
		   * @return byte[]
		   * Method to Process Settlement for credit card
		   */
		 	public byte[] processSettlement(
					 byte[] message) /*throws VitalCommunicationException*/ {
				
			 if (logger.isDebugEnabled()) {
					logger.debug("processSettlement(message=" + message + ")");
				}
			 
	         try {
		            HTTPConnection con = new HTTPConnection(VITAL_GATEWAY_PROTOCOL,
		                    VITAL_GATEWAY_HOST, VITAL_GATEWAY_PORT);
	 	            NVPair[] nvPair = new NVPair[1];
		            nvPair[0] = new NVPair(CONTENT_TYPE_HEADER, SETTLEMENT_CONTENT_TYPE);
	       
		            if (logger.isDebugEnabled()) {
		                logger.debug("OUTGOING SETTLEMENT STRING:");
		                logger.debug(new String(message));
		            }

		            // Do parity flag stuff
		            DataFormattingUtil.setEvenParityBytes(message);

		            // Frame STX/ETX?

		            HTTPResponse rsp = con.Post(VITAL_GATEWAY_URL, message, nvPair);
		            
		            String contentTypeHeader = rsp.getHeader(CONTENT_TYPE_HEADER);
		            
		            if (contentTypeHeader.equals(ERROR_CONTENT_TYPE_RESPONSE)) {
//		                throw new VitalCommunicationException(new String(rsp.getData())
//		                        .trim());
		            }
		            else // contentTypeHeader == "x-data/xact-response"
		            {
		             	 resBytes = rsp.getData();
		                 DataFormattingUtil.fixBytes(resBytes);
		                 logger.debug("SETTLEMENT RESPONSE:");
		                 logger.debug(new String(resBytes));
		            }
		        } catch (Throwable throwable) {
		        	
		        	   notifyPaymentProcessingError(throwable, "Settlement");
		        }
	 	        return resBytes;
		    } //*End processsettlement function
	    
	     private static void notifyPaymentProcessingError(
			
	                 Throwable throwable, String source) {

	    	logger.error("Payment processing is failed for source " + source + " error " + throwable.toString());
			
	    	ByteArrayOutputStream baos = new ByteArrayOutputStream();

			PrintStream out = new PrintStream(baos);
			
			throwable.printStackTrace(out);
			
			String errorMsg = new String(baos.toByteArray());
			
//			String subject = source + " is failed";
			
			out.close();

//			try {
////					 send(Constants.PAYMENT_PROCESSING_ERROR_TO, 
////							Constants.PAYMENT_PROCESSING_ERROR_FROM, 
////					subject, errorMsg, "text/plain", true);
//			}
//			finally {
//				out.close();
//			}
			
		}
	     
	  	 public static void send(String to, String from, String subject,
	            String body, String contentType, boolean bSendToOtherAddresses) {
	        send(to, from, subject, body, contentType, bSendToOtherAddresses, null,
	                null);
	    }
	 public static void send(String to, String from, String subject,
	            String body, String contentType, boolean bSendToOtherAddresses,
	            String ccAddress, String bccAddress) {
	        if (logger.isDebugEnabled()) {
	            logger.debug("send(to=" + to + ", from=" + from + ", subject="
	                    + subject + ", body=" + body + ", contentType="
	                    + contentType + ", bSendToOtherAddresses="
	                    + bSendToOtherAddresses + ", ccAddress=" + ccAddress
	                    + ", bccAddress=" + bccAddress + ")");
	        }

	        /*
	         try {
	            // Define SMTP server. If no system property exists, use
	            // constant SMTP_SERVER
	            Properties props = System.getProperties();
	            if (props.get(SMTP_HOST_KEY) == null) {
	                props.put(SMTP_HOST_KEY, SMTP_SERVER);
	            }

	            Session session = Session.getInstance(props, null);
	            session.setDebug(JAVAMAIL_DEBUG);

	            Message msg = new MimeMessage(session);

	            // From: from@from.com
	            msg.setFrom(new InternetAddress(from));

	            // To: user@user.com
	            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(
	                    to, false));

	            if (ccAddress != null) {
	                msg.setRecipients(Message.RecipientType.CC, InternetAddress
	                        .parse(ccAddress, false));
	            }

	            if (bccAddress != null) {
	                msg.setRecipients(Message.RecipientType.BCC, InternetAddress
	                        .parse(bccAddress, false));
	            }

	            // TODO: Verify if you can setRecipients() twice and still have it
	            // work...
	            if (bSendToOtherAddresses) {
	                InternetAddress[] bcc = new InternetAddress[2];
	                bcc[0] = new InternetAddress("error@myhms.com");

	                // Note: Usage in BC Reservation notifications is
	                // to To: bc@amansi.com, From: bc@amansi.com, so this
	                // might BCC the From which is the same as the TO, however
	                // I think sendmail is smart enough to handle this.

	                // JJM TODO: Verify this is required for MyRES notification to
	                // properties.
	                bcc[1] = new InternetAddress(from);

	                // Bcc: Josh, Evan, Dianne, Dichen, and hotel property
	                msg.setRecipients(Message.RecipientType.BCC, bcc);
	            }

	            // Subject: subject
	            msg.setSubject(subject);

	            // Body (from letter)
	            msg.setContent(body, contentType);

	            msg.setSentDate(new java.util.Date());

	            Transport.send(msg);
	        } catch (Exception e) {
	            logger.error("ERROR SENDING EMAIL: " + e);
	            e.printStackTrace();
	        }*/
	    }
		 
	  // strips all parity bits from a byte array
	    public static void fixBytes(byte[] b) {
			if (logger.isDebugEnabled()) {
				logger.debug("fixBytes(b=" + b + ")");
			}

	        for (int i = 0; i < b.length; i++)
	            b[i] = (byte) stripMSB(b[i]);
	    }

	   /*
	    * strips parity bit from byte
	    */	
	    public static char stripMSB(byte b) {
 			if (logger.isDebugEnabled()) {
 				logger.debug("stripMSB(b=" + b + ")");
 			}

	        return (char) ((byte) b & 0x7F);
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

	    public static String pad(boolean left, char pad_char, String str, int length) {
			if (logger.isDebugEnabled()) {
				logger.debug("pad(left=" + left + ", pad_char=" + pad_char
						+ ", str=" + str + ", length=" + length + ")");
			}

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

	   

		/************************************/
	    public static String timeZoneToVitalTZStr(TimeZone tz) {
	        StringBuffer strBuf = new StringBuffer(3);

	        boolean bObserveDST = tz.useDaylightTime();

	        int offset = tz.getRawOffset();
	        int hours = (offset / (1000 * 60 * 60));

	        // BYTE 1 -- "DIRECTION" of offset
	        // What should "GMT" send?
	        // (not that it matters, there shouldn't be properties
	        // with GMT, or CET either for that matter.)
	        if (hours < 0) {
	            strBuf.append(bObserveDST ? '7' : '1');
	        } else {
	            strBuf.append(bObserveDST ? '6' : '0');
	        }

	        // BYTE 2 & 3 -- "OFFSET"
	        // Change offset to a positive number
	        hours = (hours < 0 ? -hours : hours);
	        strBuf.append(rightJustifyZero(String.valueOf(hours), 2));

	        return strBuf.toString();
	    }
 	}
