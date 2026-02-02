package com.example.mungstragram.postImage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    @Query("""
        SELECT pi FROM PostImage pi
        LEFT JOIN FETCH pi.post
        WHERE pi.post.id = :id
        """)
    List<PostImage> findByPostId(@Param("id") Long id);
}
