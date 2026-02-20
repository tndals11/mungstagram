package com.example.mungstragram.auth;

import com.example.mungstragram._common.dto.Response;
import com.example.mungstragram.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthApiController {

    private final UserService userService;
    private final GoogleAuthService googleAuthService;

    @PostMapping("/api/auth/login")
    ResponseEntity<Response<AuthResponse.LoginTokenDTO>> login(
        @Valid @RequestBody AuthRequest.LoginDTO loginDTO
    ) {
        AuthResponse.LoginTokenDTO loginTokenDTO = userService.login(loginDTO);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + loginTokenDTO.getAccessToken())
                .header("RefreshToken", loginTokenDTO.getRefreshToken())
                .body(Response.ok(null));
    }

    @PostMapping("/api/auth/logout")
    ResponseEntity<Response<Void>> logout (
            @RequestHeader("refreshToken") String refreshToken
    ) {
        userService.logout(refreshToken);
        return ResponseEntity.ok().body(Response.ok(null));
    }

    @GetMapping("/api/auth/google/url")
    public ResponseEntity<Response<String>> getGoogleUrl() {
        String url = googleAuthService.getGoogleUrl();
        return ResponseEntity.ok(Response.ok(url));
    }

    @GetMapping("/api/auth/oauth/google/callback")
    public ResponseEntity<Response<String>> callback(
            @RequestParam String code
    ) {

        String jwtToken = googleAuthService.googleLogin(code);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + jwtToken)
                .body(Response.ok(jwtToken));
    }

    @PostMapping("/api/auth/reissueToken")
    public ResponseEntity<Response<Void>> reissueToken(
            @RequestHeader("RefreshToken") String refreshToken
    ) {

        String newAccessToken = userService.reissueToken(refreshToken);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + newAccessToken)
                .body(Response.ok(null));
    }

}
