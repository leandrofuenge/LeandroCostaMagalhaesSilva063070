package com.app.music.controller.v1;

import com.app.music.dto.AlbumRequest;
import com.app.music.dto.AlbumResponse;
import com.app.music.service.AlbumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/api/v1/albuns")
@Tag(
    name = "Álbuns",
    description = "Endpoints para gerenciamento de álbuns e upload de capas"
)
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    // ===========================
    // Criar álbum
    // ===========================
    @Operation(summary = "Cria um novo álbum para um artista")
    @PostMapping("/artista/{artistaId}")
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumResponse criar(
            @PathVariable Long artistaId,
            @Valid @RequestBody AlbumRequest request
    ) {
        return albumService.criar(artistaId, request);
    }

    // ===========================
    // Listagens
    // ===========================
    @Operation(summary = "Lista todos os álbuns")
    @GetMapping("/all")
    public List<AlbumResponse> listarTodos() {
        return albumService.listarTodos();
    }

    @Operation(summary = "Busca um álbum pelo ID")
    @GetMapping("/{id}")
    public AlbumResponse buscarPorId(@PathVariable Long id) {
        return albumService.buscarPorId(id);
    }

    @Operation(summary = "Lista álbuns de um artista específico")
    @GetMapping("/artista/{artistaId}")
    public List<AlbumResponse> listarPorArtista(@PathVariable Long artistaId) {
        return albumService.listarPorArtista(artistaId);
    }

    @Operation(summary = "Lista álbuns com paginação")
    @GetMapping("/paginacao")
    public ResponseEntity<Page<AlbumResponse>> listarComPaginacao(Pageable pageable) {
        return ResponseEntity.ok(albumService.listarAlbums(pageable));
    }

    @Operation(summary = "Lista álbuns por tipo de artista (CANTOR ou BANDA)")
    @GetMapping("/tipo-artista")
    public ResponseEntity<Page<AlbumResponse>> listarPorTipoArtista(
            @RequestParam String tipo,
            Pageable pageable
    ) {
        return ResponseEntity.ok(albumService.listarPorTipoArtista(tipo, pageable));
    }

    @Operation(summary = "Busca álbuns pelo nome do artista")
    @GetMapping("/buscar-por-artista")
    public ResponseEntity<Page<AlbumResponse>> buscarPorNomeArtista(
            @RequestParam String nome,
            Pageable pageable
    ) {
        return ResponseEntity.ok(albumService.buscarPorNomeArtista(nome, pageable));
    }

    // ===========================
    // Atualizar álbum
    // ===========================
    @Operation(summary = "Atualiza um álbum existente")
    @PutMapping("/{id}")
    public AlbumResponse atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AlbumRequest request
    ) {
        return albumService.atualizar(id, request);
    }

    // ===========================
    // Upload de capas do álbum
    // ===========================
    @Operation(
        summary = "Upload de capas do álbum",
        description = "Faz upload de uma ou mais imagens de capa para um álbum específico no MinIO"
    )
    @PostMapping(
        value = "/{albumId}/capas",
        consumes = "multipart/form-data"
    )
    public ResponseEntity<List<URL>> uploadCapasDoAlbum(
            @Parameter(description = "ID do álbum", required = true)
            @PathVariable Long albumId,

            @Parameter(
                description = "Uma ou mais imagens de capa do álbum",
                required = true
            )
            @RequestParam("files") List<MultipartFile> files
    ) {
        return ResponseEntity.ok(
                albumService.uploadCapas(albumId, files)
        );
    }
}
