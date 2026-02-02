package com.example.mungstragram.pet;

import com.example.mungstragram._common.dto.Response;
import com.example.mungstragram.user.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
            HttpSession session
    ) {

        User user = (User) session.getAttribute("sessionUser");

        petService.createPet(createDTO, user.getId());

       return ResponseEntity.ok().body(Response.ok(null));
    }

    /**
     * 등록한 펫 삭제
     */
    @DeleteMapping("/api/pets")
    ResponseEntity<Response<Void>> deletePet(
        @Valid @RequestBody PetRequest.DeleteDTO deleteDTO,
        HttpSession session
    ) {

        User user = (User) session.getAttribute("sessionUser");

        petService.deletePet(deleteDTO, user.getId());

        return ResponseEntity.ok().body(Response.ok(null));
    }

    /**
     * 등록한 펫 수정
     */
    @PutMapping("/api/pets/{id}")
    ResponseEntity<Response<Void>> updatePet(
            @Valid @ModelAttribute PetRequest.UpdateDTO updateDTO,
            @PathVariable Long id,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("sessionUser");

        petService.updatePet(updateDTO, id, user.getId());

        return ResponseEntity.ok().body(Response.ok(null));
    }

    /**
     * 펫 - 단건조회
     */
    @GetMapping("/api/pets/{id}")
    ResponseEntity<Response<PetResponse.DetailDTO>> detailPet(
            HttpSession session,
            @PathVariable Long id
    ) {
        User user = (User) session.getAttribute("sessionUser");

        PetResponse.DetailDTO detailDTO = petService.detailPet(id, user.getId());

        return ResponseEntity.ok().body(Response.ok(detailDTO));
    }


    /**
     * 펫 - 전체조회
     */
    @GetMapping("/api/pets")
    ResponseEntity<Response<List<PetResponse.ListDTO>>> listPet(
            HttpSession session
    ) {
        User user = (User) session.getAttribute("sessionUser");

        List<PetResponse.ListDTO> listDTO = petService.listPet(user.getId());

        return ResponseEntity.ok().body(Response.ok(listDTO));
    }

}
