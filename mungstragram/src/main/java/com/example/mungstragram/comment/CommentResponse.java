package com.example.mungstragram.comment;

import lombok.Data;

import java.time.LocalDateTime;

public class CommentResponse {

    @Data
    public static class DetailDTO {

        private String nickname;
        private String content;
        private LocalDateTime createdAt;

        public DetailDTO(Comment comment) {
            this.nickname = comment.getUser().getNickname();
            this.content = comment.getContent();
            this.createdAt = comment.getCreatedAt();
        }
    }
}
