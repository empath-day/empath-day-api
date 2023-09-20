package com.empathday.empathdayapi.interfaces.comment;

import com.empathday.empathdayapi.common.response.CommonResponse;
import com.empathday.empathdayapi.domain.schedule.comment.CommentService;
import com.empathday.empathdayapi.interfaces.comment.ScheduleCommentDto.DeleteCommentRequest;
import com.empathday.empathdayapi.interfaces.comment.ScheduleCommentDto.ModifyCommentRequest;
import com.empathday.empathdayapi.interfaces.comment.ScheduleCommentDto.RegisterScheduleCommentRequest;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    @PostMapping("/api/v1/comments")
    public CommonResponse<String> registerComment(
        @RequestBody RegisterScheduleCommentRequest request
    ) {
        commentService.registerComment(request);

        return CommonResponse.success("OK");
    }

    @Operation(
        summary = "피드 댓글, 대댓글을 수정할 수 있습니다.",
        description = "댓글, 대댓글 수정합니다."
    )
    @PutMapping("/api/v1/comments")
    public CommonResponse<String> modifyComment(
        @RequestBody ModifyCommentRequest request
    ) {
        commentService.modifyComment(request);

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
