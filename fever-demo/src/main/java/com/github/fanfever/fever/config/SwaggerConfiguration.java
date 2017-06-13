package com.github.fanfever.fever.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author fanfever
 * @email fanfeveryahoo@gmail.com
 * @url https://github.com/fanfever
 * @date 2016年7月9日
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
	// Configuration of Swagger mainly centers around the Docket bean.
	@Bean
	public Docket api() {

		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("cn.udesk.cases.web.controller"))
				.paths(PathSelectors.any()).build().apiInfo(apiInfo());
		// .globalResponseMessage(RequestMethod.GET,
		// responseMessage(RequestMethod.GET))
		// .globalResponseMessage(RequestMethod.POST,
		// responseMessage(RequestMethod.POST));
	}

	@SuppressWarnings("unused")
	private List<springfox.documentation.service.ResponseMessage> responseMessage(RequestMethod requestMethod) {
		switch (requestMethod) {
		case GET:
			return Arrays.asList(
					new ResponseMessageBuilder().code(HttpStatus.NO_CONTENT.value()).message(HttpStatus.NO_CONTENT.getReasonPhrase()).build());
		case POST:
			return Arrays.asList(new ResponseMessageBuilder().code(HttpStatus.CONFLICT.value()).message(HttpStatus.NO_CONTENT.getReasonPhrase())
					.responseModel(new ModelRef("Error")).build());
		default:
			break;
		}
		return null;
	}

	private ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfo("CRM OPEN API", "Secure RESTful Services with Maven(Guide) and Spring Boot", "0.0.2-SNAPSHOT", null,
				new Contact("zhangfan", "https://github.com/fanfever", "fanfeveryahoo@gmail.com"), "", "");
		return apiInfo;
	}

}
