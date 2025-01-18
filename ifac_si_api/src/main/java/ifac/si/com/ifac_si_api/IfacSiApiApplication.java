package ifac.si.com.ifac_si_api;

import ifac.si.com.ifac_si_api.config.MinioConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(MinioConfig.class)
public class IfacSiApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(IfacSiApiApplication.class, args);
	}

}
