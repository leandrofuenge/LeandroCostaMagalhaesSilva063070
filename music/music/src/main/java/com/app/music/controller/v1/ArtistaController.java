package com.app.music.controller.v1;

import com.app.music.dto.ArtistaRequest;
import com.app.music.dto.ArtistaResponse;
import com.app.music.service.ArtistaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/artistas")
@Tag(name = "Artistas", description = "Endpoints para gerenciamento de artistas")
public class ArtistaController {

    private final ArtistaService artistaService;

    public ArtistaController(ArtistaService artistaService) {
        this.artistaService = artistaService;
    }

    @Operation(summary = "Cria um novo artista")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistaResponse criar(@Valid @RequestBody ArtistaRequest request) {
        return artistaService.criar(request);
    }

    @Operation(summary = "Lista todos os artistas")
    @GetMapping
    public List<ArtistaResponse> listarTodos() {
        return artistaService.listarTodos();
    }

    @Operation(summary = "Busca um artista pelo ID")
    @GetMapping("/{id}")
    public ArtistaResponse buscarPorId(@PathVariable Long id) {
        return artistaService.buscarPorId(id);
    }

    @Operation(summary = "Atualiza um artista existente")
    @PutMapping("/{id}")
    public ArtistaResponse atualizar(@PathVariable Long id, @Valid @RequestBody ArtistaRequest request) {
        return artistaService.atualizar(id, request);
    }
}
