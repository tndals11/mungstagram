package com.example.mungstragram.pet;

import com.example.mungstragram._common.enums.pet.Gender;
import com.example.mungstragram.user.User;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class PetRequest {

    @Data
    public static class CreateDTO {

        @NotBlank(message = "강아지 이름은 필수입니다")
        @Size(min = 1, max = 50, message = "이름은 1~50자 사이입니다")
        String name;

        @NotNull(message = "강아지 나이는 필수입니다")
        @Min(value = 0, message = "강아지는 0살 이상이어야합니다")
        @Max(value = 30, message = "나이가 너무 많습니다")
        private Integer age;

        @NotBlank(message = "강아지 종은 필수입니다")
        @Size(min = 2, max = 100, message = "종은 1~100자 사이입니다")
        private String breed;

        @NotBlank(message = "강아지 특징은 필수입니다")
        @Size(min = 1, max = 100, message = "특징은 1~100자 사이입니다")
        private String description;

        @NotNull(message = "이미지는 필수입니다")
        private MultipartFile profileImage;

        @NotNull(message = "성별은 필수입니다")
        private Gender gender;

        public Pet toEntity(User user, String profileImage) {
            return Pet.builder()
                    .user(user)
                    .name(this.name)
                    .age(this.age)
                    .breed(this.breed)
                    .description(this.description)
                    .gender(this.gender)
                    .profileImage(profileImage)
                    .build();
        }
    }

    @Data
    public static class DeleteDTO {
        @NotNull(message = "1개의 아이디는 필수입니다")
        private List<Long> petIds;
    }

    @Data
    public static class UpdateDTO {

        @Size(min = 1, max = 50, message = "이름은 1~50자 사이입니다")
        String name;

        @Min(value = 0, message = "강아지는 0살 이상이어야합니다")
        @Max(value = 30, message = "나이가 너무 많습니다")
        private Integer age;

        @Size(min = 2, max = 100, message = "종은 1~100자 사이입니다")
        private String breed;

        @Size(min = 1, max = 100, message = "특징은 1~100자 사이입니다")
        private String description;

        private MultipartFile profileImage;
    }

}
