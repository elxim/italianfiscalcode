package mp.codicefiscale;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import lombok.Data;


@ConfigurationProperties(prefix="mp.codicefiscale")
@Validated
@Data
public class CfProperties {

	@NotNull String istatproxyUrl;
	@NotNull String cfVersion;
	
	
}
