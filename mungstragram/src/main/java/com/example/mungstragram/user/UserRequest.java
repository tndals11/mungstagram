package com.example.mungstragram.user;

import com.example.mungstragram._common.enums.user.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class UserRequest {

    @Data
    public static class CreateDTO {
        @NotBlank(message = "아이디는 필수입니다")
        @Size(min = 8, max = 50, message = "아이디는 8~50 글자 사이여야 합니다")
        private String username;

        @NotBlank(message = "비밀번호는 필수입니다")
        @Size(min = 8, max = 100, message = "비밀번호는 8~100 글자 사이여야합니다")
        private String password;

        @NotBlank(message = "별명은 필수입니다")
        @Size(min = 3, max = 100, message = "별명은 3~30 글자 사이여야합니다")
        private String nickname;

        /**
         * toEntity: request요청으로 받아온 값을 바로 Entity에 때려서 저장시킨다
         */
        public User toEntity() {
            return User.builder()
                    .username(this.username)
                    .password(this.password)
                    .nickname(this.nickname)
                    .status(Status.ACTIVE)
                    .build();
        }
    }

    @Data
    public static class UpdateDTO {
        @NotBlank(message = "별명은 필수입니다")
        @Size(min = 3, max = 100, message = "별명은 3~30 글자 사이여야합니다")
        private String nickname;
    }
}
