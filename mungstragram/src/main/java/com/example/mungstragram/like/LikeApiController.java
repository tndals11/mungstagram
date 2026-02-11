package com.example.mungstragram.like;

import com.example.mungstragram._common.dto.Response;
import com.example.mungstragram.user.CustomUserDetails;
import com.example.mungstragram.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeApiController {

    private final LikeService likeService;

    @PostMapping("/api/posts/{id}/likes")
    ResponseEntity<Response<Void>> likePosts(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
            ) {

        User user = userDetails.getUser();

        likeService.likePosts(id, user.getId());

        return ResponseEntity.ok(Response.ok(null));
    }
}
