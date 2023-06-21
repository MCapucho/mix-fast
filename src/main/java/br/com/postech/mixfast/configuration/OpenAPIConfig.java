package br.com.postech.mixfast.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Value("${mix-fast.openapi.dev-url}")
    private String devUrl;

    @Bean
    public OpenAPI myOpenAPI() {
        Server devServer = new Server();
        devServer.setUrl(devUrl);
        devServer.setDescription("URL do servidor local");

        Contact contact = new Contact();
        contact.setEmail("murylo_capucho@outlook.com");
        contact.setName("Murylo Capucho Ribeiro");
        contact.setUrl("https://www.linkedin.com/in/murylo-capucho-ribeiro-b185b6a3/");

        Info info = new Info()
                .title("API Mix Fast")
                .version("1.0.0")
                .contact(contact)
                .description("API fast food para controle de pedidos (MIX-FAST)");

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}
