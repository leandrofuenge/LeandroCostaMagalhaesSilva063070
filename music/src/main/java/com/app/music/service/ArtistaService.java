package com.app.music.service;

import com.app.music.dto.ArtistaRequest;
import com.app.music.dto.ArtistaResponse;
import com.app.music.entity.Artista;
import com.app.music.repository.ArtistaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistaService {

    private final ArtistaRepository artistaRepository;

    public ArtistaService(ArtistaRepository artistaRepository) {
        this.artistaRepository = artistaRepository;
    }

    // 🔹 CRIAR ARTISTA 
    public ArtistaResponse criar(ArtistaRequest request) {

        artistaRepository.inserir(
                request.getNome(),
                request.getDescricao(),
                request.getDataNascimento(),
                request.getTipo(),
                request.getPaisOrigem(),
                request.getWebsite(),
                request.getRegionalId()
        );

        // 🔹 Retorna o último artista inserido 
        List<Artista> artistas = artistaRepository.listarTodos();
        Artista artista = artistas.get(artistas.size() - 1);

        return mapToResponse(artista);
    }

    // 🔹 LISTAR TODOS
    public List<ArtistaResponse> listarTodos() {
        return artistaRepository.listarTodos()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // 🔹 BUSCAR POR ID
    public ArtistaResponse buscarPorId(Long id) {
        Artista artista = artistaRepository.buscarPorId(id);
        if (artista == null) {
            throw new RuntimeException("Artista não encontrado");
        }
        return mapToResponse(artista);
    }

    // 🔹 ATUALIZAR ARTISTA 
    public ArtistaResponse atualizar(Long id, ArtistaRequest request) {

        Artista existente = artistaRepository.buscarPorId(id);
        if (existente == null) {
            throw new RuntimeException("Artista não encontrado");
        }

        artistaRepository.atualizar(
                id,
                request.getNome(),
                request.getDescricao(),
                request.getDataNascimento(),
                request.getTipo(),
                request.getPaisOrigem(),
                request.getWebsite()
        );

        Artista atualizado = artistaRepository.buscarPorId(id);
        return mapToResponse(atualizado);
    }

    // 🔹 MAPEAMENTO CENTRALIZADO
    private ArtistaResponse mapToResponse(Artista a) {
        return new ArtistaResponse(
                a.getId(),
                a.getNome(),
                a.getDescricao(),
                a.getDataNascimento(),
                a.getTipo(),
                a.getPaisOrigem(),
                a.getWebsite(),
                a.getRegional() != null ? a.getRegional().getId() : null
        );
    }
}
