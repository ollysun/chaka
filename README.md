# chaka
clone the project

chakatest for maven project
binarygap for the binary test

#chatatest

You can run the project

mvn spring-boot:run

To add transaction
http://localhost:8090/transaction

Payload. make sure the timestamp is within 60 seconds
else you get 204 error code or future which is 422 code.
Those with 204 or 422 are not added except 201 created successfully
{
    "amount":"12.3343",
    "timestamp":"2018-07-17T09:59:51.312Z"
}

To view statistics
http://localhost:8090/statistics

To delete transactions
http://localhost:8090/transactions


