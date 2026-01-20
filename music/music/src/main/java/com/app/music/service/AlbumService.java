package com.app.music.service;

import com.app.music.dto.AlbumRequest;
import com.app.music.dto.AlbumResponse;
import com.app.music.entity.Artista;
import com.app.music.repository.AlbumRepository;
import com.app.music.repository.ArtistaRepository;
import com.app.music.projection.AlbumComArtistaProjection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final ArtistaRepository artistaRepository;

    public AlbumService(AlbumRepository albumRepository,
                        ArtistaRepository artistaRepository) {
        this.albumRepository = albumRepository;
        this.artistaRepository = artistaRepository;
    }

    public AlbumResponse criar(Long artistaId, AlbumRequest request) {

        Artista artista = artistaRepository.findById(artistaId)
                .orElseThrow(() -> new RuntimeException("Artista não encontrado"));

        albumRepository.inserir(
                request.getTitulo(),
                request.getAnoLancamento(),
                request.getTipo(),
                request.getGravadora(),
                request.getDescricao(),
                request.getNumeroFaixas(),
                request.getPreco().doubleValue(),
                artista.getId()
        );

        List<AlbumComArtistaProjection> albuns =
                albumRepository.listarPorArtista(artistaId);

        return mapToResponse(albuns.get(albuns.size() - 1));
    }

    public List<AlbumResponse> listarTodos() {
        return albumRepository.listarTodos()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public AlbumResponse buscarPorId(Long id) {
        AlbumComArtistaProjection album = albumRepository.buscarPorId(id);
        if (album == null) {
            throw new RuntimeException("Álbum não encontrado");
        }
        return mapToResponse(album);
    }

    public List<AlbumResponse> listarPorArtista(Long artistaId) {
        return albumRepository.listarPorArtista(artistaId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private AlbumResponse mapToResponse(AlbumComArtistaProjection p) {
        return new AlbumResponse(
                p.getId(),
                p.getTitulo(),
                p.getAnoLancamento(),
                p.getTipo(),
                p.getGravadora(),
                p.getDescricao(),
                p.getDuracaoTotal(),
                p.getNumeroFaixas(),
                p.getPreco(),
                p.getArtistaId(),
                p.getArtistaNome()
        );
    }
    
    public AlbumResponse atualizar(Long albumId, AlbumRequest request) {

        // 🔎 Verifica se o álbum existe
        AlbumComArtistaProjection existente =
                albumRepository.buscarPorId(albumId);

        if (existente == null) {
            throw new RuntimeException("Álbum não encontrado");
        }

        // 🔄 Atualiza via SQL legado
        albumRepository.atualizar(
                albumId,
                request.getTitulo(),
                request.getAnoLancamento(),
                request.getTipo(),
                request.getGravadora(),
                request.getDescricao(),
                request.getNumeroFaixas(),
                request.getPreco().doubleValue()
        );

        // 🔁 Retorna o álbum atualizado
        AlbumComArtistaProjection atualizado =
                albumRepository.buscarPorId(albumId);

        return mapToResponse(atualizado);
    }
    
    public Page<AlbumResponse> listarAlbums(Pageable pageable) {
        return albumRepository
                .listarPaginado(pageable)
                .map(this::mapToResponse);
    }
    
    public Page<AlbumResponse> listarPorTipoArtista(String tipo, Pageable pageable) {
        return albumRepository
                .listarPorTipoArtista(tipo, pageable)
                .map(this::mapToResponse);
    }
    
    public Page<AlbumResponse> buscarPorNomeArtista(String nome, Pageable pageable) {
        return albumRepository
                .buscarPorNomeArtista(nome, pageable)
                .map(this::mapToResponse);
    }

}
