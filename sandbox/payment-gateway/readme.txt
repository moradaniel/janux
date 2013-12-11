
* Requirements:
	1. Get the apache-maven-2.2.1 or higher
		For compile and test the project 
			
* For compile and test the project

1- Can change the configuration before run the maven commands from the pom properties or the file src/main/filters/filter.properties

2- Execute this command: mvn clean compile war:war test
All tests are executed by maven2 when you run this command. 

* About the project organization.

In the package "biz.payment.test" is unit test. 

	CreditCardServiceTest : test the CreditCardStorageService in memory
	
* Test the credit card form for save a new credit card

	Go to http://localhost:9090/payment-gateway/jsp/creditCardForm
	
* Javadocs can be downloaded
Add the parameter -DdownloadJavadocs=true
 
