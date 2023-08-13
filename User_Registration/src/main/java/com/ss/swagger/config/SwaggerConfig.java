package com.ss.swagger.config;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class SwaggerConfig 
{
	private static final String AUTHORIZATION_HEADER="Authorization";
	
	private ApiKey apiKeys()
	{
		return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
		
	}
	
	private List<SecurityContext> securityContext()
	{
		return Arrays.asList(SecurityContext.builder().securityReferences(securityReferences()).build());
		
	}
	
	private List<SecurityReference> securityReferences()
	{
		AuthorizationScope scope=new AuthorizationScope("global", "accessEveryThinks");
		return Arrays.asList(new SecurityReference("JWT",new AuthorizationScope[] {scope}));
	}
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(metaData())
				.securityContexts(securityContext())
				.securitySchemes(Arrays.asList(apiKeys()))
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.ss"))
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo metaData() {
		return new ApiInfo("User Registration :String Boot", "this project is developed by ss teams", "1.0", "Terms of service",
				new Contact("Sculpt soft", "https://www.sculptsoft.com/", "info@sculptsoft.com"), "License APIS",
				"APIS", Collections.emptyList());
	}

}
