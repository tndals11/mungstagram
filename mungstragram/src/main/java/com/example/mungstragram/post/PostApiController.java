package com.example.mungstragram.post;

import com.example.mungstragram._common.dto.Response;
import com.example.mungstragram.comment.CommentRequest;
import com.example.mungstragram.comment.CommentResponse;
import com.example.mungstragram.comment.CommentService;
import com.example.mungstragram.user.CustomUserDetails;
import com.example.mungstragram.user.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;
    private final CommentService commentService;

    /**
     * 게시글 - 생성
     */
    @PostMapping("/api/pets/{petId}/posts")
    ResponseEntity<Response<Void>> createPost(
        @PathVariable Long petId,
        @Valid @ModelAttribute PostRequest.CreateDTO createDTO,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        User user = userDetails.getUser();

        postService.createPost(petId, createDTO, user.getId());

        return ResponseEntity.ok().body(Response.ok(null));
    }

    /**
     * 게시글 전체조회
     */
    @GetMapping("/api/pets/{petId}/posts")
    ResponseEntity<Response<List<PostResponse.ListDTO>>> listPost(
            @PathVariable Long petId
    ) {
        List<PostResponse.ListDTO> listDTO = postService.listPost(petId);

        return ResponseEntity.ok().body(Response.ok(listDTO));
    }

    /**
     * 게시글 - 삭제
     */
    @DeleteMapping("/api/posts/{id}")
    ResponseEntity<Response<Void>> deletePost(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        User user = userDetails.getUser();

        postService.deletePost(id, user.getId());

        return ResponseEntity.ok().body(Response.ok(null));
    }

    /**
     * 게시글 - 수정
     */
    @PutMapping("/api/posts/{id}")
    ResponseEntity<Response<Void>> updatePost(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @ModelAttribute PostRequest.UpdateDTO updateDTO
    ) {
        User user = userDetails.getUser();

        postService.updatePost(id, updateDTO, user.getId());

        return ResponseEntity.ok().body(Response.ok(null));
    }

    /**
     * 게시글 - 단건조회
     */
    @GetMapping("/api/posts/{id}")
    ResponseEntity<Response<PostResponse.DetailDTO>> detailPost(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        User user = userDetails.getUser();

        PostResponse.DetailDTO detailDTO = postService.detailPost(id, user.getId());

        return ResponseEntity.ok().body(Response.ok(detailDTO));
    }

    /**
     * 댓글 - 작성
     */
    @PostMapping("/api/posts/{id}/comments")
    ResponseEntity<Response<Void>> createComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id,
            @Valid @RequestBody CommentRequest.CreateDTO createDTO
    ) {
        User user = userDetails.getUser();

        commentService.createComment(createDTO, id, user.getId());

        return ResponseEntity.ok(Response.ok(null));
    }

    /**
     * 댓글 - 보기
     */
    @GetMapping("/api/posts/{id}/comments")
    ResponseEntity<Response<List<CommentResponse.DetailDTO>>> listComment(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id
    ) {
        User user = userDetails.getUser();

        List<CommentResponse.DetailDTO> listComment = commentService.listComment(id);

        return ResponseEntity.ok(Response.ok(listComment));
    }
}
