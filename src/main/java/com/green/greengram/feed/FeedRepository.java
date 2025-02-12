package com.green.greengram.feed;

import com.green.greengram.entity.Feed;
import com.green.greengram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FeedRepository extends JpaRepository<Feed, Long> {
    Optional<Feed> findByFeedIdAndWriterUser(Long feedId, User writerUser);


    // 그 조건에 부합하는 튜플을 반환해줌

    //쿼리메소드를 delete, update 는 비추
    int deleteByFeedIdAndWriterUser(Long feedId, User writerUser);
    // select한번 하고 delete 쿼리문 두번 날라감 -> 쓸모가 엄슴
    // 쿼리메소드는 메소드 명으로 쿼리 만드느느거고 jpql은??


    // 둘다 똑같은 delete 처리결과 가져옴 int는 동시에 발생 Optional은 선택 삭제따로 . Optional이 더 유연하다

    //JPQL(Java Persistence Query Language)
    @Modifying // 이 애노테이션이 있어야 delete or update JPQL, 리턴타입은 void or int
    @Query("delete from Feed f where f.feedId=:feedId AND f.writerUser.userId=:writerUserId")
    //f.feedId 컬럼명 말고 멤버필드 적으면 됨
    //바로 쿼리문 날려서 select 필요가 없다. 삭제할때 밖에 없다
    int deleteFeed( Long feedId, Long writerUserId);


    @Modifying
    @Query(value ="delete from Feed f where f.feed_id=:feedId and f.writer_user_id=:writerUserId",nativeQuery = true)
    int deleteFeedSql(Long feedId, Long writerUserId);
    /*
    Feed (대문자로 시작) - 클래스명 작성해야 함

    feedId = 1, writerUserId = 2 가정하에 아래 sql문이 만들어진다

    delete from feed f
    where f.feed_id = 1
    AND f.user_id =2

     */
}


