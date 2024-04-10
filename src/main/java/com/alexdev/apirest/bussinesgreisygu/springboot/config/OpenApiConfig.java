package com.alexdev.apirest.bussinesgreisygu.springboot.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public Contact contactOpenApi(){
        return new Contact()
                .name("Alex Lopez")
                .email("alexxxd23@gmail.com")
                .url("https://www.linkedin.com/in/alexanderlopezz/");
    }
    @Bean
    public Info infoOpenApi(){
        return new Info()
                .title("Springboot GreisyGu$ API REST")
                .description("Springboot project for Maxikiosco GreisyGu$")
                .version("v1.0")
                .contact(contactOpenApi());
    }
    @Bean
    public OpenAPI configOpenApi(){
        return new OpenAPI().info(infoOpenApi());
    }
}
