 
package com.spoon.config;

/**
 *
 * @author User
 */
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Spoonacular Recipe API Gateway")
                .version("1.0.0")
                .description("Middleware for searching and viewing recipes from the Spoonacular API.")
            );
    }
}