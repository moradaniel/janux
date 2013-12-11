* The project support only java 6.
Because there is a HTTPClient lib that has problem with java 5 and SSL certificates. 
This lib is used when the requests for cc transactions are sent to TSYS. 

* For compile and test the project you have to get the apache-maven-2.0.9 or higher

A database has to be created and configured in the file "src/main/resources/paymentGateway.properties".
The script for create the database is in the folder "/sql/mysql/"

Execute this command: mvn clean compile war:war test
All tests are executed by maven2 when you run this command. 
Maven2 up a web server where it deploy the generated war on port 9090, and run the tests.

* About the project organization.

In the package "com.innpoints.payment.test" are unit and integration tests for run all alternatives to invoke the main services. 

	CreditCardControllerRestTest: test the calls via http. These calls return json or jsonp format. 

		There is a PaymentGatewayRestClientImpl class contains all http calls.
	
		For return json, the URL format: 
	
		http://localhost:38080/payment-gateway/payment/creditcard.json?save&pay_uuid=&pay_cardNumber=4111111111111111&pay_expiration=12%2F12&pay_type=VI&pay_cardholderName=&pay_postalAddressLine1=&pay_postalAddressLine2=&pay_postalAddressPostalCode=&pay_postalAddressCity=&pay_postalAddressStateProvince=AS&pay_postalAddressCountry=US
	
		For return jsonp, the URL format: 
	
		http://localhost:38080/payment-gateway/payment/creditcard.jsonp?save&callback=jsonp1304025962036&pay_uuid=&pay_cardNumber=4111111111111111&pay_expiration=12%2F12&pay_type=VI&pay_cardholderName=&pay_postalAddressLine1=&pay_postalAddressLine2=&pay_postalAddressPostalCode=&pay_postalAddressCity=&pay_postalAddressStateProvince=AS&pay_postalAddressCountry=US
	
		In the jsonp URL, you have to use the extension ".jsonp" and add a new parameter callback=jsonp1304025962036. It is the javascript function used by the jsonp protocol. It is useful to do calls cross-domain using jsonp.


	CreditCardRemotingServiceTest : test the calls via remoting service of the CreditCardStorageService. 

	CreditCardServiceTest : test the CreditCardStorageService in memory 

	CreditCardProcessingServiceVitalTest : test all possible credit card transactions using the Tsys (Vital) payment processor.
	
	
* Javadocs can be downloaded for the "janux-api-payment-processor" jar
Add the parameter -DdownloadJavadocs=true
	