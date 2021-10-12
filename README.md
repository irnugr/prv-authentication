# prv-authentication

Service to maintain authentication

## Specification to run this service
- Java 11
- Maven 3.8.1

## To build this into jar
mvn clean package

## Command to start the service using maven
mvn spring-boot:run -Dspring-boot.run.profiles={dev|sit|uat|prd}

## Command to start the service using java
java -Dspring.profiles.active={dev|sit|uat|prd} -Djasypt.encryptor.algorithm={Pass The Algorithm} -Djasypt.encryptor.iv-generator-classname=org.jasypt.iv.NoIvGenerator -Djasypt.encryptor.password={Pass Your Secret Here} -jar {jarfile}
