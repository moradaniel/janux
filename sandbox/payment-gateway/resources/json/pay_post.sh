#!/bin/sh

if [ "$#" -lt 1 ]; then
 echo "Usage: `basename $0` [json-file]"
 echo "where [json-file] is a file containing a properly"
 echo "formatted json string based on the creditCard.json api"
 exit 1
fi

curl -k -u user1:password1 https://localhost/payment/app/creditCard.json -d `cat $1`
#curl -k -u gia-user:foo7uChu https://secure.innpoints.com/payment/app/creditCard.json -G -d 'save&creditCard={"creditCard":{"cardholderName":"Joe%20Tester","number":"4111111111111111","expiration":"12/30","type":{"code":"VI"}}}'
echo
