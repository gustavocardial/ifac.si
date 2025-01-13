package ifac.si.com.ifac_si_api.service;

import io.minio.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class MinIOService {

    @Autowired
    private MinioClient minioClient;

    public String uploadFile(MultipartFile file) throws Exception {
        String bucketName = "imagens";
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

        // Retorna a URL pública permanente
        return "http://localhost:9000/" + bucketName + "/" + fileName;
    }

    public String getFileUrl(String bucketName, String objectName) throws Exception {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .expiry(60, TimeUnit.MINUTES)
                        .method(Method.GET)
                        .build());
    }

}