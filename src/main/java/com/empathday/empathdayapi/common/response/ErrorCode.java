package com.empathday.empathdayapi.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    COMMON_SYSTEM_ERROR("일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
    COMMON_INVALID_PARAMETER("요청한 값이 올바르지 않습니다."),
    COMMON_ENTITY_NOT_FOUND("존재하지 않는 엔티티입니다."),
    COMMON_ILLEGAL_STATUS("잘못된 상태값입니다."),
    REQUIRED_EMOTION("감정 상태는 필수값입니다."),
    USER_ENTITY_NOT_FOUND("존재하지 않는 회원입니다."),
    SCHEDULE_ENTITY_NOT_FOUND("존재하지 않는 피드입니다."),
    FEED_LIKE_ENTITY_NOT_FOUND("좋아요 정보가 존재하지 않습니다."),
    COMMENT_ENTITY_NOT_FOUND("존재하지 않는 댓글입니다."),
    DELETE_COMMENT_ONLY_WRITER("댓글 작성자만 댓글을 지울 수 있습니다."),
    ;

    private final String errorMsg;
}
