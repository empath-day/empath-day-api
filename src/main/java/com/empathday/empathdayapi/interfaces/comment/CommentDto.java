package com.empathday.empathdayapi.interfaces.comment;

import com.empathday.empathdayapi.common.utils.NumberUtils;
import com.empathday.empathdayapi.domain.common.DeleteStatus;
import com.empathday.empathdayapi.domain.schedule.comment.Comment;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.RetrieveScheduleResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.CollectionUtils;

public class CommentDto {

    /** request **/
    @Getter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RegisterCommentRequest {

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
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ModifyCommentRequest {

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
    @ToString
    @NoArgsConstructor
    public static class RegisterCommentResponse {

        private Long id;
        private Long userId;
        private String content;
        private Integer likeCount;
        private DeleteStatus deleteStatus;
        private RetrieveScheduleResponse schedule;
        private List<RegisterCommentResponse> childComments;

        @Builder
        private RegisterCommentResponse(
            Long id, Long userId, String content,
            Integer likeCount, DeleteStatus deleteStatus,
            RetrieveScheduleResponse schedule,List<RegisterCommentResponse> childComments
        ) {
            this.id = id;
            this.userId = userId;
            this.content = content;
            this.likeCount = likeCount;
            this.deleteStatus = deleteStatus;
            this.schedule = schedule;
            this.childComments = childComments;
        }

        public static RegisterCommentResponse fromEntity(Comment comment) {
            return RegisterCommentResponse.builder()
                .id(comment.getId())
                .userId(comment.getUserId())
                .content(comment.getContent())
                .likeCount(comment.getLikeCount())
                .deleteStatus(comment.getDeleteStatus())
                .schedule(RetrieveScheduleResponse.fromEntity(comment.getSchedule()))
                .childComments(RegisterCommentResponse.fromParentComment(comment.getChildComment()))
                .build();
        }

        public static List<RegisterCommentResponse> fromParentComment(List<Comment> childComments) {
            if (CollectionUtils.isEmpty(childComments)) {
                return Collections.EMPTY_LIST;
            }

            return childComments.stream()
                .map(childComment -> RegisterCommentResponse.fromEntity(childComment))
                .collect(Collectors.toList());
        }
    }

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

        public static List<RetrieveCommentResponse> fromEntity(List<Comment> comments) {
            if (CollectionUtils.isEmpty(comments)) {
                return Collections.EMPTY_LIST;
            }

            return comments.stream()
                .map(comment -> RetrieveCommentResponse.fromEntity(comment))
                .collect(Collectors.toList());
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
