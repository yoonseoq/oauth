package com.green.greengram.entity;

import com.green.greengram.config.security.SignInProviderType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity // 테이블을 만들고 DML때 사용 , 얘 있을때 User에 빨간줄생김: 무조건 pk가 있어야함
@Getter
@Setter
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"provider_type", "uid"}
                )
        }
)
public class User extends UpdatedAt{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    //@GeneratedValue(strategy = GenerationType.TABLE) uuid
    private long userId;

    //유니크 주기
    @Column(nullable = false)
    private SignInProviderType providerType;

    @Column(nullable = false, length = 30, unique = true) //null은 가능한 안쓰는 편이 좋다
    private String uid;

    @Column(nullable = false, length = 100)
    private String upw;

    @Column(length = 30)
    private String nickName;

    @Column( length = 50)
    private String pic;

    //시퀀스 테이블이랑 그냥 테이블 무슨차이
    //tsid uuid사용시 문자열이 일어남
}
