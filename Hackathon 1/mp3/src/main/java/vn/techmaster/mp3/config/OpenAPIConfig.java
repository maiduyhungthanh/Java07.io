package vn.techmaster.mp3.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenAPIConfig {
  @Bean
	public OpenAPI customOpenAPI(
			@Value("${application-description}") String appDesciption,
			@Value("${application-version}") String appVersion) {

		return new OpenAPI()
			.info(new Info().title("Bank REST API Document")
			.version(appVersion)
			.contact(new Contact().name("Mai Thiet Yet").email("maiduyhungthanh@gmail.com").url("https://techmaster.vn"))
			.description(appDesciption)
			.termsOfService("http://swagger.io/terms/")
			.license(new License().name("Apache 2.0")
			.url("http://springdoc.org")));
	}

  @Bean
  public GroupedOpenApi bankOpenApi() {
    String paths[] = {"/api/**"};
    return GroupedOpenApi.builder().group("bank").pathsToMatch(paths)
          .build();
  }


}
