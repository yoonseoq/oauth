package com.green.greengram.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
@Schema(title = "피드 DELETE 요청")
@EqualsAndHashCode
public class FeedDeleteReq {
    @Positive
    @Schema(description = "피드 PK", name="feed_id", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private long feedId;
//    @JsonIgnore
//    private long signedUserId; //  이건 마이바티스ㄸㅐ만 필요

    @ConstructorProperties({"feed_id"})
    public FeedDeleteReq(long feedId) {
        this.feedId = feedId;
    }

//    public void setSignedUserId(long signedUserId) {
//        this.signedUserId = signedUserId;
//    }
}
