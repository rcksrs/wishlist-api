package com.rcksrs.wishlist.infra;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        var contact = new Contact()
                .name("Eryck Soares")
                .url("https://www.linkedin.com/in/erycksrs/");

        var info = new Info()
                .title("Wishlist API")
                .description("API para gerenciamento de produtos na wishlist")
                .version("1.0.0")
                .contact(contact);

        return new OpenAPI().info(info);
    }
}
