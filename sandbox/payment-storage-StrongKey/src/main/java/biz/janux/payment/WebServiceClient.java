package biz.janux.payment;

import java.io.StringReader;

import javax.xml.transform.stream.StreamSource;

import org.jdom.transform.JDOMResult;
import org.springframework.ws.client.core.WebServiceTemplate;

public class WebServiceClient {

	private WebServiceTemplate webServiceTemplate;
	
	public JDOMResult customSendAndReceive(String uri,String message) {
		StreamSource source = new StreamSource(new StringReader(message));
		JDOMResult result = new JDOMResult();
		webServiceTemplate.sendSourceAndReceiveToResult(uri,source,result);
		return result;
	}

  public WebServiceTemplate getWebServiceTemplate() { return this.webServiceTemplate;}

  public void setWebServiceTemplate(WebServiceTemplate o) { this.webServiceTemplate = o; }
}
