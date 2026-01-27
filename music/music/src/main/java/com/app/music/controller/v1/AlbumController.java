package com.app.music.controller.v1;

import com.app.music.dto.AlbumRequest;
import com.app.music.dto.AlbumResponse;
import com.app.music.service.AlbumService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
        description = "Endpoints para gerenciamento de álbuns musicais e upload de capas no MinIO"
)
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @Operation(
            summary = "Criar álbum",
            description = "Cria um novo álbum associado a um artista específico"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Álbum criado com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AlbumResponse.class)
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Artista não encontrado")
    })
    @PostMapping("/artista/{artistaId}")
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumResponse criar(
            @Parameter(
                    description = "ID do artista ao qual o álbum pertence",
                    example = "1",
                    required = true
            )
            @PathVariable Long artistaId,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do álbum",
                    required = true,
                    content = @Content(schema = @Schema(implementation = AlbumRequest.class))
            )
            @Valid @RequestBody AlbumRequest request
    ) {
        return albumService.criar(artistaId, request);
    }

    @Operation(
            summary = "Listar todos os álbuns",
            description = "Retorna uma lista com todos os álbuns cadastrados"
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping("/all")
    public List<AlbumResponse> listarTodos() {
        return albumService.listarTodos();
    }

    @Operation(
            summary = "Buscar álbum por ID",
            description = "Busca um álbum específico pelo seu identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Álbum encontrado"),
            @ApiResponse(responseCode = "404", description = "Álbum não encontrado")
    })
    @GetMapping("/{id}")
    public AlbumResponse buscarPorId(
            @Parameter(description = "ID do álbum", example = "10")
            @PathVariable Long id
    ) {
        return albumService.buscarPorId(id);
    }

    @Operation(
            summary = "Listar álbuns por artista",
            description = "Retorna todos os álbuns associados a um artista específico"
    )
    @GetMapping("/artista/{artistaId}")
    public List<AlbumResponse> listarPorArtista(
            @Parameter(description = "ID do artista", example = "3")
            @PathVariable Long artistaId
    ) {
        return albumService.listarPorArtista(artistaId);
    }

    @Operation(
            summary = "Listar álbuns com paginação",
            description = "Retorna os álbuns de forma paginada"
    )
    @GetMapping("/paginacao")
    public ResponseEntity<Page<AlbumResponse>> listarComPaginacao(
            @Parameter(hidden = true) Pageable pageable
    ) {
        return ResponseEntity.ok(albumService.listarAlbums(pageable));
    }

    @Operation(
            summary = "Listar álbuns por tipo de artista",
            description = "Filtra álbuns pelo tipo de artista (CANTOR ou BANDA)"
    )
    @GetMapping("/tipo-artista")
    public ResponseEntity<Page<AlbumResponse>> listarPorTipoArtista(
            @Parameter(
                    description = "Tipo do artista",
                    example = "BANDA",
                    required = true
            )
            @RequestParam String tipo,

            @Parameter(hidden = true)
            Pageable pageable
    ) {
        return ResponseEntity.ok(albumService.listarPorTipoArtista(tipo, pageable));
    }

    @Operation(
            summary = "Buscar álbuns pelo nome do artista",
            description = "Busca álbuns com base no nome (ou parte do nome) do artista"
    )
    @GetMapping("/buscar-por-artista")
    public ResponseEntity<Page<AlbumResponse>> buscarPorNomeArtista(
            @Parameter(
                    description = "Nome do artista",
                    example = "Linkin Park",
                    required = true
            )
            @RequestParam String nome,

            @Parameter(hidden = true)
            Pageable pageable
    ) {
        return ResponseEntity.ok(albumService.buscarPorNomeArtista(nome, pageable));
    }

    @Operation(
            summary = "Atualizar álbum",
            description = "Atualiza os dados de um álbum existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Álbum atualizado"),
            @ApiResponse(responseCode = "404", description = "Álbum não encontrado")
    })
    @PutMapping("/{id}")
    public AlbumResponse atualizar(
            @Parameter(description = "ID do álbum", example = "5")
            @PathVariable Long id,

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Novos dados do álbum",
                    required = true,
                    content = @Content(schema = @Schema(implementation = AlbumRequest.class))
            )
            @Valid @RequestBody AlbumRequest request
    ) {
        return albumService.atualizar(id, request);
    }

    @Operation(
            summary = "Upload de capas do álbum",
            description = "Faz upload de uma ou mais imagens de capa para um álbum específico. " +
                          "Os arquivos são armazenados no MinIO e retornam URLs públicas/presigned."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Capas enviadas com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = URL.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Álbum não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro ao enviar arquivos")
    })
    @PostMapping(
            value = "/{albumId}/capas",
            consumes = "multipart/form-data"
    )
    public ResponseEntity<List<URL>> uploadCapasDoAlbum(
            @Parameter(
                    description = "ID do álbum",
                    example = "12",
                    required = true
            )
            @PathVariable Long albumId,

            @Parameter(
                    description = "Arquivos de imagem da capa do álbum (jpg, png, webp, etc)",
                    required = true
            )
            @RequestParam("files") List<MultipartFile> files
    ) {
        return ResponseEntity.ok(
                albumService.uploadCapas(albumId, files)
        );
    }
}