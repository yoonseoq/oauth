package com.green.greengram.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass //   Entity 부모역할
@EntityListeners(AuditingEntityListener.class) // 이벤트(내가 하는 모든 행위) 연결(binding)
public class UpdatedAt extends CreatedAt {
    @LastModifiedDate //insert, update 되었을때 현재일시값을 넣는다
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}
