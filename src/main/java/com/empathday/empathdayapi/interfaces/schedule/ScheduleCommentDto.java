package com.empathday.empathdayapi.interfaces.schedule;

import com.empathday.empathdayapi.common.utils.NumberUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class ScheduleCommentDto {

    /** request **/
    @Getter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterScheduleCommentRequest {

        private Long scheduleId;
        private Long userId;
        private Long commentParentId;
        @NotBlank(message = "댓글 내용은 필수 값입니다.")
        private String content;

        @JsonIgnore
        public boolean hasParentComment() {
            return NumberUtils.isNotNullOrZero(commentParentId);
        }
    }
}
