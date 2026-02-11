package com.example.mungstragram.user;

import com.example.mungstragram._common.dto.Response;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    /**
     * @RequiredArgsConstructor: 생성자를 주입 시킨다
     */
    private final UserService userService;

    /**
     * 회원가입
     */
    @PostMapping("/api/users/register")
    ResponseEntity<Response<Void>> createUser(@Valid @RequestBody UserRequest.CreateDTO createDTO) {

        userService.createUser(createDTO);

        return ResponseEntity.ok().body(Response.ok(null));
    }

    /**
     * 회원-마이페이지(단건)
     */
    @GetMapping("/api/users/me")
    ResponseEntity<Response<UserResponse.DetailDTO>> getByIdUser(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        User user = userDetails.getUser();

        UserResponse.DetailDTO detailDTO =  userService.getByIdUser(user.getId());

        return ResponseEntity.ok().body(Response.ok(detailDTO));
    }

    /**
     * 회원-마이페이지(수정)
     */
    @PatchMapping("/api/users/me")
    ResponseEntity<Response<Void>> updateUser(
            @Valid @RequestBody UserRequest.UpdateDTO updateDTO,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        User user = userDetails.getUser();

        userService.updateUser(updateDTO, user.getId());

        return ResponseEntity.ok().body(Response.ok(null));
    }

    /**
     * 회원탈퇴
     */
    @PatchMapping("/api/users/me/withdraw")
    ResponseEntity<Response<Void>> withdrawUser(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        User user = userDetails.getUser();

        System.out.println(user);

        userService.withdrawUser(user.getId());

        return ResponseEntity.ok().body(Response.ok(null));
    }

}
