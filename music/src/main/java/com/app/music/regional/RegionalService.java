package com.app.music.regional;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.app.music.integration.ArgusClient;
import com.app.music.regional.dto.RegionalDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RegionalService {

    private final RegionalRepository regionalRepository;
    private final ArgusClient argusClient;

    public RegionalService(RegionalRepository regionalRepository,
                           ArgusClient argusClient) {
        this.regionalRepository = regionalRepository;
        this.argusClient = argusClient;
    }

    /**
     * Sincroniza as regionais com a API externa (ARGUS),
     * mantendo histórico e versionamento conforme edital.
     */
    @Transactional
    public void sincronizarRegionais() {

        List<RegionalDTO> regionaisExternas = argusClient.buscarRegionais();

        Map<Integer, RegionalDTO> externasMap = regionaisExternas.stream()
                .collect(Collectors.toMap(
                        RegionalDTO::getId,
                        dto -> dto
                ));

        List<RegionalEntity> regionaisAtivas = regionalRepository.findByAtivoTrue();

        Map<Integer, RegionalEntity> internasMap = regionaisAtivas.stream()
                .collect(Collectors.toMap(
                        RegionalEntity::getExternalId,
                        r -> r
                ));

        for (RegionalDTO dto : regionaisExternas) {

            RegionalEntity atual = internasMap.get(dto.getId());

            if (atual == null) {
                salvarNovaRegional(dto);
                continue;
            }

            if (!atual.getNome().equals(dto.getNome())) {
                regionalRepository.inativarPorExternalId(dto.getId());
                salvarNovaRegional(dto);
            }
        }

        Set<Integer> externalIdsExternos = externasMap.keySet();

        for (RegionalEntity regional : regionaisAtivas) {
            if (!externalIdsExternos.contains(regional.getExternalId())) {
                regional.setAtivo(false);
                regionalRepository.save(regional);
            }
        }
    }

    private void salvarNovaRegional(RegionalDTO dto) {
        RegionalEntity nova = new RegionalEntity();
        nova.setExternalId(dto.getId());
        nova.setNome(dto.getNome());
        nova.setAtivo(true);
        regionalRepository.save(nova);
    }
    
}
    