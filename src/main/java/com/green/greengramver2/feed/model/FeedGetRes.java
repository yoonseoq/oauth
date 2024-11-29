package com.green.greengramver2.feed.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FeedGetRes {
    private long feedId;
    private String contents;
    private String location;
    private String createdAt;
    private long writerId;
    private String writerNm;
    private String writerPic;

    private List<String> pics = new ArrayList<>();
}
