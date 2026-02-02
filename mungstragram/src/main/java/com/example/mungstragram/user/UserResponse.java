package com.example.mungstragram.user;

import lombok.Data;

import java.time.LocalDateTime;

public class UserResponse {

    @Data
    public static class DetailDTO {
        private String nickname;
        private LocalDateTime createdAt;

        public DetailDTO(User user) {
            this.nickname = user.getNickname();
            this.createdAt = user.getCreatedAt();
        }
    }

}
