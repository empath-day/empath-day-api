package com.empathday.empathdayapi.interfaces.comment;

import com.empathday.empathdayapi.common.utils.NumberUtils;
import com.empathday.empathdayapi.domain.common.DeleteStatus;
import com.empathday.empathdayapi.domain.schedule.comment.Comment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
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

    @Getter
    @ToString
    @NoArgsConstructor
    public static class ModifyCommentRequest {

        private Long commentId;
        private Long userId;
        private String content;
    }

    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteCommentRequest {

        private Long commentId;
        private Long userId;
    }

    /** response **/
    @Getter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RetrieveCommentResponse {

        private Long id;
        private Long parentCommentId;
        private Long scheduleId;
        private String content;
        private Long writerId;
        private String writerName;
        private String writerProfileImageUrl;
        private List<RetrieveCommentResponse> childComments;

        public static RetrieveCommentResponse fromEntity(Comment entity) {
            return RetrieveCommentResponse.builder()
                .id(entity.getId())
                .parentCommentId(hasParent(entity))
                .scheduleId(entity.getSchedule().getId())
                .content(entity.getContent())
                .writerId(entity.getUserId())
                .writerName("임시--")
                .writerProfileImageUrl("임시->시큐리티 스펙 맞춘 후 추가 예정")
                .build();
        }

        private static Long hasParent(Comment entity) {
            if (entity.getParentComment() != null) {
                return entity.getParentComment().getId();
            }

            return null;
        }

        public void setParentCommentId(Long parentCommentId) {
            this.parentCommentId = parentCommentId;
        }
    }
}
