* For creation docker image in local, you should make maven docker plugin skip false.
    <skip>true</skip>


* Above command will build the project and create a docker image.
    ./mvnw package

** Project based on Spring Boot and RestAPI. Project has a docker file and can be dockerized.


* By accessing the root of the server (/read-line) it needs FileRequest json in body which include tableFilePath, columnDefinitionFilePath, 
rowDefinitionFilePath and newFilePath to show updated rows of file.

