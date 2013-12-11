package biz.janux.payment;

public interface ConfigurationService {

	public String getUrlServer();

	public void setUrlServerContext(String urlServerContext);

	public String getUrlServerContext();

	public void setUrlServerProtocol(String urlServerProtocol);

	public String getUrlServerProtocol();

	public void setUrlServerHost(String urlServerHost);

	public String getUrlServerHost();

	public void setUrlServerPort(String urlServerPort);

	public String getUrlServerPort();

	public void setLoginPassword(String loginPassword);

	public String getLoginPassword();

	public void setLoginUsername(String loginUsername);

	public String getLoginUsername();

}
