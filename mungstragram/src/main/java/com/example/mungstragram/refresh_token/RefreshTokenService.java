package com.example.mungstragram.refresh_token;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final long REFRESH_TOKEN_TTL = 7 * 24 * 60 * 60L;

    public void save(Long userId, String refreshToken) {
        redisTemplate.opsForValue().set(
                "refresh:" + userId,
                refreshToken,
                REFRESH_TOKEN_TTL,
                TimeUnit.SECONDS
        );
    }

    public String get(Long userId) {
        return redisTemplate.opsForValue().get("refresh:" + userId);
    }

    public void delete(Long userId) {
        redisTemplate.delete("refresh:" + userId);
    }
}
