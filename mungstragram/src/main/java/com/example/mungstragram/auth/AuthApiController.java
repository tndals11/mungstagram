package com.example.mungstragram.auth;

import com.example.mungstragram._common.dto.Response;
import com.example.mungstragram.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthApiController {

    private final UserService userService;

    @PostMapping("/api/auth/login")
    ResponseEntity<Response<String>> login(
        @Valid @RequestBody AuthRequest.LoginDTO loginDTO
    ) {
        String jwtToken = userService.login(loginDTO);

        return ResponseEntity.ok()
                .header("Authorization", "Bearer " + jwtToken)
                .body(Response.ok(jwtToken));
    }

    @PostMapping("/api/auth/logout")
    ResponseEntity<Response<Void>> logout (
    ) {
        return ResponseEntity.ok().body(Response.ok(null));
    }
}
