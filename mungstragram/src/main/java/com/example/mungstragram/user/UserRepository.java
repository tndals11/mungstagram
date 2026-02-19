package com.example.mungstragram.user;

import com.example.mungstragram._common.enums.user.OAuthProvider;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByNickname(@NotBlank(message = "별명은 필수입니다") @Size(min = 3, max = 100, message = "별명은 3~30 글자 사이여야합니다") String nickname);

    boolean existsByUsername(@NotBlank(message = "아이디는 필수입니다") @Size(min = 8, max = 50, message = "아이디는 8~50 글자 사이여야 합니다") String username);

    boolean existsByUsernameAndPassword(@NotBlank(message = "아이디는 필수입니다") String username, @NotBlank(message = "비밀번호는 필수입니다") String password);

    Optional<User> findByUsername(@NotBlank(message = "아이디는 필수입니다") String username);

    @Query("""
        SELECT u FROM User u
        JOIN FETCH u.userRoles ur
        WHERE u.username = :username
        """)
    Optional<User> findByUsernameWithRoles(@Param("username") String username);

    Optional<User> findByProviderAndProviderId(OAuthProvider provider, String providerId);
}
