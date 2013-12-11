* Requirements:

	1. Get the apache-maven-2.2.1 with support only java 6 for test.
		For compile and test the project 	
		
* For compile and test the project

2- Execute this command: mvn clean compile install
All tests are executed by maven2 when you run this command.

* About the project organization.

In the package "biz.janux.payment.test" is a test for run all alternatives to invoke the strong key encryption service. 

StrongKeyIntegrationTest : test the encryption, decryption and search services of the StrongKey appliance. 
	Notes: 
		For get the xsd: https://secvault01.secure.demo.com:8181/strongkeyliteWAR/EncryptionService?xsd=1
		For get the wsdl: https://secvault01.secure.demo.com:8181/strongkeyliteWAR/EncryptionService?wsdl

	
* Javadocs can be downloaded
Add the parameter -DdownloadJavadocs=true


