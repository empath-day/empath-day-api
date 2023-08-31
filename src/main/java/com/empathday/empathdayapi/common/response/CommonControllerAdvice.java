package com.empathday.empathdayapi.common.response;

import static com.empathday.empathdayapi.common.response.ErrorCode.COMMON_INVALID_PARAMETER;
import static org.springframework.core.NestedExceptionUtils.getMostSpecificCause;

import com.empathday.empathdayapi.common.exception.BaseException;
import com.google.common.collect.Lists;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class CommonControllerAdvice {

    private static final List<ErrorCode> ERROR_CODE_LIST = Lists.newArrayList();

    /**
     * http status: 500 AND result: FAIL
     * 시스템 예외 상황. 집중 모니터링 대상
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public CommonResponse onException(Exception e) {
//        String eventId = MDC.get(CommonHttpRequestInterceptor.HEADER_REQUEST_UUID_KEY);
//        log.error("eventId = {} ", eventId, e);
        log.error("error cause is {}", e.getMessage());
        return CommonResponse.fail(ErrorCode.COMMON_SYSTEM_ERROR);
    }

    /**
     * http status: 200 AND result: FAIL
     * 시스템은 이슈 없고, 비즈니스 로직 처리에서 에러가 발생함
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(value = BaseException.class)
    public CommonResponse onBaseException(BaseException e) {
        if (ERROR_CODE_LIST.contains(e.getErrorCode())) {
            log.error("[BaseException] cause = {}, errorMsg = {}", getMostSpecificCause(e), getMostSpecificCause(e).getMessage());
        } else {
            log.warn("[BaseException] cause = {}, errorMsg = {}", getMostSpecificCause(e), getMostSpecificCause(e).getMessage());
        }
        return CommonResponse.fail(e.getMessage(), e.getErrorCode().name());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object processValidationError(MethodArgumentNotValidException ex) {
        return CommonResponse.fail(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage(), COMMON_INVALID_PARAMETER.name());
    }
}
