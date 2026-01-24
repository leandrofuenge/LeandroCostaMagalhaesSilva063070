package com.app.music.dto;

public class TokenResponseDTO {

    private String accessToken;
    private String tokenType;
    private Long expiresIn;
    private String refreshToken;

    public TokenResponseDTO(
            String accessToken,
            String tokenType,
            Long expiresIn,
            String refreshToken
    ) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.refreshToken = refreshToken;
    }

}
