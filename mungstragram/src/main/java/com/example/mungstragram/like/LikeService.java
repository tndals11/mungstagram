package com.example.mungstragram.like;

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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeService {

    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Transactional
    public void likePosts(Long postId, Long userId) {

        Post postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        User userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Like likeEntity = likeRepository.findByUserIdAndPostId(userId, postId)
                .orElse(null);

        if (likeEntity == null) {
            Like like = Like.builder()
                    .post(postEntity)
                    .user(userEntity)
                    .build();

            likeRepository.save(like);

            postEntity.addLike();

            if (!postEntity.getUser().getId().equals(userId)) {
                notificationService.createNotification(userEntity, postEntity.getUser(), postEntity, Type.LIKE);
            }

        } else {
            likeRepository.delete(likeEntity);
            postEntity.removeLike();
        }
    }
}
