package com.app.music.controller.v1;

import com.app.music.dto.ArtistaRequest;
import com.app.music.dto.ArtistaResponse;
import com.app.music.service.ArtistaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/artistas")
public class ArtistaController {

    private final ArtistaService artistaService;

    public ArtistaController(ArtistaService artistaService) {
        this.artistaService = artistaService;
    }

    // 🔹 CRIAR
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistaResponse criar(@RequestBody ArtistaRequest request) {
        return artistaService.criar(request);
    }

    // 🔹 LISTAR TODOS
    @GetMapping
    public List<ArtistaResponse> listarTodos() {
        return artistaService.listarTodos();
    }

    // 🔹 BUSCAR POR ID
    @GetMapping("/{id}")
    public ArtistaResponse buscarPorId(@PathVariable Long id) {
        return artistaService.buscarPorId(id);
    }

    // 🔹 ATUALIZAR
    @PutMapping("/{id}")
    public ArtistaResponse atualizar(
            @PathVariable Long id,
            @RequestBody ArtistaRequest request
    ) {
        return artistaService.atualizar(id, request);
    }
}
