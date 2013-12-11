package biz.janux.commerce.payment.implementation.vendor.vital.response;
import java.io.PrintStream;

import biz.janux.commerce.payment.implementation.vendor.vital.helper.VitalHelper;
import biz.janux.commerce.payment.interfaces.response.SettlementResponse;
import biz.janux.commerce.payment.interfaces.response.codes.SettlementResponseCodes;

import org.apache.log4j.Logger;
/**
 * @author Nilesh
 * This implements SettlementResponse and SettlementResponseCodes
 * This class is used to generate response of Settlement
 */
public class VitalSettlementResponse implements SettlementResponse , SettlementResponseCodes {
	
	public byte[] vitalResponse;
 	 
		static Logger logger = Logger.getLogger(VitalSettlementResponse.class);
 
		// private member variables
		protected String recordFormat;
		protected String applicationType;
		protected String routingID;
		protected String recordType;
		protected String batchRecordCount;
		protected String batchNetDeposit;
		protected String batchResponseCode;
		protected String batchNumber;
		protected String batchResponseText;
		protected String batchTransmissionDate;
		protected String errorType;
		protected String errorRecordSeq;
		protected String errorRecordType;
		protected String errorDataFieldNum;
		protected String errorData;
		protected byte[] originalBytes;

		public VitalSettlementResponse(byte[] b){
			// parse internal response so that following methods give valid output
			vitalResponse = b;
			
			if (logger.isDebugEnabled()) {
				logger.debug("SettlementResponse(b=" + b + ")");
			}
 			// temporary, as to not mess up future processing
			byte[] bytes = new byte[b.length];
			originalBytes = new byte[b.length];
			System.arraycopy(b, 0, bytes, 0, b.length);
			System.arraycopy(b, 0, originalBytes, 0, b.length);

			VitalHelper.fixBytes(bytes); 

			recordFormat = new String(bytes, 1, 1);
			applicationType = new String(bytes, 2, 1);
			routingID = new String(bytes, 4, 1);
			recordType = new String(bytes, 5, 5);
			batchRecordCount = new String(bytes, 10, 9);
			batchNetDeposit = new String(bytes, 19, 16);
			batchResponseCode = new String(bytes, 35, 2);

			batchNumber = new String(bytes, 39, 3);
			if (batchResponseCode.equals(GOODBATCH)) {
				batchResponseText = new String(bytes, 42, 9).trim();
			} else if (batchResponseCode.equals(REJECTEDBATCH)) {
				errorType = new String(bytes, 42, 1);
				errorRecordSeq = new String(bytes, 43, 4);
				errorRecordType = new String(bytes, 47, 1);
				errorDataFieldNum = new String(bytes, 48, 2);

				// variable field
				// errorData = new String(bytes, 50, 32);
				errorData = new String(bytes, 50, bytes.length - 52); // JJM
				// 3/12/04
				// CHANGED CODE, THERE SEEMS TO BE AN ADDITIONAL CHARACTER
				// bytes.length - 51); // - 50, Then Ignore ETX
			} else if (batchResponseCode.equals(DUPLICATEBATCH)) {
				// MMDD
				batchTransmissionDate = new String(bytes, 42, 4);
			}

			  print(System.out);
		}

		// THESE SHOULD PROBABLY HAVE DIFFERENT METHOD NAMES
		// i.e. isRejected(), isGood(), isDuplicate()...
		// isBad() { return isRejected() || isDuplicate(); }
		public boolean isApproved() {
			return batchResponseCode.equals(GOODBATCH);
		}

		public boolean isDeclined() {
			return batchResponseCode.equals(REJECTEDBATCH)
					|| batchResponseCode.equals(DUPLICATEBATCH);
		}

		public void print(PrintStream out) {
			out.println(toString());
		}

		public String toString() {
			StringBuffer buf = new StringBuffer();
			try {
				buf.append(" ***************************************************************************** \n");
				buf.append("Record Format      : \"" + recordFormat + "\"\n");
				buf.append("Application Type   : \"" + applicationType + "\"\n");
				buf.append("Routing ID         : \"" + routingID + "\"\n");
				buf.append("Batch Rec Count    : \"" + batchRecordCount + "\"\n");
				buf.append("Batch Net Deposit  : \"" + batchNetDeposit + "\"\n");
				buf.append("Batch Response Code: \"" + batchResponseCode + "\"\n");
				buf.append("Batch Number       : \"" + batchNumber + "\"\n");
				if (batchResponseCode.equals(GOODBATCH)) {
					buf.append("Response Code      : \"" + batchResponseText
							+ "\"\n");
				} else if (batchResponseCode.equals(REJECTEDBATCH)) {
					buf.append("Error Type         : \"" + errorType + "\"\n");
					buf.append("Error Rec Sequence : \"" + errorRecordSeq + "\"\n");
					buf
							.append("Error Rec Type     : \"" + errorRecordType
									+ "\"\n");
					buf.append("Error Data Field   : \"" + errorDataFieldNum
							+ "\"\n");
					buf.append("Error Data         : \"" + errorData + "\"\n");
				}

				buf.append("Response           : \""
						+ getDetailedResponseDefinition() + "\"\n");
				buf.append("Approved?          : " + isApproved() + "\n");
				buf.append("Declined?          : " + isDeclined() + "\n");
				buf
						.append(" ****************************************************************************** \n");
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				ex.printStackTrace();
			}

			return buf.toString();
		}
		/**
		 * @return String
		 */
		public String getBatchResponseCode() {
			return batchResponseCode;
		}
		/**
		 * 
		 * @return String
		 */
		public String getBatchResponseText() {
			return batchResponseText;
		}
		/**
		 * @return String
		 */
		public String getBatchNumber() {
			return batchNumber;
		}
		/**
		 * @return byte[]
		 */
		public byte[] getBytes() {
			return originalBytes;
		}

		public String getDetailedResponseDefinition() {
			if (logger.isDebugEnabled()) {
				logger.debug("getDetailedResponseDefinition()");
			}

			if (batchResponseCode.equals(GOODBATCH)) {
				return "Approved and completed";
			} else if (batchResponseCode.equals(REJECTEDBATCH)) {
				String errorMessage = "The batch was rejected ";
				if (errorType.equals(BLOCKEDTERMINAL))
					errorMessage += "BLOCKED TERMINAL";
				else if (errorType.equals(CARDTYPEERROR))
					errorMessage += "CARD TYPE ERROR ";
				else if (errorType.equals(DEVICEERROR))
					errorMessage += "DEVICE ERROR";
				else if (errorType.equals(BATCHERROR))
					errorMessage += "BATCH ERROR";
				else if (errorType.equals(SEQUENCEERROR))
					errorMessage += "SEQUENCE ERROR";
				else if (errorType.equals(TRANSMISSIONERROR))
					errorMessage += "TRANSMISSION ERROR";
				else if (errorType.equals(UNKNOWNERROR))
					errorMessage += "UNKNOWN ERROR";
				else if (errorType.equals(ROUTNINGERROR))
					errorMessage += "ROUTNING ERROR";
				errorMessage += "The error sequence number is " + errorRecordSeq
						+ " ";
				if (errorRecordType.equals(HEADERERROR))
					errorMessage += "The error was in the header record ";
				else if (errorRecordType.equals(PARAMETERERROR))
					errorMessage += "The error was in the parameter record ";
				else if (errorRecordType.equals(DETAILERROR))
					errorMessage += "The error was in the detail record ";
				else if (errorRecordType.equals(LINEITEMERROR))
					errorMessage += "The error was in a line item ";
				else if (errorRecordType.equals(TRAILERERROR))
					errorMessage += "The error was in the trailer record ";

				return errorMessage;
			} else if (batchResponseCode.equals(DUPLICATEBATCH)) {
				return "The batch number was a duplicate";
			} else {
				return "UNKNOWN RESPONSE, CODE: " + batchResponseCode;
			}
		}
 		/**
		 * @return String
		 */
		public String getBatchRecordCount() {
			return batchRecordCount;
		}
 		/**
		 * @return String
		 */
		public String getErrorData() {
			return errorData;
		}

		/**
		 * @return String
		 */
		public String getErrorDataFieldNum() {
			return errorDataFieldNum;
		}

		/**
		 * @return String
		 */
		public String getErrorRecordSeq() {
			return errorRecordSeq;
		}

		/**
		 * @return String
		 */
		public String getErrorRecordType() {
			return errorRecordType;
		}

		/**
		 * @return String 
		 */
		public String getErrorType() {
			return errorType;
		}

		public String getErrorCode() {
			// TODO Auto-generated method stub
			return null;
		}

		public String getLocalTransDate() {
			// TODO Auto-generated method stub
			return null;
		}

		public String getLocalTransTime() {
			// TODO Auto-generated method stub
			return null;
		}

		public String getStatus() {
			// TODO Auto-generated method stub
			return null;
		}

		public String getTransactionIdentifier() {
			// TODO Auto-generated method stub
			return null;
		}

		public String getTransactionSequenceNumber() {
			// TODO Auto-generated method stub
			return null;
		}
		/**
		 * 
		 * @return String 
		 */
		public String getRecordFormat() {
			return recordFormat;
		}

		public void setRecordFormat(String recordFormat) {
			this.recordFormat = recordFormat;
		}
		/**
		 * 
		 * @return String 
		 */
		public String getApplicationType() {
			return applicationType;
		}
		
		public void setApplicationType(String applicationType) {
			this.applicationType = applicationType;
		}
		/**
		 * 
		 * @return String 
		 */
		public String getRoutingID() {
			return routingID;
		}

		public void setRoutingID(String routingID) {
			this.routingID = routingID;
		}
		/**
		 * 
		 * @return String 
		 */
		public String getRecordType() {
			return recordType;
		}

		public void setRecordType(String recordType) {
			this.recordType = recordType;
		}
		/**
		 * 
		 * @return String 
		 */
		public String getBatchNetDeposit() {
			return batchNetDeposit;
		}

		public void setBatchNetDeposit(String batchNetDeposit) {
			this.batchNetDeposit = batchNetDeposit;
		}
		/**
		 * 
		 * @return String 
		 */
		public String getBatchTransmissionDate() {
			return batchTransmissionDate;
		}

		public void setBatchTransmissionDate(String batchTransmissionDate) {
			this.batchTransmissionDate = batchTransmissionDate;
		}
		/**
		 * 
		 * @return byte[] 
		 */
		public byte[] getOriginalBytes() {
			return originalBytes;
		}
		/**
		 * 
		 * @param originalBytes
		 */
		public void setOriginalBytes(byte[] originalBytes) {
			this.originalBytes = originalBytes;
		}
		/**
		 * 
		 * @param batchRecordCount
		 */
		public void setBatchRecordCount(String batchRecordCount) {
			this.batchRecordCount = batchRecordCount;
		}
		/**
		 * 
		 * @param batchResponseCode
		 */
		public void setBatchResponseCode(String batchResponseCode) {
			this.batchResponseCode = batchResponseCode;
		}
		/**
		 * 
		 * @param batchNumber
		 */
		public void setBatchNumber(String batchNumber) {
			this.batchNumber = batchNumber;
		}
		/**
		 * 
		 * @param batchResponseText
		 */
		public void setBatchResponseText(String batchResponseText) {
			this.batchResponseText = batchResponseText;
		}
		/**
		 * 
		 * @param errorType
		 */
		public void setErrorType(String errorType) {
			this.errorType = errorType;
		}
		/**
		 * 
		 * @param errorRecordSeq
		 */
		public void setErrorRecordSeq(String errorRecordSeq) {
			this.errorRecordSeq = errorRecordSeq;
		}
		/**
		 * 
		 * @param errorRecordType
		 */
		public void setErrorRecordType(String errorRecordType) {
			this.errorRecordType = errorRecordType;
		}
		/**
		 * 
		 * @param errorDataFieldNum
		 */
		public void setErrorDataFieldNum(String errorDataFieldNum) {
			this.errorDataFieldNum = errorDataFieldNum;
		}
		/**
		 * 
		 * @param errorData
		 */
		public void setErrorData(String errorData) {
			this.errorData = errorData;
		} 
}
