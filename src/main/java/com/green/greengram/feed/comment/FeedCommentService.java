package com.green.greengram.feed.comment;

import com.green.greengram.common.exception.CustomException;
import com.green.greengram.common.exception.FeedErrorCode;
import com.green.greengram.config.security.AuthenticationFacade;
import com.green.greengram.entity.Feed;
import com.green.greengram.entity.FeedComment;
import com.green.greengram.entity.User;
import com.green.greengram.feed.FeedRepository;
import com.green.greengram.feed.comment.model.*;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedCommentService {
    private final FeedCommentMapper mapper;
    private final AuthenticationFacade authenticationFacade;
    private final FeedCommentRepository feedCommentRepository;
    private final FeedRepository feedRepository;

    public long postFeedComment(FeedCommentPostReq p) {
       /*
        p.setUserId(authenticationFacade.getSignedUserId());
        mapper.insFeedComment(p);
        return p.getFeedCommentId();

        */
        Feed feed = new Feed();
        feed.setFeedId(p.getFeedId());

        User user = new User();
        user.setUserId(authenticationFacade.getSignedUserId());

        FeedComment feedComment = new FeedComment();
        feedComment.setFeed(feed);
        feedComment.setUser(user);
        feedComment.setComment(p.getComment());

        feedCommentRepository.save(feedComment);
        return feedComment.getFeedCommentId();
    }

    public FeedCommentGetRes getFeedComment(FeedCommentGetReq p) {
        FeedCommentGetRes res = new FeedCommentGetRes();
        if(p.getStartIdx() < 0) {
            res.setCommentList(new ArrayList<>());
            return res;
        }
        List<FeedCommentDto> commentList = mapper.selFeedCommentList(p); //1~21사이
        res.setCommentList(commentList);
        res.setMoreComment( commentList.size() == p.getSize() );
        if(res.isMoreComment()) {
            commentList.remove(commentList.size() - 1);
        }
        return res;
    }


    public void delFeedComment(FeedCommentDelReq p) {
       FeedComment feedComment = feedCommentRepository.findById(p.getFeedCommentId()).orElse(null);
       if(feedComment == null || feedComment.getUser().getUserId() != authenticationFacade.getSignedUserId()) {
           throw new CustomException(FeedErrorCode.FAIL_TO_DEL_COMMENT);
       }
       feedCommentRepository.delete(feedComment);
       /*
        p.setUserId(authenticationFacade.getSignedUserId());
       int result = feedCommentRepository
               .deleteFeedComment(p.getFeedCommentId(),p.getUserId());


       return result;


        */

    }
}
