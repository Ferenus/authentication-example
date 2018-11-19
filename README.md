# Spring Boot / React / JWT Login Page example
If you are looking for a way to connect React, Spring Boot and Spring Security, this is the place to start. There are 
few examples available for Java and React, but most of them are old or not easy to build. So, I've decided to make my own proof of concept. 
Whole project deploy to single jar file. You can also run it in a development mode with a hot reload.

For a frontend we're using React and Redux. For a backend we're using Java, Maven, Spring Boot, Spring Security, H2. The JWT signing is 
done by Java JWT library (https://github.com/jwtk/jjwt). 

## Server Side
We're publishing three REST API endpoints: "/api/authentication" to login (JWT signing), "/api/users/register" to register and "/api/tasks" which can be accessed only by authenticated users. 
In addition, it has an H2 database containing 2 users (email: user@gmail.com; pass: user / email: tomcat@gmail.com; pass: tomcat).

The verification is done in the JwtAuthenticationTokenFilter. If a correct token isn't found the 401 http response is returned. 
If a correct token is found, the Authentication object is added to the Spring Security Context.

## Client Side
The simple React app serves login page and registration page. On successful login it, the user is redirected to the main page.

## Setup
- Install node version 11.0.0
- Install npm version 6.4.1
- Install Java jdk 1.8
- Install Maven 3.x
- Add Java and Maven to the env variable PATH

## Install
to run production build:
- mvn clean install
- java -jar target/huro-0.0.1-SNAPSHOT.jar
- URL access: http://localhost:8080

hot reload:
- mvn spring-boot:run or debug in IntelliJ
- cd src/main/webapp
- npm start
- URL access: http://localhost:3000
