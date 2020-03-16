package com.myproject.springfox.config;


import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicate;
import com.myproject.springfox.domain.ApiError;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {
    @Bean
    public Docket api() {

        List<ResponseMessage> globalResponses = globalResponseMessages();
        TypeResolver typeResolver = new TypeResolver();
        //@formatter:off
        return new Docket(DocumentationType.SWAGGER_2)
                .globalResponseMessage(RequestMethod.GET, globalResponses)
                .globalResponseMessage(RequestMethod.POST, globalResponses)
                .globalResponseMessage(RequestMethod.DELETE, globalResponses)
                .globalResponseMessage(RequestMethod.PATCH, globalResponses)
                .globalResponseMessage(RequestMethod.PUT, globalResponses)
                .additionalModels(typeResolver.resolve(ApiError.class))

                // adding security
                .securitySchemes(newArrayList(apiKey()))
                .securityContexts(newArrayList(securityContext()))

                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(paths())


                .build()
                .apiInfo(apiInfo())
                ;
        //@formatter:on
    }

    // Describes the api info
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Spring Boot REST API")
                .description("Sample Springfox REST API")
                .contact(new Contact("Test  User", "www.someUrl.com", "testUser@email.com"))
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0.0")
                .build();
    }

    private Predicate<String> paths() {

        // filter all paths
//        return PathSelectors.any();

        // filter only selected path
        return PathSelectors.regex("/api/.*");
    }

    // Provide a standard global error response
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


    private ApiKey apiKey() {
        return new ApiKey("auth_token", "auth-token", "header");
    }

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
}