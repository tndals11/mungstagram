package com.example.mungstragram.user;

import com.example.mungstragram._common.dto.Response;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/api/users")
    ResponseEntity<Response<Void>> createUser(@Valid @RequestBody UserRequest.CreateDTO createDTO) {

        userService.createUser(createDTO);

        return ResponseEntity.ok().body(Response.ok(null));
    }

    /**
     * 회원-마이페이지(단건)
     */
    @GetMapping("/api/users/me")
    ResponseEntity<Response<UserResponse.DetailDTO>> getByIdUser(
            HttpSession session
    ) {
        User user = (User) session.getAttribute("sessionUser");

        UserResponse.DetailDTO detailDTO =  userService.getByIdUser(user.getId());

        return ResponseEntity.ok().body(Response.ok(detailDTO));
    }

    /**
     * 회원-마이페이지(수정)
     */
    @PatchMapping("/api/users/me")
    ResponseEntity<Response<Void>> updateUser(
            @Valid @RequestBody UserRequest.UpdateDTO updateDTO,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("sessionUser");

        userService.updateUser(updateDTO, user.getId());

        return ResponseEntity.ok().body(Response.ok(null));
    }

    /**
     * 회원탈퇴
     */
    @PatchMapping("/api/users/me/withdraw")
    ResponseEntity<Response<Void>> withdrawUser(
            HttpSession session
    ) {
        User user = (User) session.getAttribute("sessionUser");

        userService.withdrawUser(user.getId());

        return ResponseEntity.ok().body(Response.ok(null));
    }

}
