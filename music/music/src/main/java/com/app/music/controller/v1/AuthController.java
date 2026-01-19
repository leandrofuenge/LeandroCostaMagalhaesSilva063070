package com.app.music.controller.v1;

import com.app.music.security.JwtService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/token")
    public Map<String, String> token(@RequestParam String user) {
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken
        );
    }

    @PostMapping("/refresh")
    public Map<String, String> refresh(@RequestParam String refreshToken) {
        if (!jwtService.isValid(refreshToken)) {
            throw new RuntimeException("Refresh token inválido ou expirado");
        }

        String user = jwtService.getSubject(refreshToken);
        String newAccessToken = jwtService.generateToken(user);

        return Map.of("accessToken", newAccessToken);
    }
}
