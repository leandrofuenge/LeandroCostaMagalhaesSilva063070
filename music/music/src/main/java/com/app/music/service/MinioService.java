package com.app.music.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.S3Configuration;

import jakarta.annotation.PostConstruct;
import java.net.URL;
import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class MinioService {

    @Value("${minio.endpoint}")
    private String endpoint;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Value("${minio.bucket}")
    private String bucket;

    @Value("${minio.presigned.expiration}")
    private Long presignedExpiration; // em segundos

    private S3Client s3Client;
    private S3Presigner s3Presigner;

    @PostConstruct
    public void init() {
        AwsBasicCredentials creds = AwsBasicCredentials.create(accessKey, secretKey);

        s3Client = S3Client.builder()
                .endpointOverride(URI.create(endpoint))
                .credentialsProvider(StaticCredentialsProvider.create(creds))
                .region(Region.US_EAST_1)  // MinIO não exige região específica
                .serviceConfiguration(S3Configuration.builder()
                        .pathStyleAccessEnabled(true) // necessário para MinIO
                        .build())
                .build();

        s3Presigner = S3Presigner.builder()
                .endpointOverride(URI.create(endpoint))
                .credentialsProvider(StaticCredentialsProvider.create(creds))
                .region(Region.US_EAST_1)
                .build();
    }

    // ===========================
    // Upload de um arquivo
    // ===========================
    public String uploadFile(MultipartFile file, String key) {
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
            return key;
        } catch (Exception e) {
            throw new RuntimeException("Falha no upload do arquivo: " + e.getMessage(), e);
        }
    }

    // ===========================
    // Upload múltiplo de arquivos
    // ===========================
    public List<String> uploadFiles(List<MultipartFile> files, String prefix) {
        List<String> keys = new ArrayList<>();

        for (MultipartFile file : files) {
            String key = prefix + "/" + System.currentTimeMillis() + "-" + file.getOriginalFilename();
            uploadFile(file, key);
            keys.add(key);
        }

        return keys;
    }

    // ===========================
    // Gerar link pré-assinado único
    // ===========================
    public URL generatePresignedUrl(String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .getObjectRequest(getObjectRequest)
                .signatureDuration(Duration.ofSeconds(presignedExpiration))
                .build();

        return s3Presigner.presignGetObject(presignRequest).url();
    }

    // ===========================
    // Gerar links pré-assinados múltiplos
    // ===========================
    public List<URL> generatePresignedUrls(List<String> keys) {
        List<URL> urls = new ArrayList<>();
        for (String key : keys) {
            urls.add(generatePresignedUrl(key));
        }
        return urls;
    }
}
