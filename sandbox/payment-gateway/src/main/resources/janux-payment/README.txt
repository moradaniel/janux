The files in this folder are configured to be read by the application at:

  classpath:[name-of-this-folder]/...

They are suffixed with a .sample extension because they are meant to be edited, the .sample
extension removed, and copied to:

	$CATALINA_BASE/lib/[name-of-this-folder]

or equivalent folder in a different application server.

The intent is that same war be useable in various environments, with the environment-specific
settings be contained in the files in this folder, and stored along with the environment.

For example, a log4jListener is configured in web.xml to look for the log4j.xml config file at:

	classpath:[name-of-this-folder]/log4j.xml

We suggest that you rename the log4j.xml.sample herewith to log4j.xml, edit it to suit your
preferences, and copy it to the folder: 

	$CATALINA_BASE/lib/inn-payment

In this way, the log4j.xml file can be adapted to each specific environment.  Likewise for the other
files in this folder.

Of course, you can also remove the .sample extension and place the files in:

  WEB-INF/classes/[name-of-this-folder]

and the application will also work.
