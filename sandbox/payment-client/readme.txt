* Requirements:

	1. Get the apache-maven-2.2.1 with support only java 6 for test.
		For compile and test the project 	
		
	2. Build the payment-gateway.war by the janux.payment-gateway project
		
* For compile and test the project

1- Can change the configuration before run the maven commands from the pom properties or the file src/main/filters/filter.properties

2- Execute this command: mvn clean compile install
All tests are executed by maven2 when you run this command. 
Maven2 runs a web server where it deploys on port 9090 the payment-gateway.war built by the janux-payment-gateway project and the tests are run

* About the project organization.

In the package "biz.janux.payment.gateway.test" are integration tests for run all alternatives to invoke the main services. 

	CreditCardControllerRestTest: test the calls via http. These calls return json or jsonp format. 

		There is a PaymentGatewayRestClientImpl class contains all http calls.
	
		The following requests are provided as illustration. We make every effort to insure the accuracy
		of this document, but please refer to the output of the tests in payment-util for the latest
		implementation of the json representations.
	
			Save with only required attributes:
			http://localhost:9090/payment/app/creditCard.json?save&creditCard={"creditCard":{"number":"4111111111111111","expiration":"12/2030","typeCode":"VI"}} 

			Save with businessUnitCode and/or autoCreateBusinessUnit attributes
			http://localhost:9090/payment/app/creditCard.json?save&creditCard={"creditCard":{"number":"4111111111111111","expiration":"12/2030","typeCode":"VI"}}}&businessUnitCode=1A-0980&autoCreateBusinessUnit=true
			
			Save with all possible attributes
			http://localhost:9090/payment/app/creditCard.jsonp?save&creditCard={"number":"4111111111111111","expiration":"09/2011","typeCode":"VI","billingAddress":{"city":"theCityWasChanged","country":"US","stateProvince":"CA","postalCode":"94043","line1":"1234 Amphitheatre Parkway"}}
			
			http://localhost:9090/payment/app/creditCard.json?load&pay_uuid=52146078-65ce-4b4c-9cf7-2c5907a27a34
	
		For return jsonp, the URL format: 
	
			http://localhost:9090/payment/app/creditCard.jsonp?save&callback=jsonp1304025962036&creditCard={"creditCard":{"number":"4111111111111111","expiration":"12/2030","typeCode":"VI"}}
			http://localhost:9090/payment/app/creditCard.jsonp?load&callback=jsonp1304025962036&pay_uuid=52146078-65ce-4b4c-9cf7-2c5907a27a34
		
			In the jsonp URL, you have to use the extension ".jsonp" and add a new parameter callback=jsonp1304025962036. It is the javascript function used by the jsonp protocol. It is useful to do calls cross-domain using jsonp.


	CreditCardRemotingServiceTest : test the calls via remoting service of the CreditCardStorageService. 
	
* Javadocs can be downloaded
Add the parameter -DdownloadJavadocs=true

* For test the janux-payment-client from command line
1- Execute this command: "mvn clean compile package" ,for build the executable jar "janux-payment-client-0.4.01-SNAPSHOT-jar"
2- Set the server configuration in the payment-client.properties file in the working directory
3- Execute this jar
Main class: biz.janux.payment.ClientMain
Arguments:

<1:Rest or 2:Remoting call> <1:Save or 2:Load or 3:Remove> 

if the action is "Save" you have to send these arguments:
<cardHolderName> <cardNumber> <type> <expirationDate>

if the action is "Load" or "Remove" you have to send these arguments:
<cardUuid>

Examples:

For save a credit card
Using Rest calls
java -jar "target/janux-payment-client-0.4.01-SNAPSHOT.jar"  1 1 "John Lennon" 4111111111111111 VI 12/20
Using Remoting Service calls
java -jar "target/janux-payment-client-0.4.01-SNAPSHOT.jar"  2 1 "John Lennon" 4111111111111111 VI 12/20

For load a credit card
Using Rest calls
java -jar "target/janux-payment-client-0.4.01-SNAPSHOT.jar" 1 2 010be75d-540f-4510-8be7-5d540f6510a4
Using Remoting Service calls
java -jar "target/janux-payment-client-0.4.01-SNAPSHOT.jar" 2 2 010be75d-540f-4510-8be7-5d540f6510a4

For remove a credit card
Using Rest calls
java -jar "target/janux-payment-client-0.4.01-SNAPSHOT.jar" 1 3 010be75d-540f-4510-8be7-5d540f6510a4
Using Remoting Service calls
java -jar "target/janux-payment-client-0.4.01-SNAPSHOT.jar" 2 3 010be75d-540f-4510-8be7-5d540f6510a4

 
