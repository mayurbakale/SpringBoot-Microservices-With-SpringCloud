package com.udemy.springbootrestwebservice.swagger;

import java.util.HashSet;
import java.util.Set;

import org.assertj.core.util.Arrays;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import io.swagger.models.Contact;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig extends WebMvcConfigurerAdapter{
	
	private static Contact contact;
	private static Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>();
	
	static {
		contact = new Contact();
		contact.setName("Mayur Bakale");
		contact.setUrl("http://gmail.com/mayurbakale");
		contact.setEmail("mayurbakale@gmail.com");
		
		DEFAULT_PRODUCES_AND_CONSUMES.add("application/json");
		DEFAULT_PRODUCES_AND_CONSUMES.add("application/xml");
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfo("Car Website", "Car Website For Used Cars", "v1.0", "TOS", "Mayur Bakale", "1.0", "http://apache.commons.license");
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo())
				.produces(DEFAULT_PRODUCES_AND_CONSUMES)
				.consumes(DEFAULT_PRODUCES_AND_CONSUMES);
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
          registry
            .addResourceHandler("swagger-ui.html")
            .addResourceLocations("classpath:/META-INF/resources/");
          registry
            .addResourceHandler("/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}

