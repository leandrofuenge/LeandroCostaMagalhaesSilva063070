package com.app.music.controller.v1;

import com.app.music.security.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(
    name = "Autenticação",
    description = "Endpoints para geração e renovação de tokens JWT"
)
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    // ===========================
    // Gerar token
    // ===========================
    @Operation(
        summary = "Gera tokens JWT",
        description = "Gera um access token e um refresh token para um usuário"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Tokens gerados com sucesso"),
        @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    @PostMapping("/token")
    public Map<String, String> token(
            @Parameter(
                description = "Identificador do usuário (username ou e-mail)",
                required = true,
                example = "user@email.com"
            )
            @RequestParam String user
    ) {
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        );
    }

    // ===========================
    // Refresh token
    // ===========================
    @Operation(
        summary = "Renova o access token",
        description = "Gera um novo access token a partir de um refresh token válido"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Access token renovado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Refresh token inválido ou expirado")
    })
    @PostMapping("/refresh")
    public Map<String, String> refresh(
            @Parameter(
                description = "Refresh token JWT válido",
                required = true
            )
            @RequestParam String refreshToken
    ) {
        if (!jwtService.isValid(refreshToken)) {
            throw new RuntimeException("Refresh token inválido ou expirado");
        }

        String user = jwtService.getSubject(refreshToken);
        String newAccessToken = jwtService.generateToken(user);

        return Map.of("accessToken", newAccessToken);
    }
}
