package projectlink.configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;


@Configuration
public class SpringDocConfiguration {
    @Bean
    OpenAPI openAPI() {
        return new OpenAPI().info(
                new Info()
                        .title("프로젝트 링크 API 명세서")
                        .description("프로젝트링크 REST API 서비스")
                        .version("v1.0")
                        .license(
                                new License()
                                        .name("Apache 2.0")
                                        .url("http://myapiserver.com"))

        );
    }
}
