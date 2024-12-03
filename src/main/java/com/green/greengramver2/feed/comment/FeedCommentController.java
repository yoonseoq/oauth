package com.green.greengramver2.feed.comment;

import com.green.greengramver2.common.model.ResultResponse;
import com.green.greengramver2.feed.comment.model.FeedCommentDto;
import com.green.greengramver2.feed.comment.model.FeedCommentGetReq;
import com.green.greengramver2.feed.comment.model.FeedCommentGetRes;
import com.green.greengramver2.feed.comment.model.FeedCommentPostReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("feed/comment")
@Tag(name = "4. 피드 댓글", description = "피드 댓글 관리")
public class FeedCommentController {
    private final FeedCommentService service;

    @PostMapping
    @Operation(summary = "피드 댓글 등록", description = "")
    public ResultResponse<Long> postFeedComment(@RequestBody FeedCommentPostReq p) {
        log.info("FeedCommentController > postFeedComment > p: {}", p);
        long feedCommentId = service.postFeedComment(p);
        return ResultResponse.<Long>builder()
                .resultMessage("댓글 등록 완료")
                .resultData(feedCommentId)
                .build();
    }

    @GetMapping
    @Operation(summary = "피드 댓글 리스트", description = "댓글 더보기 처리")
    public ResultResponse<FeedCommentGetRes> getFeedComment(@ParameterObject @ModelAttribute FeedCommentGetReq p) {
        log.info("FeedCommentController > getFeedComment > p: {}", p);
        FeedCommentGetRes res = service.getFeedComment(p);
        return ResultResponse.<FeedCommentGetRes>builder()
                .resultMessage(String.format("%d rows", res.getCommentList().size()))
                .resultData(res)
                .build();
    }

    @GetMapping("/ver2")
    @Operation(summary = "피드 댓글 리스트", description = "댓글 더보기 처리")
    public ResultResponse<FeedCommentGetRes> getFeedComment2(@Parameter(description = "피드 PK", example = "12") @RequestParam("feed_id") long feedId
                                                           , @Parameter(description = "페이지", example = "2") @RequestParam int page
        ) {
        FeedCommentGetReq p = new FeedCommentGetReq(feedId, page);
        log.info("FeedCommentController > getFeedComment > p: {}", p);
        FeedCommentGetRes res = service.getFeedComment(p);
        return ResultResponse.<FeedCommentGetRes>builder()
                .resultMessage(String.format("%d rows", res.getCommentList().size()))
                .resultData(res)
                .build();
    }

}
