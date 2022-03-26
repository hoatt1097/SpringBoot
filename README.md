
## SpringBoot (Java) Backend
Application to demonstrate various parts of a service oriented RESTfull application.


### Technology Stack
Component         | Technology
---               | ---
Backend (REST)    | [SpringBoot](https://projects.spring.io/spring-boot) (Java)
Security          | Token Based (Spring Security and [JWT](https://github.com/auth0/java-jwt) )
REST Documentation| [Swagger UI / Springfox](https://github.com/springfox/springfox) and [ReDoc](https://github.com/Rebilly/ReDoc)
REST Spec         | [Open API Standard](https://www.openapis.org/)
Persistence       | JPA (Using Spring Data)
Server Build Tools| Maven(Java)
Database| Mysql

## Prerequisites
Ensure you have this installed before proceeding further
- Java 8
- Maven 3.3.9+

## About
This is an RESTfull implementation of an order processing app based on Northwind database schema from Microsoft.
The goal of the project is to
- Highlight techniques of making and securing a REST full app using [SpringBoot](https://projects.spring.io/spring-boot)

### Features of the Project
* Backend
    * Token Based Security (using Spring security)
    * API documentation and Live Try-out links with Swagger
    * Db with Mysql
    * Using JPA and JDBC template to talk to relational database
    * How to request and respond for paginated data

* Build
    * How to build all in one app that includes (database, sample data, RESTfull API, Auto generated API Docs, frontend and security)
    * Portable app, Ideal for dockers, cloud hosting.
    

### Build Backend (SpringBoot Java)
```bash
# Maven Build : Navigate to the root folder where pom.xml is present 
mvn clean install
```

### Open api start
```bash
# Start with docker
# port and other configurations for API servere is in [./open-api/openapi.yml) file

# Install docker and docker-compose. After that check version:
docker -v # Docker version 20.10.12, build e91ed57
docker-compose -v # docker-compose version 1.29.2, build 5becea4c

# Run docker compose up to start open api
docker-compose up -d
```

### Accessing Application
Cpmponent         | URL                    | Credentials
---               |------------------------| ---
OpenApi doc      | http://localhost:8100/ |

### Tools
1. Intellij
2. Postman
3. Sourcetree
4. MYSQL Workbench
5. Git


### DOCUMENT
1. Springboot + security
   * https://www.callicoder.com/categories/spring-boot/
   * https://www.callicoder.com/spring-boot-spring-security-jwt-mysql-react-app-part-1/
   * https://kipalog.com/posts/Hu-o--ng-da--n-Spring-Security---JWT--Json-Web-Token----Hibernate


2. API document
   * Swagger
     * https://swagger.io/docs/specification/about/
     * https://topdev.vn/blog/gioi-thieu-swagger-cong-cu-document-cho-restfull-apis/
     * https://topdev.vn/blog/su-dung-swagger-ui-trong-jersey-rest-ws-project/


4. JWT
   * https://viblo.asia/p/tim-hieu-ve-json-web-token-jwt-7rVRqp73v4bP


4. Git
   * https://backlog.com/git-tutorial/vn/contents/


5. SourceTree
   * https://jdomain.vn/huong-dan-su-dung-source-tree/ 