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
    public void deletePet(PetRequest.DeleteDTO deleteDTO, Long userId) {

        User userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<Pet> petEntities = petRepository.findAllByIdWithUser(deleteDTO.getPetIds());

        for (Pet pet : petEntities) {
            if (!pet.isOwner(userEntity)) {
                throw new CustomException(ErrorCode.ACCESS_DENIED);
            }
            fileUtils.deleteFile(pet.getProfileImage());
        }

        // 삭제 할 값이 엄청나게 많아진다면 InBatch를 사용하는게 성능은 더 좋아진다
        petRepository.deleteAllByIdInBatch(deleteDTO.getPetIds());
    }

    @Transactional
    public void updatePet(PetRequest.UpdateDTO updateDTO, Long id, Long userId) {

        Pet petEntity = petRepository.findByIdWithUser(id, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.PET_NOT_FOUND));

        if (updateDTO.getProfileImage() != null && !updateDTO.getProfileImage().isEmpty()) {
            fileUtils.deleteFile(petEntity.getProfileImage());

            String savedName = fileUtils.saveFile(updateDTO.getProfileImage());

            petEntity.updateImage(savedName);
        }

        petEntity.update(updateDTO);
    }

    public PetResponse.DetailDTO detailPet(Long id, Long userId) {

        Pet petEntity = petRepository.findByIdWithUser(id, userId)
                .orElseThrow(() -> new CustomException(ErrorCode.PET_NOT_FOUND));

        return new PetResponse.DetailDTO(petEntity);
    }

    public List<PetResponse.ListDTO> listPet(Long userId) {
        return petRepository.findAllByUserIdWithUser(userId).stream()
                .map(PetResponse.ListDTO::new)
                .toList();
    }
}
