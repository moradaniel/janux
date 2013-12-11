package biz.janux.payment;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.XMLOutputter;
import org.jdom.transform.JDOMResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptedStorageServiceStrongKeyImpl implements EncryptedStorageService<String, String>{

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private String uriWebService;

	/**
	 * The unique identifier of the encryption domain on the SKLES
	 */
	private long domain;

	private String usernameEncryptor;
	
	private String passwordEncryptor;
	
	private String usernameDecryptor;
	
	private String passwordDecryptor;
	
	private WebServiceClient webServiceClient;
	
	Namespace ns = Namespace.getNamespace("ns", "http://web.strongkeylite.strongauth.com/");
	XMLOutputter out = new XMLOutputter();
	
	public String encryption(String sensitiveData) {
		log.debug("Attemping to encrypt and store a credit card number in the appliance");
		
		Document requestXml = new Document();
		
		Element root = new Element("encrypt",ns);
		root.addNamespaceDeclaration(ns);

		requestXml.setRootElement(root);

		Element element;
		element = new Element("did").setText(""+getDomain());
		root.addContent(element);
		
		element = new Element("username").setText(getUsernameEncryptor());
		root.addContent(element);
		
		element = new Element("password").setText(getPasswordEncryptor());
		root.addContent(element);
		
		element = new Element("plaintext").setText(sensitiveData);
		root.addContent(element);
		
		String requestXmlToString = out.outputString(requestXml); 
		JDOMResult response= getWebServiceClient().customSendAndReceive(getUriWebService(),requestXmlToString);
		
		Document responseXml = response.getDocument();
		log.debug("Successfully encrypted and stored a credit card number in the appliance");
		return responseXml.getRootElement().getChild("return").getText();
	}

	public String decryption(String token) {
		log.debug("Attemping to deencrypt a token from the appliance");
		
		Document requestXml = new Document();
		
		Element root = new Element("decrypt",ns);
		root.addNamespaceDeclaration(ns);

		requestXml.setRootElement(root);

		Element element;
		element = new Element("did").setText(""+getDomain());
		root.addContent(element);
		
		element = new Element("username").setText(getUsernameDecryptor());
		root.addContent(element);
		
		element = new Element("password").setText(getPasswordDecryptor());
		root.addContent(element);
		
		element = new Element("token").setText(token);
		root.addContent(element);
		
		String requestXmlToString = out.outputString(requestXml); 
		JDOMResult response= getWebServiceClient().customSendAndReceive(getUriWebService(),requestXmlToString);
		
		Document responseXml = response.getDocument();
		log.debug("Successfully decrypted a token from the appliance");
		
		return responseXml.getRootElement().getChild("return").getText();
	}

	public boolean delete(String token) {
		log.debug("Attemping to delete a token from the appliance");
		
		Document requestXml = new Document();
		
		Element root = new Element("delete",ns);
		root.addNamespaceDeclaration(ns);

		requestXml.setRootElement(root);

		Element element;
		element = new Element("did").setText(""+getDomain());
		root.addContent(element);
		
		element = new Element("username").setText(getUsernameEncryptor());
		root.addContent(element);
		
		element = new Element("password").setText(getPasswordEncryptor());
		root.addContent(element);
		
		element = new Element("token").setText(token);
		root.addContent(element);
		
		String requestXmlToString = out.outputString(requestXml); 
		JDOMResult response= getWebServiceClient().customSendAndReceive(getUriWebService(),requestXmlToString);
		
		Document responseXml = response.getDocument();
		log.debug("Successfully deleted a token from the appliance");
		
		String something = responseXml.getRootElement().getChild("return").getText();
		return true;
	}

	public String search(String sensitiveData) {
		log.debug("Attemping to search a credit card number in the appliance");
		
		Document requestXml = new Document();
		
		Element root = new Element("search",ns);
		root.addNamespaceDeclaration(ns);

		requestXml.setRootElement(root);

		Element element;
		element = new Element("did").setText(""+getDomain());
		root.addContent(element);
		
		element = new Element("username").setText(getUsernameDecryptor());
		root.addContent(element);
		
		element = new Element("password").setText(getPasswordDecryptor());
		root.addContent(element);
		
		element = new Element("plaintext").setText(sensitiveData);
		root.addContent(element);
		
		String requestXmlToString = out.outputString(requestXml); 
		JDOMResult response= getWebServiceClient().customSendAndReceive(getUriWebService(),requestXmlToString);
		
		Document responseXml = response.getDocument();
		log.debug("Successfully searched a credit card number in the appliance");
		
		return responseXml.getRootElement().getChild("return").getText();
	}

	public void setWebServiceClient(WebServiceClient webServiceClient) {
		this.webServiceClient = webServiceClient;
	}

	public WebServiceClient getWebServiceClient() {
		return webServiceClient;
	}

	public void setUriWebService(String uriWebService) {
		this.uriWebService = uriWebService;
	}

	public String getUriWebService() {
		return uriWebService;
	}

	public void setDomain(long domain) {
		this.domain = domain;
	}

	public long getDomain() {
		return domain;
	}

	public void setUsernameEncryptor(String usernameEncryptor) {
		this.usernameEncryptor = usernameEncryptor;
	}

	public String getUsernameEncryptor() {
		return usernameEncryptor;
	}

	public void setPasswordEncryptor(String passwordEncryptor) {
		this.passwordEncryptor = passwordEncryptor;
	}

	public String getPasswordEncryptor() {
		return passwordEncryptor;
	}

	public void setUsernameDecryptor(String usernameDecryptor) {
		this.usernameDecryptor = usernameDecryptor;
	}

	public String getUsernameDecryptor() {
		return usernameDecryptor;
	}

	public void setPasswordDecryptor(String passwordDecryptor) {
		this.passwordDecryptor = passwordDecryptor;
	}

	public String getPasswordDecryptor() {
		return passwordDecryptor;
	}

}
