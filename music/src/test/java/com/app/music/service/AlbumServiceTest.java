package com.app.music.service;

import com.app.music.dto.AlbumRequest;
import com.app.music.dto.AlbumResponse;
import com.app.music.entity.Artista;
import com.app.music.event.AlbumCreatedEvent;
import com.app.music.projection.AlbumComArtistaProjection;
import com.app.music.repository.AlbumRepository;
import com.app.music.repository.ArtistaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AlbumServiceTest {

    @InjectMocks
    private AlbumService albumService;

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private ArtistaRepository artistaRepository;

    @Mock
    private MinioService minioService;

    @Mock
    private org.springframework.context.ApplicationEventPublisher eventPublisher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateAlbumAndPublishEvent() {
        Long artistaId = 1L;

        AlbumRequest request = new AlbumRequest();
        request.setTitulo("Hybrid Theory");
        request.setAnoLancamento(2000);
        request.setTipo("Rock");
        request.setGravadora("Warner");
        request.setDescricao("Descrição");
        request.setNumeroFaixas(12);
        request.setPreco(BigDecimal.valueOf(29.90)); 


        Artista artista = new Artista();
        artista.setId(artistaId);
        artista.setNome("Linkin Park");

        AlbumComArtistaProjection proj = mock(AlbumComArtistaProjection.class);
        when(proj.getId()).thenReturn(10L);

        when(artistaRepository.findById(artistaId)).thenReturn(Optional.of(artista));
        when(albumRepository.listarPorArtista(artistaId)).thenReturn(List.of(proj));

        AlbumResponse result = albumService.criar(artistaId, request);

        assertThat(result.getId()).isEqualTo(10L);

        // Verifica se o evento foi publicado
        verify(eventPublisher, times(1)).publishEvent(any(AlbumCreatedEvent.class));
        // Verifica se inseriu no repositório
        verify(albumRepository, times(1)).inserir(
                eq(request.getTitulo()),
                eq(request.getAnoLancamento()),
                eq(request.getTipo()),
                eq(request.getGravadora()),
                eq(request.getDescricao()),
                eq(request.getNumeroFaixas()),
                eq(request.getPreco().doubleValue()),
                eq(artistaId)
        );
    }

    @Test
    void shouldUploadAlbumCovers() throws Exception {
        Long albumId = 10L;

        AlbumComArtistaProjection album = mock(AlbumComArtistaProjection.class);
        when(albumRepository.buscarPorId(albumId)).thenReturn(album);

        MultipartFile file1 = mock(MultipartFile.class);
        MultipartFile file2 = mock(MultipartFile.class);

        List<String> keys = List.of("albums/10/covers/file1.png", "albums/10/covers/file2.png");
        when(minioService.uploadFiles(List.of(file1, file2), "albums/10/covers")).thenReturn(keys);

        URL url1 = new URL("http://localhost:9000/albums/10/covers/file1.png");
        URL url2 = new URL("http://localhost:9000/albums/10/covers/file2.png");

        when(minioService.generatePresignedUrls(keys)).thenReturn(List.of(url1, url2));

        List<URL> result = albumService.uploadCapas(albumId, List.of(file1, file2));

        assertThat(result).containsExactly(url1, url2);

        verify(minioService, times(1)).uploadFiles(List.of(file1, file2), "albums/10/covers");
        verify(minioService, times(1)).generatePresignedUrls(keys);
    }

    @Test
    void shouldReturnAlbumById() {
        AlbumComArtistaProjection proj = mock(AlbumComArtistaProjection.class);
        when(proj.getId()).thenReturn(10L);
        when(albumRepository.buscarPorId(10L)).thenReturn(proj);

        AlbumResponse result = albumService.buscarPorId(10L);

        assertThat(result.getId()).isEqualTo(10L);
    }

    @Test
    void shouldListAllAlbums() {
        AlbumComArtistaProjection proj1 = mock(AlbumComArtistaProjection.class);
        AlbumComArtistaProjection proj2 = mock(AlbumComArtistaProjection.class);

        when(albumRepository.listarTodos()).thenReturn(List.of(proj1, proj2));

        List<AlbumResponse> result = albumService.listarTodos();

        assertThat(result).hasSize(2);
    }
}
