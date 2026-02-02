package com.example.mungstragram.auth;

import com.example.mungstragram._common.dto.Response;
import com.example.mungstragram.user.UserService;
import jakarta.servlet.http.HttpSession;
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
    ResponseEntity<Response<Void>> login(
        @Valid @RequestBody AuthRequest.LoginDTO loginDTO,
        HttpSession session
    ) {
        userService.login(loginDTO, session);

        return ResponseEntity.ok().body(Response.ok(null));
    }

    @PostMapping("/api/auth/logout")
    ResponseEntity<Response<Void>> logout (
            HttpSession session
    ) {
        session.invalidate();

        return ResponseEntity.ok().body(Response.ok(null));
    }
}
