package com.app.music.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.net.URL;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MinioServiceTest {

    @InjectMocks
    private MinioService minioService;

    @Mock
    private S3Client s3Client;

    @Mock
    private S3Presigner s3Presigner;

    @Mock
    private MultipartFile multipartFile;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ReflectionTestUtils.setField(minioService, "bucket", "music");
        ReflectionTestUtils.setField(minioService, "presignedExpiration", 1800L);
        ReflectionTestUtils.setField(minioService, "endpoint", "http://minio:9000");
        ReflectionTestUtils.setField(minioService, "publicEndpoint", "http://localhost:9000");
    }

    @Test
    void shouldUploadFileSuccessfully() throws Exception {
        when(multipartFile.getBytes()).thenReturn("file-content".getBytes());
        when(multipartFile.getContentType()).thenReturn("image/png");

        String key = "albums/test.png";

        String result = minioService.uploadFile(multipartFile, key);

        assertThat(result).isEqualTo(key);

        verify(s3Client, times(1))
                .putObject(any(PutObjectRequest.class), any(RequestBody.class));
    }

    @Test
    void shouldUploadMultipleFiles() throws Exception {
        MultipartFile file1 = mock(MultipartFile.class);
        MultipartFile file2 = mock(MultipartFile.class);

        when(file1.getBytes()).thenReturn("1".getBytes());
        when(file2.getBytes()).thenReturn("2".getBytes());

        when(file1.getContentType()).thenReturn("image/png");
        when(file2.getContentType()).thenReturn("image/png");

        when(file1.getOriginalFilename()).thenReturn("a.png");
        when(file2.getOriginalFilename()).thenReturn("b.png");

        List<String> keys =
                minioService.uploadFiles(List.of(file1, file2), "albums");

        assertThat(keys).hasSize(2);

        verify(s3Client, times(2))
                .putObject(any(PutObjectRequest.class), any(RequestBody.class));
    }

    @Test
    void shouldGeneratePresignedUrlSuccessfully() throws Exception {
        URL expectedUrl = new URL("http://localhost:9000/music/albums/test.png");

        PresignedGetObjectRequest presigned = mock(PresignedGetObjectRequest.class);
        when(presigned.url()).thenReturn(expectedUrl);

        when(s3Presigner.presignGetObject(any(GetObjectPresignRequest.class)))
                .thenReturn(presigned);

        URL result = minioService.generatePresignedUrl("albums/test.png");

        assertThat(result).isEqualTo(expectedUrl);
    }
}
