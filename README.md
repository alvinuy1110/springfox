SpringFox Demo
==============

This is to demo springfox with with Spring Boot.  The objective is to produce Swagger 2 specifications
from the code.

Swagger Setup
=============
# Add dependency

```
  <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-swagger2</artifactId>
            <version>2.9.2</version>
        </dependency>
```

# Enable Code Scan

Add the annotation "EnableSwagger2".

# API Spec URL

The api specs are accessible in "/v2/api-docs".

Example:

```
http://localhost:8080/v2/api-docs
```

# Enable UI

##  Add the dependency

```
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.9.2</version>
</dependency>
```

## UI Url

```
http://localhost:8080/swagger-ui.html
```

Note: Be careful of CORS is the UI is not in the same domain

References
==========

* https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
