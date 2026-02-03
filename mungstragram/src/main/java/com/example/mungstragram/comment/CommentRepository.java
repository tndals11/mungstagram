package com.example.mungstragram.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("""
        SELECT c FROM Comment c
        JOIN FETCH c.user u
        JOIN FETCH c.post p
        WHERE u.id = :userId
        AND c.id = :id
    """)
    Optional<Comment>  findByIdWithUserAndPost(@Param("id") Long id, @Param("userId") Long userId);

    @Query("""
        SELECT c FROM Comment c
        JOIN FETCH c.user u
        LEFT JOIN FETCH c.children ch
        LEFT JOIN FETCH ch.user chu
        WHERE c.post.id = :postId
        AND c.parent IS NULL
        ORDER BY c.createdAt DESC
    """)
    List<Comment> findAllByPostIdAndParentIsNull(@Param("postId") Long postId);

    @Modifying(clearAutomatically = true)
    @Query("""
        delete FROM Comment c
        WHERE c.post.id = :id
    """)
    void deleteByPostId(@Param("id") Long id);
}
