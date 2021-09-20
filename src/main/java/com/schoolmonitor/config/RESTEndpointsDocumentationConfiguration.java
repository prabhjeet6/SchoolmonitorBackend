/**
 * 
 */
package com.schoolmonitor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Prabhjeet Singh
 *
 *         Sep 20, 2021
 */

@Configuration
@EnableSwagger2
public class RESTEndpointsDocumentationConfiguration {

	@Bean
	public Docket swaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2).select().paths(PathSelectors.ant("/**/*"))
				.apis(RequestHandlerSelectors.basePackage("com.schoolmonitor")).build().apiInfo(apiDetails());

	}

	private ApiInfo apiDetails() {
		return new ApiInfoBuilder().title("Schoolmonitor Backend API ")
				.description("Reference API for Schoolmonitor Backend App").version("1.0")
				.contact(new Contact("Prabhjeet Singh", "https://github.com/prabhjeet6/",
						"prabhjeet.singh92@outlook.com"))
				.build();

	}
}
