package com.example.mungstragram.post;

import com.example.mungstragram._common.enums.pet.Gender;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

public class PostResponse {

    @Data
    public static class DetailDTO {
        private String nickname;
        private String name;
        private Integer age;
        private String breed;
        private String description;
        private Gender gender;
        private String title;
        private String content;
        private List<String> fileImages;
        private Integer likeCount;
        private boolean isOwner;
        private boolean isLiked;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public DetailDTO(Post post, boolean isOwner, boolean isLiked) {
            this.nickname = post.getUser().getNickname();
            this.name = post.getPet().getName();
            this.age = post.getPet().getAge();
            this.breed = post.getPet().getBreed();
            this.description = post.getPet().getDescription();
            this.gender = post.getPet().getGender();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.fileImages = post.getImages().stream()
                    .map(postImage -> "/images/" + postImage.getImageUrl())
                    .toList();
            this.isOwner = isOwner;
            this.isLiked = isLiked;
            this.likeCount = post.getLikeCount();
            this.createdAt = post.getCreatedAt();
            this.updatedAt = post.getUpdatedAt();
        }
    }

    @Data
    public static class ListDTO {
        private Long id;
        private String nickname;
        private String profileImage;
        private String title;
        private String name;
        private LocalDateTime createdAt;

        public ListDTO(Post post) {
            this.id = post.getId();
            this.nickname = post.getUser().getNickname();
            this.profileImage = "/images/" + post.getPet().getProfileImage();
            this.title = post.getTitle();
            this.name = post.getPet().getName();
            this.createdAt = post.getCreatedAt();
        }
    }

}
