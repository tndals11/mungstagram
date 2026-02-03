package com.example.mungstragram.pet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {

    @Query("""
        SELECT p FROM Pet p
        JOIN FETCH p.user u
        WHERE p.id = :id
        """)
    Optional<Pet> findByIdWithUser(@Param("id") Long id);

    @Query("""
        SELECT p FROM Pet p
        JOIN FETCH p.user u
        ORDER BY p.createdAt DESC
        """)
    List<Pet> findAllWithUser();

}
