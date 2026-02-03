package com.example.mungstragram.comment;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

public class CommentResponse {

    @Data
    public static class DetailDTO {
        private Long id;
        private String nickname;
        private String content;
        private LocalDateTime createdAt;
        private List<DetailDTO> children;

        public DetailDTO(Comment comment) {
            this.id = comment.getId();
            this.nickname = comment.getUser().getNickname();
            this.content = comment.getContent();
            this.createdAt = comment.getCreatedAt();
            this.children = comment.getChildren().stream()
                    .map(DetailDTO::new)
                    .toList();
        }
    }
}
