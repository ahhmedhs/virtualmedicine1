package com.stackroute.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;


@Configuration
public class SwaggerConfig {

    @Bean
    public Docket swaggerDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.stackroute"))

                .paths(PathSelectors.any())

                .build()
                .apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {
        return new ApiInfo(
                "Appointment Service",
                "Appointment and schedule service",
                "1.0",
                "Terms of Service",
                new Contact("stackroute","www","stackroute@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/",
                Collections.emptyList()
        );	}



}

