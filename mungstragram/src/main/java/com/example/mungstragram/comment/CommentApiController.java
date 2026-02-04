package com.example.mungstragram.comment;

import com.example.mungstragram._common.dto.Response;
import com.example.mungstragram.user.CustomUserDetails;
import com.example.mungstragram.user.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentApiController {

    private final CommentService commentService;

    /**
     * 댓글 - 삭제
     */
    @DeleteMapping("/api/comments/{id}")
    ResponseEntity<Response<Void>> deleteComment(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
            ) {
        User user = userDetails.getUser();

        commentService.deleteComment(id, user.getId());

        return ResponseEntity.ok(Response.ok(null));
    }
}
