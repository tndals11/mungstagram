package com.example.mungstragram.auth;

import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

public class AuthResponse {

    @Data
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class TokenDTO {
        private String tokenType;
        private String accessToken;
        private Integer expiresIn;
        private String refreshToken;
    }

    @Data
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class GoogleUserInfo {
        private String id;
        private String email;
        private String name;
    }
}
