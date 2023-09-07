package com.empathday.empathdayapi.exception.feed;

import com.empathday.empathdayapi.common.exception.BaseException;
import com.empathday.empathdayapi.common.response.ErrorCode;

public class FeedNotFoundException extends BaseException {

    public FeedNotFoundException() {
        super(ErrorCode.FEED_ENTITY_NOT_FOUND);
    }
}
