package com.app.music.service;

import com.app.music.dto.ArtistaRequest;
import com.app.music.dto.ArtistaResponse;
import com.app.music.entity.Artista;
import com.app.music.repository.ArtistaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ArtistaServiceTest {

    @InjectMocks
    private ArtistaService artistaService;

    @Mock
    private ArtistaRepository artistaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateArtistSuccessfully() {
        ArtistaRequest request = new ArtistaRequest();
        request.setNome("Linkin Park");
        request.setDescricao("Banda de rock");
        request.setDataNascimento(LocalDate.of(1996, 1, 1));
        request.setTipo("Rock");
        request.setPaisOrigem("EUA");
        request.setWebsite("https://linkinpark.com");
        request.setRegionalId(1L);

        Artista artistaMock = new Artista();
        artistaMock.setId(10L);
        artistaMock.setNome(request.getNome());
        artistaMock.setDescricao(request.getDescricao());
        artistaMock.setDataNascimento(request.getDataNascimento());
        artistaMock.setTipo(request.getTipo());
        artistaMock.setPaisOrigem(request.getPaisOrigem());
        artistaMock.setWebsite(request.getWebsite());

        // Mock do repository
        when(artistaRepository.listarTodos()).thenReturn(List.of(artistaMock));

        ArtistaResponse response = artistaService.criar(request);

        assertThat(response.getId()).isEqualTo(10L);
        assertThat(response.getNome()).isEqualTo("Linkin Park");

        verify(artistaRepository, times(1)).inserir(
                eq(request.getNome()),
                eq(request.getDescricao()),
                eq(request.getDataNascimento()),
                eq(request.getTipo()),
                eq(request.getPaisOrigem()),
                eq(request.getWebsite()),
                eq(request.getRegionalId())
        );
    }

    @Test
    void shouldListAllArtists() {
        Artista a1 = new Artista();
        a1.setId(1L);
        a1.setNome("A1");

        Artista a2 = new Artista();
        a2.setId(2L);
        a2.setNome("A2");

        when(artistaRepository.listarTodos()).thenReturn(List.of(a1, a2));

        List<ArtistaResponse> result = artistaService.listarTodos();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getNome()).isEqualTo("A1");
        assertThat(result.get(1).getNome()).isEqualTo("A2");
    }

    @Test
    void shouldReturnArtistById() {
        Artista artista = new Artista();
        artista.setId(1L);
        artista.setNome("Linkin Park");

        when(artistaRepository.buscarPorId(1L)).thenReturn(artista);

        ArtistaResponse result = artistaService.buscarPorId(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getNome()).isEqualTo("Linkin Park");
    }

    @Test
    void shouldThrowWhenArtistNotFoundById() {
        when(artistaRepository.buscarPorId(1L)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> artistaService.buscarPorId(1L));
    }

    @Test
    void shouldUpdateArtistSuccessfully() {
        Artista existente = new Artista();
        existente.setId(1L);

        ArtistaRequest request = new ArtistaRequest();
        request.setNome("Nova Banda");
        request.setDescricao("Descrição Atualizada");
        request.setDataNascimento(LocalDate.of(2000, 1, 1));
        request.setTipo("Pop");
        request.setPaisOrigem("Brasil");
        request.setWebsite("https://novabanda.com");

        Artista atualizado = new Artista();
        atualizado.setId(1L);
        atualizado.setNome(request.getNome());
        atualizado.setDescricao(request.getDescricao());
        atualizado.setDataNascimento(request.getDataNascimento());
        atualizado.setTipo(request.getTipo());
        atualizado.setPaisOrigem(request.getPaisOrigem());
        atualizado.setWebsite(request.getWebsite());

        when(artistaRepository.buscarPorId(1L)).thenReturn(existente).thenReturn(atualizado);

        ArtistaResponse result = artistaService.atualizar(1L, request);

        assertThat(result.getNome()).isEqualTo("Nova Banda");
        assertThat(result.getDescricao()).isEqualTo("Descrição Atualizada");

        verify(artistaRepository, times(1)).atualizar(
                eq(1L),
                eq(request.getNome()),
                eq(request.getDescricao()),
                eq(request.getDataNascimento()),
                eq(request.getTipo()),
                eq(request.getPaisOrigem()),
                eq(request.getWebsite())
        );
    }

    @Test
    void shouldThrowWhenUpdatingNonExistingArtist() {
        when(artistaRepository.buscarPorId(1L)).thenReturn(null);

        ArtistaRequest request = new ArtistaRequest();
        assertThrows(RuntimeException.class, () -> artistaService.atualizar(1L, request));
    }
}
