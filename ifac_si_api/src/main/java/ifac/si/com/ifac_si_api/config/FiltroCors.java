package ifac.si.com.ifac_si_api.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class FiltroCors {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();

        corsConfig.setAllowedOrigins(Arrays.asList(
                "http://127.0.0.1:5500",
                "http://localhost:4200",
                "https://localhost:4200"
        ));

        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
        corsConfig.setExposedHeaders(List.of("Authorization"));
        corsConfig.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", corsConfig);

        return new CorsFilter(configSource);
    }
}
