package com.example.mungstragram.postImage;

import com.example.mungstragram._common.base.BaseTime;
import com.example.mungstragram.post.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_image_db")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostImage extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id",
        foreignKey = @ForeignKey(name = "fk_post_image_post_id")
    )
    private Post post;

    @Column(nullable = false, length = 5)
    private Integer displayOrder;

    @Column(nullable = false)
    private String imageUrl;

    @Builder
    public PostImage(Post post, String imageUrl, Integer displayOrder) {
        this.post = post;
        this.imageUrl = imageUrl;
        this.displayOrder = displayOrder;
    }
}
