package com.example.mungstragram.comment;

import com.example.mungstragram.post.Post;
import com.example.mungstragram.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class CommentRequest {

    @Data
    public static class CreateDTO {

        @NotBlank(message = "댓글은 필수입니다")
        @Size(min = 1, max = 255, message = "댓글은 최소 1~255 글자까지 작성이 가능합니다")
        private String content;

        public Comment toEntity(User user, Post post) {
            return Comment.builder()
                    .user(user)
                    .post(post)
                    .content(this.content)
                    .build();
        }
    }

}
