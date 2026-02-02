package com.example.mungstragram.pet;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("""
        SELECT p FROM Pet p
        JOIN FETCH p.user
        WHERE p.id in : petIds
    """)
    List<Pet> findAllByIdWithUser(@Param("petIds") List<Long> petIds);

    @Query("""
        SELECT p FROM Pet p
        JOIN FETCH p.user
        WHERE p.user.id = :userId
        AND p.id = :id
        """)
    Optional<Pet> findByIdWithUser(@Param("id") Long id, @Param("userId") Long userId);

    @Query("""
        SELECT p FROM Pet p
        JOIN FETCH p.user
        WHERE p.user.id = :userId
        ORDER BY p.createdAt DESC
        """)
    List<Pet> findAllByUserIdWithUser(Long userId);
}
