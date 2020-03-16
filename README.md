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

## Support for JSR-303

When using bean validation, add this.

```
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-bean-validators</artifactId>
    <version>2.9.2</version>
</dependency>
```

Then in your java config import "BeanValidatorPluginsConfiguration.class"

Example:
```
@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
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

Packages
========

|Package|Description  |
|-------|-------------|
|config |spring config|
|domain| pojo for REST|
|controller|REST controllers|


<a name="swagger-parts"/>Swagger Parts   
==============

# Api Info

This gives a friendly info of what the API is about.

Example:
```
return new Docket(DocumentationType.SWAGGER_2)
                .select()
                ...
                .apiInfo(apiInfo())
                ;
                
 private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Spring Boot REST API")
                .description("Sample Springfox REST API")
                .contact(new Contact("Test  User", "www.someUrl.com", "testUser@email.com"))
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0.0")
                .build();
    }
```

# Path info

Select path base on filter to be documented.

Example:

```
 return new Docket(DocumentationType.SWAGGER_2)
                ...
                .paths(paths())
                
 private Predicate<String> paths() {

        // filter all paths
//        return PathSelectors.any();

        // filter only selected path
        return PathSelectors.regex("/api/.*");
    }
               
```

# Global Response

This section describes common response code

Example:
```
new Docket(DocumentationType.SWAGGER_2)
                .globalResponseMessage(RequestMethod.GET, globalResponses)
                .globalResponseMessage(RequestMethod.POST, globalResponses)
                .globalResponseMessage(RequestMethod.DELETE, globalResponses)
                .globalResponseMessage(RequestMethod.PATCH, globalResponses)
                .globalResponseMessage(RequestMethod.PUT, globalResponses)
                
                ...
                
    // Provide a standard global error response
    private List<ResponseMessage> globalResponseMessages() {
        List<ResponseMessage> globalResponses = Arrays.asList(
                new ResponseMessageBuilder().code(200).message("OK").build(),
                new ResponseMessageBuilder().code(400).message("Bad Request").build(),
                new ResponseMessageBuilder().code(401).message("Unauthenticated").build(),
                new ResponseMessageBuilder().code(403).message("Unauthorized").build(),
                new ResponseMessageBuilder().code(404).message("Not found").build(),
                new ResponseMessageBuilder().code(500).message("Internal Error").build()
        );
        return globalResponses;
                
```

## Global Response with Model

### Add the additional models
```
 TypeResolver typeResolver = new TypeResolver();
        //@formatter:off
        return new Docket(DocumentationType.SWAGGER_2)
         .globalResponseMessage(RequestMethod.GET, globalResponses)
                        .globalResponseMessage(RequestMethod.POST, globalResponses)
                        .globalResponseMessage(RequestMethod.DELETE, globalResponses)
                        .globalResponseMessage(RequestMethod.PATCH, globalResponses)
                        .globalResponseMessage(RequestMethod.PUT, globalResponses)
   ...
                .additionalModels(typeResolver.resolve(ApiError.class))

```
### Add the response model

```
private List<ResponseMessage> globalResponseMessages() {
        List<ResponseMessage> globalResponses = Arrays.asList(
                new ResponseMessageBuilder().code(200).message("OK").build(),
                new ResponseMessageBuilder().code(400).message("Bad Request").responseModel(new ModelRef("ApiError")).build(),
                new ResponseMessageBuilder().code(401).message("Unauthenticated").responseModel(new ModelRef("ApiError")).build(),
                new ResponseMessageBuilder().code(403).message("Unauthorized").responseModel(new ModelRef("ApiError")).build(),
                new ResponseMessageBuilder().code(404).message("Not found").responseModel(new ModelRef("ApiError")).build(),
                new ResponseMessageBuilder().code(500).message("Internal Error").responseModel(new ModelRef("ApiError")).build()
        );
        return globalResponses;

    }

```

Simple REST Controller
======================

Assuming you have a basic REST controller with CRUD operations.

# Controller name

By default the name is derived using controller name. Example controller name is "StudentController", display is "student-controller".

To override, use the @Api annotation at the controller class level.

```

@Controller
@RequestMapping(value = "/api/simple")
@Api(tags="Customer Controller", description = "some desc")
public class StudentController {
```

# Response object

Add the pojo in the ResponseEntity return.

Example:
```
@RequestMapping(value = "/student/{id}", method = RequestMethod.GET)
    public ResponseEntity<Student> get(@PathVariable(name="id") int id) {
```

## Jackson

When using Jackson, Jackson annotations will be honored.

### Example 1: JSON  properties

public class Student {

```
    @JsonProperty("studentId")
    private int id;

    @JsonIgnore
    private String firstName;
```     

### Example 2: Using custom serializer

```
public class Employee {

    @JsonSerialize(using = CustomDateSerializer.class)
    private Date startDate;
```

### Example 3: Using custom object mapper

When annotating is not possible, you can use an object mapper.  You will not be able to use other features like JSR-303 and Model annotation for this object.

```
@Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule("SimpleModule");
        simpleModule.addSerializer(Location.class, new CustomLocationSerializer());
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

```
# Operation

The operation uses the method name by default. To customize, use "@ApiOperation"

Example:
```
@ApiOperation(value = "Find student by id",  notes = "some note")
```
where:
* value is the name of the operation
* notes is the description
* tags is to group into logical group instead of by controller     

## Input Pojo

We will use a POST example for this.

The name will be using the variable for the POJO.

```
@RequestMapping(value = "/students", method = RequestMethod.POST)
    public ResponseEntity<Student> create(@RequestBody CreateStudentRequest createStudentRequest) {

        Student student = createStudent(1);
        student.setFirstName(createStudentRequest.getFirstName());
        student.setLastName(createStudentRequest.getLastName());
        return ResponseEntity.status(201).body(student);
    }
```

# Custom API Response

You can add custom response at the method/ operation level.

Example:
```
@ApiResponses(value = {
            @ApiResponse(code = 422, message = "processing error", response = ApiError.class)})
```

# Customize Model

Add the annotation "ApiModelProperty" to the property
Example:
```
@ApiModelProperty(value = "first name", example = "John")
    @NotEmpty
    private String firstName;
```
where:
* value - descriptive name instead of the field name
* example - some value for user to infer

# Security

## Define the API Key

Create the way the api key.  This could be ApiKey, Basic Auth, OAuth.

Example: Custom header token
```
    private ApiKey apiKey() {
        return new ApiKey("auth_token", "auth-token", "header");
    }
```

## Define the Security Context

Here we define the link to the API Key and which paths to apply the security to.
Important to review the "forPaths(...)" to define the paths.

Example:
```
  private SecurityContext securityContext() {
        // apply security to what paths
        return SecurityContext.builder().securityReferences(this.defaultAuth()).forPaths(PathSelectors.any()).forPaths(PathSelectors.regex("/api/.*")).build();
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope
                = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(
                // the key must match the name of the ApiKey
                new SecurityReference("auth_token", authorizationScopes));
    }
```

## Configure Into SpringFox

Register the settings for SpringFox.

Example:
```
return new Docket(DocumentationType.SWAGGER_2)
                .....
                // adding security
                .securitySchemes(newArrayList(apiKey()))
                .securityContexts(newArrayList(securityContext()))

```

# Support for Params in Operations

## Using Request Param / Request Header

Example:
```
 @ApiOperation(value = "Find employee",  notes = "this is the description")
    @RequestMapping(value = "/employees/param", method = RequestMethod.GET)
    public ResponseEntity<Employee> getEmployeeByParam(
            @RequestHeader("User-Agent") String userAgent,
            @RequestParam(name = "param1", required = true, defaultValue = "abc") String param1
    ,@RequestParam(name = "param2", required = false) String param2
    ) throws Exception {
```

References
==========

* https://springfox.github.io/springfox/docs/snapshot/
* https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api
* https://dzone.com/articles/spring-boot-2-restful-api-documentation-with-swagg
* https://www.vojtechruzicka.com/documenting-spring-boot-rest-api-swagger-springfox/

