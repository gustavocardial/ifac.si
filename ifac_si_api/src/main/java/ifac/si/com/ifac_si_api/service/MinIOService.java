package ifac.si.com.ifac_si_api.service;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import io.minio.PresignedGetObjectArgs;
import java.time.Duration;

@Service
public class MinioService {

    private MinioClient minioClient;

    public void uploadFile(String bucketName, String objectName, InputStream inputStream,
                           String contentType) throws Exception {
        // Cria bucket se não existir
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }

        // Upload do arquivo
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(inputStream, inputStream.available(), -1)
                        .contentType(contentType)
                        .build());
    }

    public String getFileUrl(String bucketName, String objectName) throws Exception {
        return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .method(Method.GET)
                        .expiry(60, TimeUnit.MINUTES)
                        .build());
    }
}