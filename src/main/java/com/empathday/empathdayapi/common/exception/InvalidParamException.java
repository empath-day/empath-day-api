package com.empathday.empathdayapi.common.exception;

import com.empathday.empathdayapi.common.response.ErrorCode;

public class InvalidParamException extends BaseException {

    public InvalidParamException() {super(ErrorCode.COMMON_INVALID_PARAMETER);}
    public InvalidParamException(ErrorCode errorCode) {
        super(errorCode);
    }
    public InvalidParamException(String message) {
        super(message, ErrorCode.COMMON_INVALID_PARAMETER);
    }
    public InvalidParamException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
