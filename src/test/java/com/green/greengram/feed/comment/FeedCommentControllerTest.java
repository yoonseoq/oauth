package com.green.greengram.feed.comment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram.common.model.ResultResponse;
import com.green.greengram.feed.comment.model.*;
import com.green.greengram.feed.like.FeedLikeController;
import com.green.greengram.feed.like.FeedLikeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.List;

import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = FeedCommentController.class
        , excludeAutoConfiguration = SecurityAutoConfiguration.class
)
class FeedCommentControllerTest {
    @Autowired
    ObjectMapper objectMapper; //JSON사용
    @Autowired
    MockMvc mockMvc; //요청(보내고)-응답(받기) 처리
    @MockBean
    FeedCommentService feedCommentService; //가짜 객체를 만들고 빈등록한다.

    final long feedId_2 = 2L;
    final long feedCommentId_3 = 3L;
    final long writerUserId_4 = 4L;
    final int SIZE = 20;
    final String BASE_URL = "/api/feed/comment";

    @Test
    @DisplayName("피드댓글 등록 테스트")
    void postFeedComment() throws Exception {
        FeedCommentPostReq givenParam = new FeedCommentPostReq();
        givenParam.setUserId(feedId_2);
        givenParam.setComment("코멘트");

        // 이 내용이 들어왓을때 feedCommentId3이 들어오게끔
        given(feedCommentService.postFeedComment(givenParam)).willReturn(feedCommentId_3);

        // 테스트 할건데 컨트롤러에서 메소드를 제대로 돌아가는지 확인하는것
        // 정상적으로 돌아간다 가정하고 작성하는것

        String paramJson = objectMapper.writeValueAsString(givenParam);


        ResultActions resultActions = mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(paramJson));

        ResultResponse res = ResultResponse.<Long>builder().resultMessage("댓글 등록 완료").resultData(feedCommentId_3).build();

        String expectedResJson = objectMapper.writeValueAsString(res);

        resultActions.andExpect(status().isOk())
                     .andExpect(content().json(expectedResJson));

        verify(feedCommentService).postFeedComment(givenParam);

    }


    @Test
    void getFeedComment() throws Exception {
        final String URL = "/api/feed/comment";


        FeedCommentGetReq givenParam = new FeedCommentGetReq(feedId_2, 1, 20);

        FeedCommentDto feedCommentDto = new FeedCommentDto();
        feedCommentDto.setFeedId(feedId_2);
        feedCommentDto.setFeedCommentId(feedCommentId_3);
        feedCommentDto.setComment("comment");
        feedCommentDto.setWriterUserId(writerUserId_4);
        feedCommentDto.setWriterNm("writerNm");
        feedCommentDto.setWriterPic("writerPic");

        FeedCommentGetRes expectedResult = new FeedCommentGetRes();
        expectedResult.setMoreComment(false);
        expectedResult.setCommentList(List.of(feedCommentDto));



        given(feedCommentService.getFeedComment(givenParam)).willReturn(expectedResult);
        ResultActions resultActions = mockMvc.perform(get(URL).queryParams(getParameter(givenParam)));
        // perform 이 postman 에서 send역할
        String expectedResJson = getExpectedResJson(expectedResult);
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResJson)); //json으로 바꾼 값이 들어갈수 있게끔

    }

    private MultiValueMap<String, String> getParameter(FeedCommentGetReq givenParam) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("feedId", String.valueOf(givenParam.getFeedId()));
        queryParams.add("start_idx", String.valueOf(givenParam.getStartIdx()));
        queryParams.add("size", String.valueOf(givenParam.getSize()));
        return queryParams;
    }

    private String getExpectedResJson(FeedCommentGetRes expectedResult) throws IOException {

        return objectMapper.writeValueAsString(expectedResult);
    }

    @Test
    @DisplayName("피드 댓글 삭제")
    void delFeedComment() throws Exception {
        final int RESULT = 3;
        FeedCommentDelReq givenParam = new FeedCommentDelReq(feedCommentId_3);
        given(feedCommentService.delFeedComment(givenParam)).willReturn(RESULT);

        ResultActions resultActions = mockMvc.perform(delete(BASE_URL).queryParam("feed_comment_id", String.valueOf(feedCommentId_3)));

        String expectedResJson = objectMapper.writeValueAsString(ResultResponse.<Integer>builder().resultMessage("댓글 삭제가 완료되었습니다.")
                                                                                                    .resultData(RESULT)
                                                                                                    .build());

        resultActions.andDo(print()).andExpect(status().isOk()).andExpect(content().json(expectedResJson));

        verify(feedCommentService).delFeedComment(givenParam);
    }


}