package com.empathday.empathdayapi.domain.exception.comment;

import com.empathday.empathdayapi.common.exception.BaseException;
import com.empathday.empathdayapi.common.response.ErrorCode;

public class CommentException extends BaseException {

    public CommentException(ErrorCode errorCode) {
        super(errorCode);
    }
}
