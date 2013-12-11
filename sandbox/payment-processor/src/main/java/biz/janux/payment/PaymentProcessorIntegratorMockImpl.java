package biz.janux.payment;

import java.math.BigDecimal;
import java.util.Date;

public class PaymentProcessorIntegratorMockImpl implements PaymentProcessorIntegrator{

	int transactionIdentifierIndex = 0;
	int approvalCodeIndex = 0;
	
	public AuthorizationResponse processAuthorization(Authorization authorization, BigDecimal authAmount, AuthorizationType authorizationType) {
		AuthorizationResponse authorizationResponse = new AuthorizationResponseImpl();
		authorizationResponse.setApproved(true);
		
		approvalCodeIndex++;
		authorizationResponse.setApprovalCode("AC"+approvalCodeIndex);
		
		authorizationResponse.setDate(new Date());
		authorizationResponse.setRequestedACI("");
		authorizationResponse.setReturnedACI("");
		authorizationResponse.setResponseCode("");
		authorizationResponse.setAvsResultCode("");
		authorizationResponse.setValidationCode("");
		authorizationResponse.setLocalTransDate("");
		authorizationResponse.setLocalTransTime("");
		authorizationResponse.setAuthSourceCode("");
		authorizationResponse.setRetrievalReferenceNumber("");

		transactionIdentifierIndex++;
		authorizationResponse.setTransactionIdentifier("TI"+transactionIdentifierIndex);
		
		return authorizationResponse;
	}

	public SettlementResponse processSettlement(Settlement settlement, int batchNumber) {
		SettlementResponse settlementResponse = new SettlementResponseImpl();
		settlementResponse.setRecordFormat("");
		settlementResponse.setApplicationType("");
		settlementResponse.setRoutingID("");
		settlementResponse.setRecordType("");
		settlementResponse.setBatchRecordCount("");
		settlementResponse.setBatchNetDeposit("");
		settlementResponse.setBatchResponseCode("");
		settlementResponse.setBatchNumber(""+batchNumber);
		settlementResponse.setApproved(true);
		settlementResponse.setDate(new Date());
		settlementResponse.setBatchResponseText("");
		
		return settlementResponse;
	}

	public int checkLimitBatchNumber(int batchNumber) {
		return batchNumber;
	}

	public int checkLimitTransactionSequenceNumber(int transactionSequenceNumber) {
		return transactionSequenceNumber;
	}

	public AuthorizationResponse processAuthorizationOffline(Authorization authorization, BigDecimal authAmount, AuthorizationType authorizationType,String approvalCode) {
		AuthorizationResponse authorizationResponse = new AuthorizationResponseImpl();
		authorizationResponse.setDate(authorization.getDate());
		authorizationResponse.setAvsResultCode("0");
		authorizationResponse.setAuthSourceCode("E");		
		authorizationResponse.setResponseCode("  ");
		authorizationResponse.setReturnedACI(" ");
		authorizationResponse.setRequestedACI("");
		authorizationResponse.setTransactionIdentifier("000000000000000");
		authorizationResponse.setValidationCode("    ");
		authorizationResponse.setApprovalCode(approvalCode);
		
		transactionIdentifierIndex++;
		authorizationResponse.setTransactionSequenceNumber("TI"+transactionIdentifierIndex);

		authorizationResponse.setRetrievalReferenceNumber("OFFLINE");
		authorizationResponse.setOffline(true);
		authorizationResponse.setApproved(true);
		authorizationResponse.setLocalTransDate("");
		authorizationResponse.setLocalTransTime("");

		return authorizationResponse;
	}

}
