package com.example.mungstragram._common.dto;

import com.example.mungstragram._common.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @AllArgsConstructor: 모든 필드
 */

@Getter
@AllArgsConstructor
public class Response<T> {
    private Integer status;
    private String msg;
    private T body;
    private String code;

    public static <T> Response<T> ok(T body) {
        return new Response<>(200, "성공", body, null);
    }

    public static <T> Response<T> fail(ErrorCode errorCode, String message) {
        return new Response<>(
                errorCode.getStatus(),
                message,
                null,
                errorCode.getCode()
        );
    }

}
