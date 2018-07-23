package mp.codicefiscale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {

		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("mp.codicefiscale")).paths(PathSelectors.any())
				.build().apiInfo(apiInfo());

	}

	@SuppressWarnings("deprecation")
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Codice Fiscale Calculator")
				.description("Spring REST per la calcolo e verifica del codice fiscale")
				.termsOfServiceUrl("http://localhost:5000/cf")
				.contact("Michele Pizza").license("Notartel SpA").licenseUrl("mpizza@notariato.it")
				.version("v0.0.1-20170915").build();
	}
}
