package com.stackroute;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@EnableSwagger2
@SpringBootApplication
@EnableEurekaClient
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
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
				"Userservice api",
				"user service",
				"1.0",
				"Terms of Service",
				new Contact("stackroute","www","stackroute@gmail.com"),
				"Apache License Version 2.0",
				"https://www.apache.org/licenses/",
				Collections.emptyList()
		);	}

}
