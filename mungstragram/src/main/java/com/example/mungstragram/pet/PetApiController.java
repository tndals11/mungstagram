package com.example.mungstragram.pet;

import com.example.mungstragram._common.dto.Response;
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
public class PetApiController {

    private final PetService petService;

    /**
     * 펫 등록
     */
    @PostMapping("/api/pets")
    ResponseEntity<Response<Void>> createPet(
            @Valid @ModelAttribute PetRequest.CreateDTO createDTO,
            @AuthenticationPrincipal CustomUserDetails userDetails
            ) {

        User user = userDetails.getUser();

        petService.createPet(createDTO, user.getId());

       return ResponseEntity.ok().body(Response.ok(null));
    }

    /**
     * 등록한 펫 삭제
     */
    @DeleteMapping("/api/pets/{id}")
    ResponseEntity<Response<Void>> deletePet(
        @PathVariable Long id,
        @AuthenticationPrincipal CustomUserDetails userDetails
    ) {

        User user = userDetails.getUser();

        petService.deletePet(id, user.getId());

        return ResponseEntity.ok().body(Response.ok(null));
    }

    /**
     * 등록한 펫 수정
     */
    @PutMapping("/api/pets/{id}")
    ResponseEntity<Response<Void>> updatePet(
            @Valid @ModelAttribute PetRequest.UpdateDTO updateDTO,
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        User user = userDetails.getUser();

        petService.updatePet(updateDTO, id, user.getId());

        return ResponseEntity.ok().body(Response.ok(null));
    }

    /**
     * 펫 - 전체조회
     */
    @GetMapping("/api/pets")
    ResponseEntity<Response<List<PetResponse.ListDTO>>> listPet(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        User user = userDetails.getUser();

        List<PetResponse.ListDTO> listDTO = petService.listPet();

        return ResponseEntity.ok().body(Response.ok(listDTO));
    }

    /**
     * 펫 - 단건조회
     */
    @GetMapping("/api/pets/{id}")
    ResponseEntity<Response<PetResponse.DetailDTO>> detailPet(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable Long id
    ) {
        User user = userDetails.getUser();

        PetResponse.DetailDTO detailDTO = petService.detailPet(id, user.getId());

        return ResponseEntity.ok().body(Response.ok(detailDTO));
    }

}
