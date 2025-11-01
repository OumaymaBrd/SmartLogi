package org.example.smartspring.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI smartLogiOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8080/api");
        devServer.setDescription("Serveur de développement");

        Contact contact = new Contact();
        contact.setName("Équipe SmartLogi");
        contact.setEmail("contact@smartlogi.com");

        License license = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("API SmartLogi - Gestion de Livraison")
                .version("1.0.0")
                .contact(contact)
                .description("API REST pour la gestion complète des livraisons, colis, livreurs et zones")
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer));
    }
}
