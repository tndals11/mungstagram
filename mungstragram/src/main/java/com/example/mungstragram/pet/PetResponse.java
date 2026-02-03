package com.example.mungstragram.pet;

import com.example.mungstragram._common.enums.pet.Gender;
import lombok.Data;
import java.time.LocalDateTime;

public class PetResponse {

    @Data
    public static class DetailDTO {
        private String name;
        private Integer age;
        private String breed;
        private String description;
        private String profileImage;
        private Gender gender;
        protected boolean isOwner;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        public DetailDTO(Pet pet, boolean isOwner) {
            this.name = pet.getName();
            this.age = pet.getAge();
            this.description = pet.getDescription();
            this.profileImage = "/images/" + pet.getProfileImage();
            this.gender = pet.getGender();
            this.isOwner = isOwner;
            this.createdAt = pet.getCreatedAt();
            this.updatedAt = pet.getUpdatedAt();
        }
    }

    @Data
    public static class ListDTO {
       private Long id;
       private String name;
       private String profileImage;
       private Gender gender;
       private Integer age;
       private boolean isOwner;

       public ListDTO(Pet pet) {
           this.id = pet.getId();
           this.name = pet.getName();
           this.profileImage = "/images/" + pet.getProfileImage();
           this.gender = pet.getGender();
           this.age = pet.getAge();
       }
    }
}
