package biz.janux.commerce.payment.model;

/**
 * 
 * @author Nilesh
 * </br>
 * Model to map State Table.
 *
 */
public class State {
	private long stateId;
	private  String code;
	private String title;
	/**
	 * 
	 * @return long
	 */
	public long getStateId() {
		return stateId;
	}
	/**
	 * 
	 * @param stateId
	 */
	public void setStateId(long stateId) {
		this.stateId = stateId;
	}
	/**
	 * 
	 * @return String
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 
	 * @return String
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
}
