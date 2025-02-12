package com.green.greengram.feed.comment;

import com.green.greengram.entity.FeedComment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface FeedCommentRepository extends CrudRepository<FeedComment, Long> {
    // 이렇게하면 기본적인 crud 만들어짐
    @Modifying // 이 애노테이션이 있어야 delete or update JPQL, 리턴타입은 void or int
    @Query("delete from FeedComment f where f.feedCommentId=:feedCommentId AND f.user.userId=:writerUserId")
    //f.feedId 컬럼명 말고 멤버필드 적으면 됨
    //바로 쿼리문 날려서 select 필요가 없다. 삭제할때 밖에 없다
    int deleteFeedComment( Long feedCommentId, Long writerUserId);
}
