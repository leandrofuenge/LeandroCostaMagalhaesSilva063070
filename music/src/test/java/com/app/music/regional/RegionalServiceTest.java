package com.app.music.regional;

import com.app.music.integration.ArgusClient;
import com.app.music.regional.dto.RegionalDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegionalServiceTest {

    @Mock
    private RegionalRepository regionalRepository;

    @Mock
    private ArgusClient argusClient;

    @InjectMocks
    private RegionalService regionalService;

    @Test
    void deveCriarNovaRegionalQuandoNaoExistirInternamente() {
        RegionalDTO dto = new RegionalDTO();
        dto.setId(1);
        dto.setNome("Regional Norte");

        when(argusClient.buscarRegionais()).thenReturn(List.of(dto));
        when(regionalRepository.findByAtivoTrue()).thenReturn(List.of());

        regionalService.sincronizarRegionais();

        ArgumentCaptor<RegionalEntity> captor =
                ArgumentCaptor.forClass(RegionalEntity.class);

        verify(regionalRepository).save(captor.capture());

        RegionalEntity salva = captor.getValue();
        assertThat(salva.getExternalId()).isEqualTo(1);
        assertThat(salva.getNome()).isEqualTo("Regional Norte");
        assertThat(salva.getAtivo()).isTrue();
    }

    @Test
    void deveVersionarRegionalQuandoNomeForAlterado() {
        RegionalDTO dtoExterno = new RegionalDTO();
        dtoExterno.setId(1);
        dtoExterno.setNome("Regional Sul Nova");

        RegionalEntity regionalAtual = new RegionalEntity();
        regionalAtual.setExternalId(1);
        regionalAtual.setNome("Regional Sul");
        regionalAtual.setAtivo(true);

        when(argusClient.buscarRegionais()).thenReturn(List.of(dtoExterno));
        when(regionalRepository.findByAtivoTrue())
                .thenReturn(List.of(regionalAtual));

        regionalService.sincronizarRegionais();

        verify(regionalRepository)
                .inativarPorExternalId(1);

        verify(regionalRepository, times(1))
                .save(any(RegionalEntity.class));
    }

    @Test
    void deveInativarRegionalQuandoNaoVierMaisDaApiExterna() {
        RegionalEntity regionalInterna = new RegionalEntity();
        regionalInterna.setExternalId(1);
        regionalInterna.setNome("Regional Centro");
        regionalInterna.setAtivo(true);

        when(argusClient.buscarRegionais()).thenReturn(List.of());
        when(regionalRepository.findByAtivoTrue())
                .thenReturn(List.of(regionalInterna));

        regionalService.sincronizarRegionais();

        assertThat(regionalInterna.getAtivo()).isFalse();

        verify(regionalRepository)
                .save(regionalInterna);
    }
}
