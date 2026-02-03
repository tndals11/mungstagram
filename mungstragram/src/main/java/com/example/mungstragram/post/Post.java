package com.example.mungstragram.post;

import com.example.mungstragram._common.base.BaseTime;
import com.example.mungstragram._common.exception.CustomException;
import com.example.mungstragram.pet.Pet;
import com.example.mungstragram.postImage.PostImage;
import com.example.mungstragram.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "post_db")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "like_count", nullable = false)
    private Integer likeCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_post_user_id")
    )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false,
        foreignKey = @ForeignKey(name = "fk_post_pet_id")
    )
    private Pet pet;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostImage> images = new ArrayList<>();

    @Builder
    public Post(String title, String content, User user, Pet pet) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.pet = pet;
    }

    public boolean isOwner(Long userId) {
        if (userId == null) return false;
        return this.user.getId().equals(userId);
    }

    public void addImage(String imageUrl, int order) {
        PostImage postImage = PostImage.builder()
                .post(this)
                .imageUrl(imageUrl)
                .displayOrder(order)
                .build();

        this.images.add(postImage);
    }

    public void update(PostRequest.UpdateDTO updateDTO) {
        if (updateDTO.getContent() != null) this.content = updateDTO.getContent();
        if (updateDTO.getTitle() != null) this.title = updateDTO.getTitle();
    }

    public void addLike() {
        this.likeCount += 1;
    }

    public void removeLike() {
        if (this.likeCount > 0) {
            this.likeCount -= 1;
        }
    }
}
