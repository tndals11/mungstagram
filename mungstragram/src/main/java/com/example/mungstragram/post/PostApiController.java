package com.example.mungstragram.post;

import com.example.mungstragram._common.dto.Response;
import com.example.mungstragram.user.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    @PostMapping("/api/posts")
    ResponseEntity<Response<Void>> createPost(
        @Valid @ModelAttribute PostRequest.CreateDTO createDTO,
        HttpSession session
    ) {
        User user = (User) session.getAttribute("sessionUser");

        postService.createPost(createDTO, user.getId());

        return ResponseEntity.ok().body(Response.ok(null));
    }

    @DeleteMapping("/api/posts/{id}")
    ResponseEntity<Response<Void>> deletePost(
            @PathVariable Long id,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("sessionUser");

        postService.deletePost(id, user.getId());

        return ResponseEntity.ok().body(Response.ok(null));
    }


}
