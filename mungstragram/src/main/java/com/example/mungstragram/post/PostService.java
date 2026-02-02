package com.example.mungstragram.post;

import com.example.mungstragram._common.dto.Response;
import com.example.mungstragram._common.error.ErrorCode;
import com.example.mungstragram._common.exception.CustomException;
import com.example.mungstragram._common.utils.FileUtils;
import com.example.mungstragram.comment.Comment;
import com.example.mungstragram.comment.CommentRepository;
import com.example.mungstragram.pet.Pet;
import com.example.mungstragram.pet.PetRepository;
import com.example.mungstragram.pet.PetResponse;
import com.example.mungstragram.postImage.PostImage;
import com.example.mungstragram.postImage.PostImageRepository;
import com.example.mungstragram.user.User;
import com.example.mungstragram.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final PostRepository postRepository;
    private final FileUtils fileUtils;
    private final PostImageRepository postImageRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void createPost(PostRequest.CreateDTO createDTO, Long userId) {

        User userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Pet petEntity = petRepository.findById(createDTO.getPetId())
                .orElseThrow(() -> new CustomException(ErrorCode.PET_NOT_FOUND));

        if (!petEntity.isOwner(userEntity)) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        Post post = createDTO.toEntity(userEntity, petEntity);

        int order = 1;
        for (MultipartFile imageFile : createDTO.getImages()) {
            String savedFileName = fileUtils.saveFile(imageFile);
            post.addImage(savedFileName, order++);
        }

        postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long id, Long userId) {

       Post postEntity = postRepository.findByIdWithUserId(id, userId)
               .orElseThrow(() -> new CustomException(ErrorCode.PET_NOT_FOUND) );

       for (PostImage image : postEntity.getImages()) {
           fileUtils.deleteFile(image.getImageUrl());
       }

       postRepository.delete(postEntity);
    }

    @Transactional
    public void updatePost(Long id, PostRequest.UpdateDTO updateDTO, Long userId) {

        Post postEntity = postRepository.findByIdWithUserId(id, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.PET_NOT_FOUND) );

        if (updateDTO.getImageIds() != null && !updateDTO.getImageIds().isEmpty()) {
            List<PostImage> images = postImageRepository.findAllById(updateDTO.getImageIds());

            for (PostImage file : images) {
                fileUtils.deleteFile(file.getImageUrl());

                postImageRepository.delete(file);
            }
        }

        if (updateDTO.getImages() != null && !updateDTO.getImages().isEmpty()) {
            List<PostImage> images = postImageRepository.findByPostId(id);

            int maxOrder = images.stream()
                    .map(PostImage::getDisplayOrder)
                    .max(Integer::compareTo)
                    .orElse(0);

            int nextOrder = maxOrder + 1;
            for (MultipartFile file : updateDTO.getImages()) {
                String saveFileName = fileUtils.saveFile(file);

                postEntity.addImage(saveFileName, nextOrder++);
            }
        }

        postEntity.update(updateDTO);
    }

    public PostResponse.DetailDTO detailPost(Long id, Long userId) {

        Post postEntity = postRepository.findByIdWithUserId(id, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.PET_NOT_FOUND) );

        return new PostResponse.DetailDTO(postEntity);
    }

    public List<PostResponse.ListDTO> listPost() {
        return postRepository.findAllWithPosts().stream()
                .map(PostResponse.ListDTO::new)
                .toList();
    }
}
