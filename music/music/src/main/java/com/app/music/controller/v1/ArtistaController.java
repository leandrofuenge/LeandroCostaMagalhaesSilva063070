package com.app.music.controller.v1;

import com.app.music.dto.ArtistaRequest;
import com.app.music.dto.ArtistaResponse;
import com.app.music.service.ArtistaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/artistas")
@Tag(
    name = "Artistas",
    description = "Endpoints para criação, consulta e atualização de artistas"
)
public class ArtistaController {

    private final ArtistaService artistaService;

    public ArtistaController(ArtistaService artistaService) {
        this.artistaService = artistaService;
    }

    @Operation(
        summary = "Cria um novo artista",
        description = "Cadastra um novo artista no sistema"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Artista criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistaResponse criar(
            @Valid @RequestBody ArtistaRequest request
    ) {
        return artistaService.criar(request);
    }

    @Operation(
        summary = "Lista todos os artistas",
        description = "Retorna todos os artistas cadastrados"
    )
    @GetMapping
    public List<ArtistaResponse> listarTodos() {
        return artistaService.listarTodos();
    }

    @Operation(
        summary = "Busca artista por ID",
        description = "Retorna os dados de um artista específico"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Artista encontrado"),
        @ApiResponse(responseCode = "404", description = "Artista não encontrado")
    })
    @GetMapping("/{id}")
    public ArtistaResponse buscarPorId(
            @Parameter(description = "ID do artista", required = true)
            @PathVariable Long id
    ) {
        return artistaService.buscarPorId(id);
    }

    @Operation(
        summary = "Atualiza um artista",
        description = "Atualiza os dados de um artista existente"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Artista atualizado"),
        @ApiResponse(responseCode = "404", description = "Artista não encontrado")
    })
    @PutMapping("/{id}")
    public ArtistaResponse atualizar(
            @Parameter(description = "ID do artista", required = true)
            @PathVariable Long id,
            @Valid @RequestBody ArtistaRequest request
    ) {
        return artistaService.atualizar(id, request);
    }
}
