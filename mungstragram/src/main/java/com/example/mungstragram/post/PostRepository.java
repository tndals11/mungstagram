package com.example.mungstragram.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
        SELECT p FROM Post p
        JOIN FETCH p.user u
        JOIN FETCH p.pet pe
        LEFT JOIN FETCH p.images
        WHERE u.id = :userId
        AND p.id = :id
        """)
    Optional<Post> findByIdWithUserId(@Param("id")Long id, @Param("userId") Long userId);

    @Query("""
        SELECT p FROM Post p
        JOIN FETCH p.user u
        JOIN FETCH p.pet pe
        ORDER BY p.createdAt DESC
    """)
    List<Post> findAllWithPosts();
}
