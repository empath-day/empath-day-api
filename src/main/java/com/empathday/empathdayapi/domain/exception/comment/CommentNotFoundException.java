package com.empathday.empathdayapi.domain.exception.comment;

import static com.empathday.empathdayapi.common.response.ErrorCode.COMMENT_ENTITY_NOT_FOUND;

import com.empathday.empathdayapi.common.exception.BaseException;

public class CommentNotFoundException extends BaseException {

    public CommentNotFoundException() {
        super(COMMENT_ENTITY_NOT_FOUND);
    }
}