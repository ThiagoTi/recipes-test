# Recipes test

### Requirements

* Docker (mine is version 25.0.1)
* Docker compose (mine is version 2.24.2)
* Maven (optional, mine is version 3.6.3)
* Java 21

---

### To start DB:

* Run docker compose file in scripts folder

### To run:

Two options:

 - using Maven:
   - $ mvn clean package spring-boot:repackage
   - $ java -jar target/test-1.0.0.jar
 - using Spring Boot:
   - $ ./mvnw spring-boot:run

Server is running on port **8081**