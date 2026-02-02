package com.example.mungstragram._common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_INPUT(400, "U000", "잘못된 입력값입니다."),
    ACCESS_DENIED(403, "U003", "접근 권한이 없습니다"),
    DUPLICATE_USERNAME(409, "U009", "이미 사용 중인 아이디입니다."),
    DUPLICATE_NICKNAME(409, "U009", "이미 사용 중인 별명입니다."),
    USER_NOT_FOUND(404, "U004", "존재하지않는 사용자입니다."),
    USER_WITHDRAWN(403, "U003", "이미 탈퇴한 회원입니다."),

    FILE_NOT_FOUND(404, "F004", "파일이 존재하지않습니다."),
    FILE_UPLOAD_FAIL(500, "F005", "파일 생성에 실패했습니다."),
    FILE_MAX_SIZE(403, "F003", "파일 갯수는 최대 5개 까지만 가능합니다"),

    LOGIN_FAILED(401, "A001", "아이디 또는 비밀번호가 일치하지않습니다."),

    PET_NOT_FOUND(404, "PE004", "존재하지않는 펫입니다."),

    POST_NOT_FOUND(404, "P004", "존재하지않는 게시글입니다.");

    private final Integer status;
    private final String code;
    private final String message;
}
