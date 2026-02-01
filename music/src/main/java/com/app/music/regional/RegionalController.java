package com.app.music.regional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/regionais")
@Tag(name = "Regionais", description = "Sincronização de regionais com sistema externo")
public class RegionalController {

    private final RegionalService regionalService;

    public RegionalController(RegionalService regionalService) {
        this.regionalService = regionalService;
    }

    /**
     * Endpoint responsável por sincronizar as regionais
     * com a API externa (ARGUS).
     */
    @PostMapping("/sincronizar")
    @Operation(
        summary = "Sincronizar regionais",
        description = "Sincroniza as regionais a partir do serviço externo, mantendo histórico e versionamento."
    )
    @ApiResponse(responseCode = "204", description = "Sincronização realizada com sucesso")
    @ApiResponse(responseCode = "401", description = "Não autorizado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<Void> sincronizar() {
        regionalService.sincronizarRegionais();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}