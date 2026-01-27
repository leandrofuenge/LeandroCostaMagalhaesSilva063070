package com.app.music.controller.v1;

import com.app.music.service.MinioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/files")
@Tag(
        name = "Arquivos (MinIO)",
        description = "Endpoints para upload de arquivos e geração de URLs pré-assinadas via MinIO (S3 compatível)"
)
public class MinioController {

    private final MinioService minioService;

    public MinioController(MinioService minioService) {
        this.minioService = minioService;
    }

    // =====================================================
    // Upload único
    // =====================================================
    @Operation(
            summary = "Upload de um arquivo",
            description = """
                    Realiza o upload de um arquivo para o MinIO e retorna uma URL pré-assinada
                    para acesso temporário ao conteúdo.
                    
                    A URL gerada possui tempo de expiração configurável.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Arquivo enviado com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    example = """
                                    {
                                      "url": "http://localhost:9000/music/albums/1/covers/arquivo.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&..."
                                    }
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno ao realizar upload"
            )
    })
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, URL>> upload(
            @Parameter(
                    description = "Arquivo a ser enviado",
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE)
            )
            @RequestParam("file") MultipartFile file,

            @Parameter(
                    description = "Prefixo (diretório virtual) onde o arquivo será armazenado no bucket",
                    example = "albums/1/covers",
                    required = true
            )
            @RequestParam("prefix") String prefix
    ) {
        String key = prefix + "/" + System.currentTimeMillis() + "-" + file.getOriginalFilename();
        minioService.uploadFile(file, key);

        return ResponseEntity.ok(
                Map.of("url", minioService.generatePresignedUrl(key))
        );
    }

    // =====================================================
    // Upload múltiplo
    // =====================================================
    @Operation(
            summary = "Upload de múltiplos arquivos",
            description = """
                    Realiza o upload de vários arquivos para o MinIO e retorna
                    uma lista de URLs pré-assinadas para acesso temporário.
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Arquivos enviados com sucesso",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    example = """
                                    [
                                      "http://localhost:9000/music/albums/1/covers/arquivo1.png?...",
                                      "http://localhost:9000/music/albums/1/covers/arquivo2.png?..."
                                    ]
                                    """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno ao realizar upload"
            )
    })
    @PostMapping(value = "/upload-multiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<URL>> uploadMultiple(
            @Parameter(
                    description = "Lista de arquivos a serem enviados",
                    required = true
            )
            @RequestParam("files") List<MultipartFile> files,

            @Parameter(
                    description = "Prefixo (diretório virtual) onde os arquivos serão armazenados no bucket",
                    example = "albums/1/covers",
                    required = true
            )
            @RequestParam("prefix") String prefix
    ) {
        return ResponseEntity.ok(
                minioService.generatePresignedUrls(
                        minioService.uploadFiles(files, prefix)
                )
        );
    }
}
