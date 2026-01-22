package com.app.music.controller.v1;

import com.app.music.service.MinioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    description = "Endpoints para upload de arquivos e geração de links pré-assinados no MinIO"
)
public class MinioController {

    private final MinioService minioService;

    public MinioController(MinioService minioService) {
        this.minioService = minioService;
    }

    // ===========================
    // Upload de UM arquivo
    // ===========================
    @Operation(
        summary = "Upload de um arquivo",
        description = "Faz upload de um único arquivo para o MinIO e retorna um link pré-assinado para acesso",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Upload realizado com sucesso",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        example = "{ \"url\": \"http://localhost:9000/music/tracks/arquivo.png?...\" }"
                    )
                )
            ),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
        }
    )
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    public ResponseEntity<Map<String, URL>> uploadFile(
            @Parameter(
                description = "Arquivo a ser enviado",
                required = true
            )
            @RequestParam("file") MultipartFile file,

            @Parameter(
                description = "Prefixo (pasta) dentro do bucket",
                example = "images",
                required = true
            )
            @RequestParam("prefix") String prefix
    ) {
        String key = prefix + "/" + System.currentTimeMillis() + "-" + file.getOriginalFilename();

        minioService.uploadFile(file, key);
        URL presignedUrl = minioService.generatePresignedUrl(key);

        return ResponseEntity.ok(Map.of("url", presignedUrl));
    }

    // ===========================
    // Upload de VÁRIOS arquivos
    // ===========================
    @Operation(
        summary = "Upload de múltiplos arquivos",
        description = "Faz upload de vários arquivos para o MinIO e retorna uma lista de links pré-assinados",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Uploads realizados com sucesso"
            ),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
        }
    )
    @PostMapping(value = "/upload-multiple", consumes = "multipart/form-data")
    public ResponseEntity<List<URL>> uploadMultipleFiles(
            @Parameter(
                description = "Lista de arquivos a serem enviados",
                required = true
            )
            @RequestParam("files") List<MultipartFile> files,

            @Parameter(
                description = "Prefixo (pasta) dentro do bucket",
                example = "tracks",
                required = true
            )
            @RequestParam("prefix") String prefix
    ) {
        List<String> keys = minioService.uploadFiles(files, prefix);
        List<URL> urls = minioService.generatePresignedUrls(keys);

        return ResponseEntity.ok(urls);
    }

    // ===========================
    // Gerar link pré-assinado manualmente
    // ===========================
    @Operation(
        summary = "Gerar link pré-assinado",
        description = "Gera manualmente um link pré-assinado para um arquivo existente no MinIO",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Link gerado com sucesso",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                        example = "{ \"url\": \"http://localhost:9000/music/images/arquivo.png?...\" }"
                    )
                )
            ),
            @ApiResponse(responseCode = "404", description = "Arquivo não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
        }
    )
    @GetMapping("/presigned")
    public ResponseEntity<Map<String, URL>> getPresignedUrl(
            @Parameter(
                description = "Chave do arquivo no bucket (sem o nome do bucket)",
                example = "images/1705900000000-imagem.png",
                required = true
            )
            @RequestParam("key") String key
    ) {
        URL url = minioService.generatePresignedUrl(key);
        return ResponseEntity.ok(Map.of("url", url));
    }
}
