## Product Micro Service

The goal of this project is to implement a RESTfull API, following [Leonard Richardson maturity model](https://martinfowler.com/articles/richardsonMaturityModel.html). 

* Level 0
* Level 1 - Resources
* Level 2 - HTTP Verbs 
* Level 3 - HATEOAS (Hypertext As The Engine Of Application State)

### There are three ways to start API. Choose One and go ahead
* Using Maven Tool in test environment
* By Java command line in production environment
* Through Docker Image / Container in production environment

#### Starting application in development mode
To start the application using Maven build tool run the command below:

```console
$ mvn spring-boot:run -Dspring-boot.run.profiles=test
```

**Note:** Running application with test profile. 

### Starting application in production mode by command line
To start the application using jar package run the below commands:

```console
$ cd <path_to_project_root_directory>
$ mvn clean package
$ java -jar -Dspring.profiles.active=prod -DPRODUCT_MS_DATABASE_URL=jdbc:h2:mem:product_ms -DPRODUCT_MS_DATABASE_USERNAME=sa -DPRODUCT_MS_DATABASE_PASSWORD=  target/product-ms.jar
```

### Starting application through Docker Image/Container in production mode
Create the Docker image
```console
$ docker build -t fferreira/product-ms .
```

Verify whether docker image has been generated
```console
$ docker images
```

Create Docker container and run application through it
```console
$ docker run --name fferreira_product_ms -p 9999:9999 -e SPRING_PROFILES_ACTIVE=prod -e PRODUCT_MS_DATABASE_URL='jdbc:h2:mem:product_ms;' -e PRODUCT_MS_DATABASE_USERNAME='sa' -e PRODUCT_MS_DATABASE_PASSWORD='' fferreira/product-ms
```

Check whether container is running
```console
$ docker ps
```

Stop container
```console
$ docker stop fferreira_product_ms
```

Start a container already created
```console
$ docker start fferreira_product_ms
```

### Access API documentation 
To access the api documentation access the url below after start application.

URL: http://localhost:9999/swagger-ui.html


### API health

To monitor API health, send a get to the url below:

URL: http://localhost:9999/actuator

The **/actuator** resource exposes a list of another resources regard to API details:

E.g:
* http://localhost:9999/actuator/info
* http://localhost:9999/actuator/health


### JaCoCo test coverage

The below command will test application and generate the `jacoco.exec` binary file used by tools like Sonarqube.

```console
$ mvn test
```

The `jacoco.exec` binary file is not readable by humans, so if you would like to get a report in other format (Example: HTML) run the below command.
```console
$ mvn jacoco:report
```
**Note:** It is necessary generate `jacoco.exec` file before generate humans readable report.
