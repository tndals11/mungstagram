package com.example.mungstragram.comment;

import com.example.mungstragram._common.enums.notification.Type;
import com.example.mungstragram._common.error.ErrorCode;
import com.example.mungstragram._common.exception.CustomException;
import com.example.mungstragram.notification.NotificationService;
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
    private final NotificationService notificationService;

    @Transactional
    public void createComment(CommentRequest.CreateDTO createDTO, Long id,  Long userId) {

        User userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Post postEntity = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        Comment parentComment = null;
        if (createDTO.getParentId() != null) {
            parentComment = commentRepository.findById(createDTO.getParentId())
                    .orElse(null);
        }

        commentRepository.save(createDTO.toEntity(userEntity, postEntity, parentComment));

        if (parentComment != null) {
            if (!parentComment.getUser().getId().equals(userId)) {
                notificationService.createNotification(userEntity, parentComment.getUser(), postEntity, Type.COMMENT );
            }

        } else {
            if (!postEntity.getUser().getId().equals(userId)) {
                notificationService.createNotification(userEntity, postEntity.getUser(), postEntity, Type.COMMENT );
            }
        }
    }

    @Transactional
    public void deleteComment(Long id, Long userId) {
        commentRepository.delete(commentRepository.findByIdWithUserAndPost(id, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.ACCESS_DENIED)));
    }

    public List<CommentResponse.DetailDTO> listComment(Long id) {
        return commentRepository.findAllByPostIdAndParentIsNull(id).stream()
                .map(CommentResponse.DetailDTO::new)
                .toList();
    }
}
