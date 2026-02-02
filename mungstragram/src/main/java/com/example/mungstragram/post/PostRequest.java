package com.example.mungstragram.post;

import com.example.mungstragram.pet.Pet;
import com.example.mungstragram.user.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PostRequest {

    @Data
    public static class CreateDTO {

        @NotNull(message = "펫 ID는 필수입니다")
        private Long petId;

        @NotBlank(message = "게시글 제목은 필수입니다")
        @Size(min = 1, max = 255, message = "제목의 길이는 1~255 사이입니다")
        private String title;

        @NotBlank(message = "게시글 내용은 필수입니다")
        private String content;

        @NotNull(message = "게시글 이미지는 필수입니다")
        @Size(min = 1, max = 5, message = "이미지는 1~5개 까지만 업로드 가능합니다")
        private List<MultipartFile> images;

      public Post toEntity(User user, Pet pet) {
        return Post.builder()
                .user(user)
                .pet(pet)
                .title(this.title)
                .content(this.content)
                .build();
      }
    }
}
