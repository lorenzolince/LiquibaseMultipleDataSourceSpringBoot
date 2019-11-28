/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.llh.liquibase.conf;
import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/**
 *
 * @author lorenzolince
 */
@Profile("swagger-enabled")
@Configuration
@EnableSwagger2
public class SwaggerConfig {
        @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(
                        Arrays.asList(new ParameterBuilder()
                                .name("schemaName")
                                .description("change dynamic schema")
                                .modelRef(new ModelRef("string"))
                                .parameterType("query")
                                .required(true)
                                .build())).select()
                .apis(RequestHandlerSelectors.basePackage("com.llh.liquibase"))
                .paths(PathSelectors.ant("/api/**"))
                .build()
                .apiInfo(getApiInfo());
    }
 

    private ApiInfo getApiInfo() {
        return new ApiInfo("Example schedule",
                "Example enable or disable schedule ",
                "1.0",
                "https://pa.linkedin.com/in/lorenzo-lince-453b5433/",
                new Contact("Lorenzo Lince", "https://pa.linkedin.com/in/lorenzo-lince-453b5433/", "lorenzolince@gmail.com"),
                "01-SNAPSHOT", null,
                new ArrayList<VendorExtension>());
    }
}
