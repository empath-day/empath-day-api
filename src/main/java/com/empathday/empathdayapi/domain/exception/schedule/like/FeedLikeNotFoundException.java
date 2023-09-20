package com.empathday.empathdayapi.domain.exception.schedule.like;

import com.empathday.empathdayapi.common.exception.BaseException;
import com.empathday.empathdayapi.common.response.ErrorCode;

public class FeedLikeNotFoundException extends BaseException {

    public FeedLikeNotFoundException() {
        super(ErrorCode.FEED_LIKE_ENTITY_NOT_FOUND);
    }
}
