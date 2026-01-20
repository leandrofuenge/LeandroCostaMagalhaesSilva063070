package com.app.music.controller.v1;

import com.app.music.dto.AlbumRequest;
import com.app.music.dto.AlbumResponse;
import com.app.music.service.AlbumService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/albuns")
public class AlbumController {

    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    // 🔹 CRIAR ÁLBUM (LEGADO)
    @PostMapping("/artista/{artistaId}")
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumResponse criar(
            @PathVariable Long artistaId,
            @Valid @RequestBody AlbumRequest request
    ) {
        return albumService.criar(artistaId, request);
    }

    // 🔹 LISTAR TODOS
    @GetMapping
    public List<AlbumResponse> listarTodos() {
        return albumService.listarTodos();
    }

    // 🔹 BUSCAR POR ID
    @GetMapping("/{id}")
    public AlbumResponse buscarPorId(@PathVariable Long id) {
        return albumService.buscarPorId(id);
    }

    // 🔹 LISTAR POR ARTISTA
    @GetMapping("/artista/{artistaId}")
    public List<AlbumResponse> listarPorArtista(@PathVariable Long artistaId) {
        return albumService.listarPorArtista(artistaId);
    }

    // 🔹 ATUALIZAR ÁLBUM (LEGADO)
    @PutMapping("/{id}")
    public AlbumResponse atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AlbumRequest request
    ) {
        return albumService.atualizar(id, request);
    }
}
