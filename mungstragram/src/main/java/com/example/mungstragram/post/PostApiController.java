package com.example.mungstragram.post;

import com.example.mungstragram._common.dto.Response;
import com.example.mungstragram.comment.CommentRequest;
import com.example.mungstragram.comment.CommentResponse;
import com.example.mungstragram.comment.CommentService;
import com.example.mungstragram.user.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/api/posts")
    ResponseEntity<Response<Void>> createPost(
        @Valid @ModelAttribute PostRequest.CreateDTO createDTO,
        HttpSession session
    ) {
        User user = (User) session.getAttribute("sessionUser");

        postService.createPost(createDTO, user.getId());

        return ResponseEntity.ok().body(Response.ok(null));
    }

    /**
     * 게시글 - 삭제
     */
    @DeleteMapping("/api/posts/{id}")
    ResponseEntity<Response<Void>> deletePost(
            @PathVariable Long id,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("sessionUser");

        postService.deletePost(id, user.getId());

        return ResponseEntity.ok().body(Response.ok(null));
    }

    /**
     * 게시글 - 수정
     */
    @PutMapping("/api/posts/{id}")
    ResponseEntity<Response<Void>> updatePost(
            @PathVariable Long id,
            HttpSession session,
            @Valid @ModelAttribute PostRequest.UpdateDTO updateDTO
    ) {
        User user = (User) session.getAttribute("sessionUser");

        postService.updatePost(id, updateDTO, user.getId());

        return ResponseEntity.ok().body(Response.ok(null));
    }

    /**
     * 게시글 - 단건조회
     */
    @GetMapping("/api/posts/{id}")
    ResponseEntity<Response<PostResponse.DetailDTO>> detailPost(
            @PathVariable Long id,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("sessionUser");

        PostResponse.DetailDTO detailDTO = postService.detailPost(id, user.getId());

        return ResponseEntity.ok().body(Response.ok(detailDTO));
    }

    @GetMapping("/api/posts")
    ResponseEntity<Response<List<PostResponse.ListDTO>>> listPost() {
        List<PostResponse.ListDTO> listDTO = postService.listPost();

        return ResponseEntity.ok().body(Response.ok(listDTO));
    }

    /**
     * 댓글 - 작성
     */
    @PostMapping("/api/posts/{id}/comments")
    ResponseEntity<Response<Void>> createComment(
            HttpSession session,
            @PathVariable Long id,
            @Valid @RequestBody CommentRequest.CreateDTO createDTO
    ) {
        User user = (User) session.getAttribute("sessionUser");

        commentService.createComment(createDTO, id, user.getId());

        return ResponseEntity.ok(Response.ok(null));
    }

    /**
     * 댓글 - 보기
     */
    @GetMapping("/api/posts/{id}/comments")
    ResponseEntity<Response<List<CommentResponse.DetailDTO>>> listComment(
            HttpSession session,
            @PathVariable Long id
    ) {
        User user = (User) session.getAttribute("sessionUser");

        List<CommentResponse.DetailDTO> listComment = commentService.listComment(id);

        return ResponseEntity.ok(Response.ok(listComment));
    }
}
