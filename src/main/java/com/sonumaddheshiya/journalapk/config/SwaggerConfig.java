package com.sonumaddheshiya.journalapk.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("My Jaurnal Application API Documentation")
                        .version("1.0")
                        .description("Spring Boot REST API using OpenAPI 3")
                        .contact(new Contact()
                                .name("Sonu Maddheshiya")
                                .email("sonumaddheshiya718@gmail.com")
                                .url("https://sonu-portfolioo.netlify.app/"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}