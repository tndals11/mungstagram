package com.example.mungstragram.post;

import com.example.mungstragram._common.error.ErrorCode;
import com.example.mungstragram._common.exception.CustomException;
import com.example.mungstragram._common.utils.FileUtils;
import com.example.mungstragram.pet.Pet;
import com.example.mungstragram.pet.PetRepository;
import com.example.mungstragram.postImage.PostImage;
import com.example.mungstragram.user.User;
import com.example.mungstragram.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final PostRepository postRepository;
    private final FileUtils fileUtils;

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
}
