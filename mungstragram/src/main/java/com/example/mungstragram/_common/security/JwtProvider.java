package com.example.mungstragram._common.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.mungstragram.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    /**
     * HS512 암호화에 사용될 비밀 키
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * 토큰의 유효 기간 (밀리초 단위, 24시간 설정)
     */
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * HTTP 헤더에 토큰을 실 을 때 붙이는 접두사 
     * "Bearer " 뒤에 실제 토큰 문자열이 붙는다 (한 칸 공백 필수)
     */
    public static final String PREFIX = "Bearer ";

    /**
     * [ JWT 토큰 생성 ]
     */
    public String create(User user) {
        // 유저의 권한을 가져온다 1명의 유저는 권한이 여러개 이므로 stream사용
        // "ROLE_USER,ROLE_ADMIN" 형태로 가져옴
        String roles = user.getRoles().stream()
                .map(role -> "ROLE_" + role.getName())
                .collect(Collectors.joining(","));
        return JWT.create()
                .withSubject(user.getUsername())       // [subject] 토큰 주인
                .withClaim("id", user.getId())   // [Payload] 커스텀 데이터 : 유저 ID
                .withClaim("role", roles)        // [Payload] 커스텀 데이터 : 유저 권한
                .withIssuedAt(new Date())              // [Payload] 발행 일자 : 발행한 시간
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration)) // [Payload] 만료시간 : 내가 설정한 시간
                .sign(Algorithm.HMAC512(secret)); // [Signature] 설정한 알고리즘과 비밀키 전체 내용을 암호화 핵심!
    }

    public DecodedJWT verify(String jwt) {
        // "Bearer " 라는 접두사를 제거한 순순 토큰 내용
        String token = jwt.replace(PREFIX, "");

        // 검증 로직
        // 만약 위조 되었거나 만료가 됐다면 여기 로직에서 예외가 발생한다
        return JWT.require(Algorithm.HMAC512(secret)) // 같은 알고리즘 키로 맞춘다
                .build()                              // 검증용 객체를 생성
                .verify(token);                       // 실제 검증
    }

}
