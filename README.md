# Lottery (backend project)


Lottery is an online version of the classic number lottery-Lotto. Users select 6 unique numbers from 1 to 99 to receive a special ticket ID. Winners are determined weekly by matching at least 3 numbers. Results, user tickets, and winning numbers are stored in a MongoDB database using SpringData. A scheduled lottery draw occurs weekly (every Saturday at 12:00), processing all tickets to identify winners. Users can check their results and claim their prizes using their unique ticket ID. Key features that distinguish this application:
- *User's Input Numbers Validation*
- *Date Information for the Drawing*
- *Unique Identifier Information*
- *Checking Win Status*
- *Integration with Remote HTTP Server for Winning Numbers*
- *Scheduled Saturday Drawings at 12:00*
- *Caching Mechanism*
- *User Registration*
- *JWT Token for Access*


> AUTHOR: SZYMON KORNIK <br>
> LINKEDIN: https://www.linkedin.com/in/szymon-kornik-3b12ba217/ <br>
> WEBSITE: https://www.szymonkornik.com/ <br>


## Specification


- Spring Boot web application
- Architecture: Modular monolith hexagonal architecture 
- NoSQL databases (MongoDB) for coupon and results repositories
- Facade's design pattern
- High coverage with unit tests and integration tests
- MockMvc was used to test the controllers and winning numbers http client was stubbed using WireMock
- Scheduled winning numbers generating and result proccesing
- Full containerization in Docker


## Tech


Lottery is developed using following technologies: <br>


### Core:

![image](https://img.shields.io/badge/17-Java-orange?style=for-the-badge) &nbsp;
![image](https://img.shields.io/badge/apache_maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white) &nbsp;
![image](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring) &nbsp;
![image](https://img.shields.io/badge/MongoDB-4EA94B?style=for-the-badge&logo=mongodb&logoColor=white) &nbsp;
![image](https://img.shields.io/badge/redis-%23DD0031.svg?&style=for-the-badge&logo=redis&logoColor=white) &nbsp;
![image](https://img.shields.io/badge/Docker-2CA5E0?style=for-the-badge&logo=docker&logoColor=white) &nbsp;

### Testing:

![image](https://img.shields.io/badge/Junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white) &nbsp;
![image](https://img.shields.io/badge/Mockito-78A641?style=for-the-badge) &nbsp;
![image](https://img.shields.io/badge/Testcontainers-9B489A?style=for-the-badge) &nbsp;


## Installation and run

### Requirements:
- [Docker](https://www.docker.com/products/docker-desktop/) to run.

### To run the application:
- Just run following command, and wait for containers to be pulled up and started.

``
docker compose up
``

- Alternatively you can run docker-compose file through you IDE

After everything builds and ready, you can start the application and test the application using [Postman](https://www.postman.com/) 
or use <a href="http://localhost:8080/swagger-ui/index.html#/">Swagger</a>.
Please note, that lottery results are generated each Saturday at 12:00.<br>

## Rest-API Endpoints

Application provides two endpoints: for checking results by ticket ID and for numbers input. Please follow the specification below:

Service url: http://localhost:8080


|       ENDPOINT        | METHOD |         REQUEST            | RESPONSE |             FUNCTION                       |
|:---------------------:|:------:|:--------------------------:|:--------:|:------------------------------------------:|
|  /results/{ticketId}  |  GET   | PATH VARIABLE (ticketId)   |   JSON   |  user retrieves results for ID             |
|    /inputNumbers      |  POST  | BODY-JSON (inputNumbers)   |   JSON   |  user inputs 6 distinct numbers            |
|    /register          |  POST  | BODY-JSON (registerRequest)|   JSON   | Register a new user                        |
|     /token            |  POST  | BODY-JSON (loginRequest)   |   JSON   | Generate an access token with login details|


Future plans:

- *Implement frontend with Angular*
- *Deploying on AWS*
- *Extract modules to microservices architecture*
