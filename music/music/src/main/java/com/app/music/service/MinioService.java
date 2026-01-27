package com.app.music.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class MinioService {

    @Value("${minio.endpoint}")
    private String endpoint; // ex: http://minio:9000

    @Value("${minio.public-endpoint}")
    private String publicEndpoint; // ex: http://localhost:9000

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Value("${minio.bucket}")
    private String bucket;

    @Value("${minio.presigned.expiration}")
    private Long presignedExpiration;

    private S3Client s3Client;
    private S3Presigner s3Presigner;

    @PostConstruct
    public void init() {
        AwsBasicCredentials creds =
                AwsBasicCredentials.create(accessKey, secretKey);

        // ===========================
        // S3 CLIENT (uso interno / upload)
        // ===========================
        s3Client = S3Client.builder()
                .endpointOverride(URI.create(endpoint))
                .credentialsProvider(StaticCredentialsProvider.create(creds))
                .region(Region.US_EAST_1)
                .serviceConfiguration(
                        S3Configuration.builder()
                                .pathStyleAccessEnabled(true)
                                .build()
                )
                .build();

        // ===========================
        // 🔥 S3 PRESIGNER (endpoint público)
        // ===========================
        s3Presigner = S3Presigner.builder()
                .endpointOverride(URI.create(publicEndpoint))
                .credentialsProvider(StaticCredentialsProvider.create(creds))
                .region(Region.US_EAST_1)
                .serviceConfiguration(
                        S3Configuration.builder()
                                .pathStyleAccessEnabled(true)
                                .build()
                )
                .build();

        createBucketIfNotExists();
    }

    private void createBucketIfNotExists() {
        try {
            s3Client.headBucket(
                    HeadBucketRequest.builder()
                            .bucket(bucket)
                            .build()
            );
        } catch (Exception e) {
            s3Client.createBucket(
                    CreateBucketRequest.builder()
                            .bucket(bucket)
                            .build()
            );
        }
    }

    // ===========================
    // Upload único
    // ===========================
    public String uploadFile(MultipartFile file, String key) {
        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(
                    request,
                    RequestBody.fromBytes(file.getBytes())
            );

            return key;
        } catch (Exception e) {
            throw new RuntimeException("Falha no upload", e);
        }
    }

    // ===========================
    // Upload múltiplo
    // ===========================
    public List<String> uploadFiles(List<MultipartFile> files, String prefix) {
        List<String> keys = new ArrayList<>();

        for (MultipartFile file : files) {
            String key =
                    prefix + "/" +
                    System.currentTimeMillis() + "-" +
                    file.getOriginalFilename();

            uploadFile(file, key);
            keys.add(key);
        }
        return keys;
    }

    // ===========================
    // Presigned URL (100% correto)
    // ===========================
    public URL generatePresignedUrl(String key) {
        try {
            GetObjectRequest getObjectRequest =
                    GetObjectRequest.builder()
                            .bucket(bucket)
                            .key(key)
                            .build();

            GetObjectPresignRequest presignRequest =
                    GetObjectPresignRequest.builder()
                            .getObjectRequest(getObjectRequest)
                            .signatureDuration(
                                    Duration.ofSeconds(presignedExpiration)
                            )
                            .build();

            return s3Presigner
                    .presignGetObject(presignRequest)
                    .url();

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erro ao gerar URL pré-assinada", e
            );
        }
    }

    // ===========================
    // Presigned URLs múltiplos
    // ===========================
    public List<URL> generatePresignedUrls(List<String> keys) {
        List<URL> urls = new ArrayList<>();
        for (String key : keys) {
            urls.add(generatePresignedUrl(key));
        }
        return urls;
    }
}
