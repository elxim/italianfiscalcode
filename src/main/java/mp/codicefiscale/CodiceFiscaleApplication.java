package mp.codicefiscale;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;


@Controller
@SpringBootApplication
@EnableConfigurationProperties(CfProperties.class)
@ComponentScan("mp.codicefiscale")
public class CodiceFiscaleApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodiceFiscaleApplication.class, args);
	}
}
