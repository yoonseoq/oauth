package com.green.greengram.feed;

import com.green.greengram.entity.FeedPic;
import com.green.greengram.entity.FeedPicIds;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedPicRepository extends JpaRepository<FeedPic, FeedPicIds> {
// 기본 crud로 해결함
}
