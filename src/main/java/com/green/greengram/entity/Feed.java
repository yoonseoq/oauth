package com.green.greengram.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
//@Table(name = "feed_table")//테이블명이 클래스명이 아닌 다른이름으로 만들고 싶다면
public class Feed extends UpdatedAt{
    @Id // pk 라는 의미
    @GeneratedValue(strategy = GenerationType.IDENTITY)//자동증가
    private long feedId;

    //유저 한명이 여러피드 일대다 피드를 알아야 유저를 안다?
    @ManyToOne
    @JoinColumn(name = "writer_user_id", nullable = false)
    private User writerUser;

    @Column(length = 1_000)
    private String contents;

    @Column(length = 30)
    private String location;
}
