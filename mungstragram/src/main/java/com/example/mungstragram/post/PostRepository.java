package com.example.mungstragram.post;

import com.example.mungstragram.postImage.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
        SELECT p FROM Post p
        JOIN FETCH p.user u
        JOIN FETCH p.pet pe
        LEFT JOIN FETCH p.images
        WHERE p.id = :id
        """)
    Optional<Post> findByIdWithUserId(@Param("id")Long id);

    @Query("""
        SELECT p FROM Post p
        JOIN FETCH p.user u
        JOIN FETCH p.pet pe
        ORDER BY p.createdAt DESC
    """)
    List<Post> findAllWithPosts();

    @Query("""
        SELECT pi FROM PostImage pi WHERE pi.post.id = :id
    """)
    List<PostImage> findByPostId(@Param("id") Long id);

    @Query("""
        SELECT p FROM Post p
        JOIN FETCH p.user u
        JOIN FETCH p.pet pe
        WHERE pe.id = :petId
        """)
    List<Post> findALLByPetIdWithPosts(@Param("petId") Long petId);
}
