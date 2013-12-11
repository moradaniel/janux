The .json files included contain sample json messages that can be used to test a running
installation of the payment-gateway by cut-and-pasting the content of the files in the query string
of the browser.  This will not work with the cc_save command because those have to be sent via a
POST so that we do not risk logging the cc numbers in the http server logs.

Alternatively, on a unix machine that has the curl http client library installed, 
you can test on the command-line using the pay.sh and pay_post.sh shell scripts included.

In order to run pay.sh or pay_post.sh: 

	- copy it to the machine from which you want to test
	- make sure that curl is installed:

    $which curl
		-> /usr/bin/curl

	- make sure that the shell script is executable:

  chown a+x pay.sh

and either:

  (i) put it in your $PATH, and run:

    pay_post.sh cc_save.json

  (ii) or if not in your $PATH:

    ./pay_post.sh cc_save.json
