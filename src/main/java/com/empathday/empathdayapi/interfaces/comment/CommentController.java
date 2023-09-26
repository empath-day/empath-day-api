package com.empathday.empathdayapi.interfaces.comment;

import com.empathday.empathdayapi.common.response.CommonResponse;
import com.empathday.empathdayapi.domain.schedule.comment.CommentService;
import com.empathday.empathdayapi.interfaces.comment.CommentDto.DeleteCommentRequest;
import com.empathday.empathdayapi.interfaces.comment.CommentDto.ModifyCommentRequest;
import com.empathday.empathdayapi.interfaces.comment.CommentDto.RegisterCommentRequest;
import com.empathday.empathdayapi.interfaces.comment.CommentDto.RegisterCommentResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @Operation(
        summary = "피드 댓글, 대댓글을 등록할 수 있습니다.",
        description = "댓글, 대댓글 등록 가능합니다."
    )
    @PostMapping("/api/v1/schedule/{scheduleId}/comments")
    public CommonResponse<RegisterCommentResponse> registerComment(
        @PathVariable("scheduleId") Long scheduleId,
        @RequestBody RegisterCommentRequest request
    ) {
        return CommonResponse.success(commentService.registerComment(scheduleId, request));
    }

    @Operation(
        summary = "피드 댓글, 대댓글을 수정할 수 있습니다.",
        description = "댓글, 대댓글 수정합니다."
    )
    @PutMapping("/api/v1/schedule/{scheduleId}/comments/{commentId}")
    public CommonResponse<String> modifyComment(
        @PathVariable("scheduleId") Long scheduleId,
        @PathVariable("commentId") Long commentId,
        @RequestBody ModifyCommentRequest request
    ) {
        commentService.modifyComment(scheduleId, commentId, request);

        return CommonResponse.success("OK");
    }

    @Operation(
        summary = "피드 댓글, 대댓글을 삭제할 수 있습니다.",
        description = "댓글, 대댓글 삭제합니다."
    )
    @DeleteMapping("/api/v1/comments")
    public CommonResponse<String> deleteComment(
        @RequestBody DeleteCommentRequest request
    ) {
        commentService.deleteComment(request);

        return CommonResponse.success("OK");
    }
}
