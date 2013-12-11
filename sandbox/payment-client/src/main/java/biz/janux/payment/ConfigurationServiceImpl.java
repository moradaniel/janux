package biz.janux.payment;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class ConfigurationServiceImpl implements ConfigurationService {

	String urlServerPort;
	String urlServerHost;
	String urlServerProtocol;
	String urlServerContext;
	String loginUsername;
	String loginPassword;
	
	private HttpClient httpClient;
	
	public ConfigurationServiceImpl(String urlServerHost,String urlServerPort,String loginUsername,String loginPassword, HttpClient httpClient,StandardPBEStringEncryptor jasyptPBEStringEncryptor) {
		super();
		this.urlServerHost = urlServerHost;
		this.urlServerPort = urlServerPort;
		this.loginUsername = loginUsername; 
		this.loginPassword = loginPassword;
		this.httpClient = httpClient;
		
		//String passwordDecrypted = jasyptPBEStringEncryptor.decrypt(getLoginPassword());
	
		/**
		 * password1=jiyX7ZN9+Jan5Qj/EuevKyYGcCo70SH2 
		 */
		/**
		 * the credentials are set
		 */
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(getLoginUsername(),getLoginPassword());
		httpClient.getState().setCredentials(new AuthScope(getUrlServerHost(),Integer.parseInt(getUrlServerPort()), AuthScope.ANY_REALM), credentials);
	}

	public String getUrlServer() {
		String url = getUrlServerProtocol()+"://"+getUrlServerHost()+":"+getUrlServerPort()+"/"+getUrlServerContext();
		return url;
	}

	public String getUrlServerPort() {
		return urlServerPort;
	}

	public void setUrlServerPort(String urlServerPort) {
		this.urlServerPort = urlServerPort;
	}

	public String getUrlServerHost() {
		return urlServerHost;
	}

	public void setUrlServerHost(String urlServerHost) {
		this.urlServerHost = urlServerHost;
	}

	public String getUrlServerProtocol() {
		return urlServerProtocol;
	}

	public void setUrlServerProtocol(String urlServerProtocol) {
		this.urlServerProtocol = urlServerProtocol;
	}

	public String getUrlServerContext() {
		return urlServerContext;
	}

	public void setUrlServerContext(String urlServerContext) {
		this.urlServerContext = urlServerContext;
	}

	public String getLoginUsername() {
		return loginUsername;
	}

	public void setLoginUsername(String loginUsername) {
		this.loginUsername = loginUsername;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public HttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}

}
