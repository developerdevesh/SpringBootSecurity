package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

    private static final String SWAGGER_API_VERSION = "1.0";

    private static final String LICENSE_TEXT = "License";

    private static final String title = "User Car REST API";

    private static final String description = "docs for test user car Framework";

    @Bean
    public Docket initializeUserCarDocket() {

        return new Docket(DocumentationType.SWAGGER_2).groupName("user-car-api").select().apis(RequestHandlerSelectors.basePackage("com.example.demo"))
                // .paths(Predicates.and(Predicates.not(Predicates.or(ant("/error.*"))), Predicates.or(ant("/v1.*"))))
                .paths(PathSelectors.any()).build().apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(title).description(description).license(LICENSE_TEXT).version(SWAGGER_API_VERSION).build();
    }

}
