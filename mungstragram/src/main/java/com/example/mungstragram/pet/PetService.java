package com.example.mungstragram.pet;

import com.example.mungstragram._common.error.ErrorCode;
import com.example.mungstragram._common.exception.CustomException;
import com.example.mungstragram._common.utils.FileUtils;
import com.example.mungstragram.user.User;
import com.example.mungstragram.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PetService {

    private final UserRepository userRepository;
    private final PetRepository petRepository;
    private final FileUtils fileUtils;

    @Transactional
    public void createPet(PetRequest.CreateDTO createDTO, Long userId) {

        User userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        String savedFileName = fileUtils.saveFile(createDTO.getProfileImage());

        Pet pet = createDTO.toEntity(userEntity, savedFileName);

        petRepository.save(pet);
    }

    @Transactional
    public void deletePet(Long id, Long userId) {

        Pet petEntity = petRepository.findByIdWithUser(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PET_NOT_FOUND));

        if (!petEntity.isOwner(userId)) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        petEntity.delete();
    }

    @Transactional
    public void updatePet(PetRequest.UpdateDTO updateDTO, Long id, Long userId) {

        Pet petEntity = petRepository.findByIdWithUser(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PET_NOT_FOUND));

        if (!petEntity.isOwner(userId)) {
            throw new CustomException(ErrorCode.ACCESS_DENIED);
        }

        if (updateDTO.getProfileImage() != null && !updateDTO.getProfileImage().isEmpty()) {
            fileUtils.deleteFile(petEntity.getProfileImage());

            String savedName = fileUtils.saveFile(updateDTO.getProfileImage());

            petEntity.updateImage(savedName);
        }

        petEntity.update(updateDTO);
    }

    public PetResponse.DetailDTO detailPet(Long id, Long userId) {

        Pet petEntity = petRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.PET_NOT_FOUND));

        boolean isOwner = petEntity.isOwner(userId);

        return new PetResponse.DetailDTO(petEntity, isOwner);
    }

    public List<PetResponse.ListDTO> listPet() {
        return petRepository.findAllWithUser().stream()
                .map(PetResponse.ListDTO::new)
                .toList();
    }
}
