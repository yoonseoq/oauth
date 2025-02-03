package com.green.greengram.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass //   Entity 부모역할
@EntityListeners(AuditingEntityListener.class) // 이벤트(내가 하는 모든 행위) 연결(binding), , insert가 될때 현재일시값을 넣자
public class CreatedAt {
    @CreatedDate // insert 되었을때 현재일시값을 넣는다. 이 애노테이션이 작동을 하려면 @EntityListeners 세팅이 되어야 한다
    @Column(nullable = false) // 이 애노테이션은 자동으로 작성이 되는데 설정을 좀 더 해주고 싶다면 이 애노테이션을 붙여야 한다.
    private LocalDateTime createdAt;
}
