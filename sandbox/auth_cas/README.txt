Configuring SSL in Tomcat 5.5

------------------------------------------------------------------------------------------
keytool - create a certificate file from a private key (keystore)
------------------------------------------------------------------------------------------

------------------------------------------------------------------------------------------
Locate and backup your Trusted Certificate file, in case something goes wrong
------------------------------------------------------------------------------------------

$cd JDK_HOME/jre/lib/security
$cp cacerts cacerts.bak

------------------------------------------------------------------------------------------
Create a private key and store it in a suitable file 

(we are storing it here in the java trusted certificate file 'cacerts', but it
 would be better practice to store it in a secure location elsewhere)

(The default password for the cacerts keystore is 'changeit', it should be
changed when installing to a production environment)
------------------------------------------------------------------------------------------

JDK_HOME/bin>keytool -keystore JDK_HOME/jre/lib/security/server.key -genkey -alias tomcat -keyalg RSA

Enter keystore password:  SomePassword

What is your first and last name?
[Unknown]: localhost
What is the name of your organizational unit?
[Unknown]: SSO Services
What is the name of your organization?
[Unknown]: SSO solutions
What is the name of your City or Locality?
[Unknown]: California
What is the name of your State or Province?
[Unknown]: California
What is the two-letter country code for this unit?
[Unknown]: US

Is CN=localhost, OU=SSO Services, O=SSO Solutions, L=California, ST=California, C=US correct?
  [no]:  yes

Enter key password for <tomcat>
        (RETURN if same as keystore password):

        
------------------------------------------------------------------------------------------
Check that the particular key was added to the keystore
------------------------------------------------------------------------------------------
JDK_HOME/bin>keytool -list -v -keystore JDK_HOME/jre/lib/security/server.key -alias tomcat

Enter keystore password:  SomePassword
Alias name: tomcat
Creation date: Jun 8, 2010
Entry type: keyEntry
Certificate chain length: 1
Certificate[1]:
Owner: CN=localhost, OU=SSO Services, O=SSO Solutions, L=California, ST=California, C=US
Issuer: CN=localhost, OU=SSO Services, O=SSO Solutions, L=California, ST=California, C=US
Serial number: 4c0eb902
Valid from: Tue Jun 08 16:41:22 ACT 2010 until: Mon Sep 06 16:41:22 ACT 2010
Certificate fingerprints:
         MD5:  33:B9:83:63:2B:73:7C:10:52:10:D8:F3:51:2C:57:2A
         SHA1: 0D:DD:CC:77:B2:8C:AD:F7:1A:6A:B3:CC:B2:2C:F6:CA:5E:AA:C6:0D

------------------------------------------------------------------------------------------
Export a self-signed certificate based on this key into server.crt
------------------------------------------------------------------------------------------
JDK_HOME/bin>keytool -keystore JDK_HOME/jre/lib/security/server.key -export -alias tomcat -file JDK_HOME/jre/lib/security/server.crt

------------------------------------------------------------------------------------------
Now import this certificate 'server.crt' into your keystore of trusted certificates 'cacerts'
------------------------------------------------------------------------------------------ 
JDK_HOME/bin>keytool -import -trustcacerts -file JDK_HOME/jre/lib/security/server.crt -keystore JDK_HOME/jre/lib/security/cacerts

(The '-trustcacerts' option causes keytool to consider additional certificates
 in the chain of trust, notably those in the 'cacerts' file.  This is not
 strictly necessary for self-signed certs) 

------------------------------------------------------------------------------------------         
Configure your tomcat for ssl
------------------------------------------------------------------------------------------
Go to $CATALINA_BASE/conf/server.xml file and uncomment the following setting.


	<Connector port="8443" maxHttpHeaderSize="8192"
		maxThreads="150" minSpareThreads="25" maxSpareThreads="75"
		enableLookups="false" disableUploadTimeout="true"
		acceptCount="100" scheme="https" secure="true"
		clientAuth="false" sslProtocol="TLS" 
		keystoreFile="JDK_HOME/jre/lib/security/server.key" keystorePass="SomePassword"
		truststoreFile="JDK_HOME/jre/lib/security/cacerts"  truststorePass="changeit"
	/> 
		
