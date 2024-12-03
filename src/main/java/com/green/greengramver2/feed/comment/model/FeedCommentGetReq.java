package com.green.greengramver2.feed.comment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.BindParam;
import org.springframework.web.bind.annotation.RequestParam;

@Getter
@Setter
public class FeedCommentGetReq {
    private final static int FIRST_COMMENT_SIZE = 3;
    private final static int DEFAULT_PAGE_SIZE = 20;

    @Schema(title = "피드 PK", name="feed_id", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)

    private long feedId;
    @Schema(title = "페이지", description = "2이상 값만 사용해 주세요. 아이템수는 20개 입니다.", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private int page;

    @JsonIgnore
    private int startIdx;

    @JsonIgnore
    private int size;

    public FeedCommentGetReq(@BindParam("feed_id") long feedId, int page) {
        this.feedId = feedId;
        setPage(page);
    }

    public void setPage(int page) {
        this.page = page;
        if(page < 1) { return; }
        if(page == 1) {
            startIdx = 0;
            size = FIRST_COMMENT_SIZE + 1; //+1은 isMore처리용
            return;
        }
        startIdx = ((page - 2) * DEFAULT_PAGE_SIZE) + FIRST_COMMENT_SIZE;
        size = DEFAULT_PAGE_SIZE + 1; //+1은 isMore처리용
    }

}
