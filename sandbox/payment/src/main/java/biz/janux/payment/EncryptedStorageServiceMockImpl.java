package biz.janux.payment;

import java.util.HashMap;
import java.util.Map;

import biz.janux.payment.mock.Credential;

public class EncryptedStorageServiceMockImpl implements EncryptedStorageService<String, String>
{
	private Map<String, String> savedDataBySensitiveData = new HashMap<String, String>();
	private Map<String, String> savedDataByToken = new HashMap<String, String>();

	private Credential credential;
	
	public String encryption(String sensitiveData) {
		String token = "token"+sensitiveData;
		savedDataBySensitiveData.put(sensitiveData,token);
		savedDataByToken.put(token,sensitiveData);
		return token;
	}

	public String decryption(String token) {
		String sensitiveData = token.replaceAll("token", "");
		return sensitiveData;
	}

	public boolean delete(String token) {
		if (savedDataByToken.containsKey(token))
		{
			String sensitiveData = savedDataByToken.get(token); 
			savedDataByToken.remove(token);
			savedDataBySensitiveData.remove(sensitiveData);
			return true;
		}
		return false;
	}

	public String search(String sensitiveData) {
		String tokenString = savedDataBySensitiveData.get(sensitiveData);
		return tokenString;
	}

	/** 
	 * This bean is here to test/demonstrate decryption of system level username, 
	 * it is not otherwise used in the logic of this bean
	 */
	public Credential getCredential() { return this.credential;}
	public void setCredential(Credential n) { this.credential = n; }

} // End class EncryptedStorageServiceMockImpl
