package com.empathday.empathdayapi.domain.user.exception;

import com.empathday.empathdayapi.common.exception.BaseException;
import com.empathday.empathdayapi.common.response.ErrorCode;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException() {
        super(ErrorCode.USER_ENTITY_NOT_FOUND);
    }
}