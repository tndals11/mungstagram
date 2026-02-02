package com.example.mungstragram.user;

import com.example.mungstragram._common.base.BaseTime;
import com.example.mungstragram._common.enums.user.Status;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Entity: JPA 엔티티라는 표시
 * @Table: DB와 동일한 테이블 이름 표시
 * @Getter: 값을 가져오기 위해서 사용
 * @NoArgsConstructor: 파라미터가 없는 기본 생성자 생성
 * PROTECTED: 같은 패키지 + 상송받은 클래스에서만 사용 가능
 */
@Entity
@Table(name = "user_db")
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

    @Builder
    public User(String username, String password, String nickname, Status status) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.status = Status.ACTIVE;
    }

    public void update(String newNickname) {
        this.nickname = newNickname;
    }

    public void withdraw() {
        this.status = Status.WITHDRAWN;
    }

}
