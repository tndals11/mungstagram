package com.example.mungstragram.pet;

import com.example.mungstragram._common.base.BaseTime;
import com.example.mungstragram._common.enums.pet.Gender;
import com.example.mungstragram._common.enums.pet.Status;
import com.example.mungstragram.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pet_db")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pet extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String profileImage;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false, length = 100)
    private String breed;

    @Column(nullable = false, length = 100)
    private String description;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Gender gender;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Status petStatus = Status.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_pet_user_id")
    )
    private User user;

    @Builder
    public Pet(User user, String profileImage, String name, Integer age, String breed, String description, Gender gender) {
        this.user = user;
        this.profileImage = profileImage;
        this.name = name;
        this.age = age;
        this.breed = breed;
        this.description = description;
        this.gender = gender;
    }

    public boolean isOwner(Long userId) {
        if (userId == null) return false;
        return this.user.getId().equals(userId);
    }

    public void update(PetRequest.UpdateDTO updateDTO) {
        if (updateDTO.getName() != null) this.name = updateDTO.getName();
        if (updateDTO.getAge() != null) this.age = updateDTO.getAge();
        if (updateDTO.getBreed() != null) this.breed = updateDTO.getBreed();
        if (updateDTO.getDescription() != null) this.description = updateDTO.getDescription();
    }

    public void updateImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void delete() {
        this.petStatus = Status.DELETE;
    }
}
