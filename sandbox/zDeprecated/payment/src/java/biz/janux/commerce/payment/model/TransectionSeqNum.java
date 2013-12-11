package biz.janux.commerce.payment.model;

/**
 * 
 * @author Nilesh
 * </br>
 * This Model is mapped to trans_seq_number table
 *
 */
public class TransectionSeqNum {
	
	int merchentId;
	int transID;
	int transnum;
	/**
	 * 
	 * @return int
	 */
	public int getTransID() {
		return transID;
	}
	/**
	 * 
	 * @param transID
	 */
	public void setTransID(int transID) {
		this.transID = transID;
	}
	/**
	 * 
	 * @return int
	 */
	public int getTransnum() {
		return transnum;
	}
	/**
	 * 
	 * @param transnum
	 */
	public void setTransnum(int transnum) {
		this.transnum = transnum;
	}
	/**
	 * 
	 * @return int
	 */
	public int getMerchentId() {
		return merchentId;
	}
	/**
	 * 
	 * @param merchentId
	 */
	public void setMerchentId(int merchentId) {
		this.merchentId = merchentId;
	}
}
