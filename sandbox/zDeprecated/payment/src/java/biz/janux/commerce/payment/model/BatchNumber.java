package biz.janux.commerce.payment.model;

/**
 * 
 * @author Nilesh
 * 
 * This Model is mapped to batch_number table
 * It keeps the record of generated batchNumber
 *
 */
public class BatchNumber {

	int hotelId;
	int batchNumberID;
	int number;
	/**
	 * 
	 * @return int
	 */
	public int getBatchNumberID() {
		return batchNumberID;
	}
	/**
	 * 
	 * @param batchNumberID
	 */
	public void setBatchNumberID(int batchNumberID) {
		this.batchNumberID = batchNumberID;
	}
	/**
	 * 
	 * @return int
	 */
	public int getNumber() {
		return number;
	}
	/**
	 * 
	 * @param number
	 */
	public void setNumber(int number) {
		this.number = number;
	}
	/**
	 * 
	 * @return int
	 */
	public int getHotelId() {
		return hotelId;
	}
	/**
	 * 
	 * @param hotelId
	 */
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
}
