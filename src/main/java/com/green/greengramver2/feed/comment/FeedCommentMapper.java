package com.green.greengramver2.feed.comment;

import com.green.greengramver2.feed.comment.model.FeedCommentDto;
import com.green.greengramver2.feed.comment.model.FeedCommentGetReq;
import com.green.greengramver2.feed.comment.model.FeedCommentPostReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FeedCommentMapper {
    void insFeedComment(FeedCommentPostReq p);
    List<FeedCommentDto> selFeedCommentList(FeedCommentGetReq p);
}
