package com.example.mungstragram.auth;

import com.example.mungstragram._common.enums.user.OAuthProvider;
import com.example.mungstragram._common.enums.user.Status;
import com.example.mungstragram._common.security.JwtProvider;
import com.example.mungstragram.refresh_token.RefreshTokenService;
import com.example.mungstragram.user.User;
import com.example.mungstragram.user.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoogleAuthService {

    private final JwtProvider jwtProvider;
    @Value("${google.client-id}")
    private String clientId;

    @Value("${google.client-secret}")
    private String secret;

    @Value("${google.redirect-uri}")
    private String redirectUri;

    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    public String getGoogleUrl() {
        return "https://accounts.google.com/o/oauth2/v2/auth"
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&response_type=code"
                + "&scope=email%20profile"
                + "&access_type=offline";
    }

    @Transactional
    public String googleLogin(String code) {
        AuthResponse.TokenDTO tokenDTO = getAccessToken(code);

        AuthResponse.GoogleUserInfo userInfo = getGoogleUserInfo(tokenDTO.getAccessToken());

        User user = userRepository.findByProviderAndProviderId(OAuthProvider.GOOGLE, userInfo.getId())
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .username(userInfo.getEmail())
                            .password("OAUTH_USER")
                            .nickname(userInfo.getName())
                            .provider(OAuthProvider.GOOGLE)
                            .providerId(userInfo.getId())
                            .status(Status.ACTIVE)
                            .build();
                    return userRepository.save(newUser);
                });

        String refreshToken = jwtProvider.createRefreshToken(user.getId());

        refreshTokenService.save(user.getId(), refreshToken);

        return jwtProvider.create(user);
    }

    public AuthResponse.TokenDTO getAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        String tokenUrl = "https://oauth2.googleapis.com/token";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("client_secret", secret);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<AuthResponse.TokenDTO > response = restTemplate.exchange(
                tokenUrl,
                HttpMethod.POST,
                request,
                AuthResponse.TokenDTO.class
        );

        return response.getBody();
    }

    private AuthResponse.GoogleUserInfo getGoogleUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<AuthResponse.GoogleUserInfo> response = restTemplate.exchange(
                "https://www.googleapis.com/oauth2/v2/userinfo",
                HttpMethod.GET,
                request,
                AuthResponse.GoogleUserInfo.class
        );

        return response.getBody();
    }

}
