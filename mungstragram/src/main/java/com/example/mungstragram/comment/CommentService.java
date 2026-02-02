package com.example.mungstragram.comment;

import com.example.mungstragram._common.error.ErrorCode;
import com.example.mungstragram._common.exception.CustomException;
import com.example.mungstragram.post.Post;
import com.example.mungstragram.post.PostRepository;
import com.example.mungstragram.user.User;
import com.example.mungstragram.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void createComment(CommentRequest.CreateDTO createDTO, Long id,  Long userId) {

        User userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Post postEntity = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        commentRepository.save(createDTO.toEntity(userEntity, postEntity));
    }

    @Transactional
    public void deleteComment(Long id, Long userId) {
        commentRepository.delete(commentRepository.findByIdWithUserAndPost(id, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ACCESS_DENIED)));
    }

    public List<CommentResponse.DetailDTO> listComment(Long id) {
        return commentRepository.findAllCommentByPostId(id).stream()
                .map(CommentResponse.DetailDTO::new)
                .toList();
    }
}
