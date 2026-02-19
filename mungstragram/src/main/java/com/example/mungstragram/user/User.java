package com.example.mungstragram.user;

import com.example.mungstragram._common.base.BaseTime;
import com.example.mungstragram._common.enums.user.OAuthProvider;
import com.example.mungstragram._common.enums.user.Status;
import com.example.mungstragram.role.Role;
import com.example.mungstragram.user_role.UserRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

/**
 * @Entity: JPA 엔티티라는 표시
 * @Table: DB와 동일한 테이블 이름 표시
 * @Getter: 값을 가져오기 위해서 사용
 * @NoArgsConstructor: 파라미터가 없는 기본 생성자 생성
 * PROTECTED: 같은 패키지 + 상송받은 클래스에서만 사용 가능
 */
@Entity
@Table(name = "user_db",
    uniqueConstraints = {
        @UniqueConstraint(
                name = "uk_provider_provider_id",
                columnNames = {"provider", "provider_id"}
        )
    }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 30)
    private String nickname;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Status status;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserRole> userRoles = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ColumnDefault("'LOCAL'")
    private OAuthProvider provider;

    @Column(length = 100)
    private String providerId;

    @Builder
    public User(String username, String password, String nickname, Status status, OAuthProvider provider, String providerId) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.status = status != null ? status : Status.ACTIVE;
        this.providerId = providerId;

        if (provider == null) {
            this.provider = OAuthProvider.LOCAL;
        } else {
            this.provider = provider;
        }
    }

    public static User forAuthentication(Long id, String username, List<UserRole> userRoles) {
        User user = new User();
        user.id = id;
        user.username = username;
        user.userRoles = userRoles;
        return user;
    }

    public void isOwner(Long userId) {

    }

    public void update(String newNickname) {
        this.nickname = newNickname;
    }

    public void withdraw() {
        this.status = Status.WITHDRAWN;
    }

    public void addRole(Role role) {
        UserRole userRole = UserRole.builder()
                .user(this)
                .role(role)
                .build();
        this.userRoles.add(userRole);
    }

    public List<Role> getRoles() {
        return userRoles.stream()
                .map(UserRole::getRole)
                .toList();
    }

}
