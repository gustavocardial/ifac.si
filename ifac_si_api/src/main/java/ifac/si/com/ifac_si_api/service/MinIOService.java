package ifac.si.com.ifac_si_api.service;

import ifac.si.com.ifac_si_api.config.MinioConfig;
import io.minio.*;
import io.minio.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class MinIOService {

    private final MinioClient minioClient;
    private final MinioConfig minioConfig;

    public MinIOService(MinioClient minioClient, MinioConfig minioConfig) {
        this.minioClient = minioClient;
        this.minioConfig = minioConfig;
    }

    public String uploadFile(MultipartFile file) throws Exception {
        String bucketName = minioConfig.getBucketName();
        if (bucketName == null || bucketName.isEmpty()) {
            throw new RuntimeException("Bucket name is not set in the application properties.");
        }

        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        // Verifica se o bucket existe
        boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!bucketExists) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }

        // Faz o upload do arquivo
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .stream(file.getInputStream(), file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build()
        );

        return "http://localhost:9000/" + bucketName + "/" + fileName;
    }

    public String getFileUrl(String bucketName, String objectName) throws Exception {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .expiry(7, TimeUnit.DAYS)
                        .method(Method.GET)
                        .build());
    }

    public void deleteFile(String bucketName, String objectName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build());

            log.info("Arquivo {} deletado com sucesso do bucket {}", objectName, bucketName);
        } catch (Exception e) {
            log.error("Erro ao deletar arquivo {}: {}", objectName, e.getMessage());
            throw new RuntimeException("Erro ao deletar arquivo: " + e.getMessage());
        }
    }
}
