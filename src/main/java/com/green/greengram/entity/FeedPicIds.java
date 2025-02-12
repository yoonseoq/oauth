package com.green.greengram.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode
public class FeedPicIds implements Serializable {
    private Long feedId;
    @Column(length = 50, name = "pic")
    private String pic;
}
