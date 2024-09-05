## Arquitectura del Proyecto
Se trata de un proyecto de Spring Boot creado en IntelliJ / Spring Initializr con las siguientes dependencias:
* Spring Web
* Spring Boot
* Spring Data JPA
* H2 Database (Base de datos en memoria)

## Ejecución de la Aplicación
Abrir el proyecto en IntelliJ

Hacer mvn install

Hacer botón derecho > Run en la clase MicroBloggingApplication

## Acceso a la Base de Datos
Luego de ejecutar la aplicación, podemos ir a http://localhost:8080/h2-console
En el login ingresar usuario, password y url que están en application.properties:
* JDBC URL: jdbc:h2:mem:testdb
* User Name: sa
* Password: password

La Base de Datos en memoria se popula con las sentencias SQL de resources/data.sql

## Para probar los endpoints
Se incluye una colección de Postman en el archivo Microblogging.postman_collection.json

Contiene los siguientes endpoints:
* postTweet, para que un usuario postee un tweet
* follow, para que un usuario siga a otro usuario
* timeline, para ver los tweets que posteó un usuario

## Tests unitarios
Hacer botón derecho en la raíz del proyecto de IntelliJ y clickear en Run all tests,
o ejecutar mvn test

## Cobertura de tests con JaCoCo
Habiendo ejecutado antes mvn clean install, abrir la siguiente página para ver la cobertura de tests:
target / site / jacoco / index.html
