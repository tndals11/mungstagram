package com.example.mungstragram._common.base;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * @Getter: 값을 가져오기
 * @MappedSuperclass: 테이블로 만들어지지않음, 자식 엔티티에게 필드만 상속
 * @EntityListeners: @CreatedDate, @LastModifiedDate가 작동하게 설정
 *
 * @CreatedDate: 엔티티가 처음으로 저장될 때 자동으로 현재시간 입력 
 * @LastModifiedDate: 엔티티가 수정될 때 마다 자동으로 현재시간 업데이트
 *
 */

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTime {

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedBy
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
