package ifac.si.com.ifac_si_api.config;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint("http://localhost:9000") // Endpoint do MinIO
                .credentials("admin", "admin123") // Credenciais do MinIO
                .build();
    }
}