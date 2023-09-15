package com.empathday.empathdayapi.domain.schedule.comment;

import com.empathday.empathdayapi.domain.schedule.Schedule;
import com.empathday.empathdayapi.domain.schedule.ScheduleService;
import com.empathday.empathdayapi.domain.user.User;
import com.empathday.empathdayapi.domain.user.UserService;
import com.empathday.empathdayapi.exception.schedule.CommentNotFoundException;
import com.empathday.empathdayapi.infrastructure.schedule.comment.CommentRepository;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleCommentDto;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleCommentDto.RegisterScheduleCommentRequest;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleCommentDto.RetrieveScheduleCommentResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentService {

    private final UserService userService;
    private final ScheduleService scheduleService;
    private final CommentRepository commentRepository;

    @Transactional
    public Comment registerComment(RegisterScheduleCommentRequest request) {
        User writer = userService.getUserInfo(request.getUserId());

        Schedule schedule = scheduleService.getScheduleInfo(request.getScheduleId());

        if (request.hasParentComment()) {
            Comment parentComment = getParentComment(request.getCommentParentId());
            Comment reply = Comment.initReply(parentComment, writer, schedule, request.getContent());
            saveComment(reply);

            return reply;
        }

        Comment comment = Comment.init(writer, schedule, request.getContent());
        saveComment(comment);

        return comment;
    }

    private void saveComment(Comment reply) {
        commentRepository.save(reply);
    }

    private Comment getParentComment(Long parentCommentId) {
        return commentRepository.findById(parentCommentId).orElseThrow(
            () -> new CommentNotFoundException()
        );
    }

    public List<Comment> getAllCommentByParentId(Long scheduleId, Long userId) {
        return commentRepository.findByScheduleIdAndAndUserId(scheduleId, userId).orElseThrow(
            () -> new CommentNotFoundException()
        );
    }

    public List<RetrieveScheduleCommentResponse> retrieveCommentDetail() {
        return null;
    }
}
