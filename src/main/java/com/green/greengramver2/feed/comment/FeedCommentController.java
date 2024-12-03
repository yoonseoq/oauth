package com.green.greengramver2.feed.comment;

import com.green.greengramver2.common.model.ResultResponse;
import com.green.greengramver2.feed.comment.model.FeedCommentDto;
import com.green.greengramver2.feed.comment.model.FeedCommentGetReq;
import com.green.greengramver2.feed.comment.model.FeedCommentGetRes;
import com.green.greengramver2.feed.comment.model.FeedCommentPostReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("feed/comment")
public class FeedCommentController {
    private final FeedCommentService service;

    @PostMapping
    public ResultResponse<Long> postFeedComment(@RequestBody FeedCommentPostReq p) {
        log.info("FeedCommentController > postFeedComment > p: {}", p);
        long feedCommentId = service.postFeedComment(p);
        return ResultResponse.<Long>builder()
                .resultMessage("댓글 등록 완료")
                .resultData(feedCommentId)
                .build();
    }

    @GetMapping
    public ResultResponse<FeedCommentGetRes> getFeedComment(@ParameterObject @ModelAttribute FeedCommentGetReq p) {
        log.info("FeedCommentController > getFeedComment > p: {}", p);
        FeedCommentGetRes res = service.getFeedComment(p);
        return ResultResponse.<FeedCommentGetRes>builder()
                .resultMessage(String.format("%d rows", res.getCommentList().size()))
                .resultData(res)
                .build();
    }

}
