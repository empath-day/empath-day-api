package com.empathday.empathdayapi.domain.schedule.comment;

import com.empathday.empathdayapi.common.response.ErrorCode;
import com.empathday.empathdayapi.domain.common.DeleteStatus;
import com.empathday.empathdayapi.domain.schedule.Schedule;
import com.empathday.empathdayapi.domain.user.User;
import com.empathday.empathdayapi.domain.user.exception.UserNotFoundException;
import com.empathday.empathdayapi.domain.exception.comment.CommentException;
import com.empathday.empathdayapi.domain.exception.comment.CommentNotFoundException;
import com.empathday.empathdayapi.domain.exception.schedule.ScheduleNotFoundException;
import com.empathday.empathdayapi.infrastructure.schedule.ScheduleRepository;
import com.empathday.empathdayapi.infrastructure.schedule.comment.CommentQueryDSLRepository;
import com.empathday.empathdayapi.infrastructure.schedule.comment.CommentRepository;
import com.empathday.empathdayapi.infrastructure.user.UserRepository;
import com.empathday.empathdayapi.interfaces.comment.CommentDto;
import com.empathday.empathdayapi.interfaces.comment.CommentDto.DeleteCommentRequest;
import com.empathday.empathdayapi.interfaces.comment.CommentDto.RegisterCommentRequest;
import com.empathday.empathdayapi.interfaces.comment.CommentDto.RegisterCommentResponse;
import com.empathday.empathdayapi.interfaces.comment.CommentDto.RetrieveCommentResponse;
import com.empathday.empathdayapi.interfaces.comment.CommentDto.ModifyCommentRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentService {

    private final CommentQueryDSLRepository commentQueryDSLRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    /**
     * 댓글, 대댓글 등록
     *
     * @param scheduleId
     * @param request
     * @return
     */
    @Transactional
    public RegisterCommentResponse registerComment(Long scheduleId, RegisterCommentRequest request) {
        User writer = findUser(request.getUserId());

        Schedule schedule = findSchedule(scheduleId);

        // 자식 댓글 등록
        if (request.hasParentComment()) {
            Comment parentComment = getParentComment(request.getCommentParentId());
            Comment reply = Comment.initReply(parentComment, writer, schedule, request.getContent());
            saveComment(reply);

            return RegisterCommentResponse.fromEntity(reply);
        }

        Comment comment = Comment.init(writer, schedule, request.getContent());
        saveComment(comment);

        return RegisterCommentResponse.fromEntity(comment);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
            () -> new UserNotFoundException()
        );
    }

    private Schedule findSchedule(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(
            () -> new ScheduleNotFoundException()
        );
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

    /**
     * schedule에 해당하는 모든 댓글 정보를 조회합니다.
     * @param findSchedule
     * @return
     */
    public List<RetrieveCommentResponse> findAllBySchedule(Schedule findSchedule) {
        List<Comment> comments = commentQueryDSLRepository.findAllBySchedule(findSchedule);

        return convertCommentToDto(comments);
    }

    /** comment entity to Dto **/
    private static List<RetrieveCommentResponse> convertCommentToDto(List<Comment> comments) {
        List<RetrieveCommentResponse> result = new ArrayList<>();
        Map<Long, RetrieveCommentResponse> map = new HashMap<>();

        comments.stream().forEach(entity -> {
            RetrieveCommentResponse dto = RetrieveCommentResponse.fromEntity(entity);
            map.put(dto.getId(), dto);

            if (entity.hasParentComment()) {
                map.get(entity.getParentComment().getId()).getChildComments().add(dto);
            } else {
                result.add(dto);
            }
        });

        /*result.stream().forEach(dto -> {
            if (dto.getComm)
        });*/

        return result;
    }

    /**
     * 댓글을 삭제합니다.
     *
     * @param request
     */
    @Transactional
    public void deleteComment(DeleteCommentRequest request) {
        Comment comment = getCommentWithParent(request.getCommentId());

        if (!comment.getUserId().equals(request.getUserId())) {
            throw new CommentException(ErrorCode.DELETE_COMMENT_ONLY_WRITER);
        }

        comment.changeDeleteStatusToDelete();

        /*if (!CollectionUtils.isEmpty(comment.getChildComment())) {
        } else {
            getDeletableAncestorComment(comment).changeDeleteStatusToDelete();
        }*/
    }
    /*private Comment getDeletableAncestorComment(Comment comment) {
        Comment parent = comment.getParentComment();
        if(parent != null && parent.getChildComment().size() == 1 && parent.getDeleteStatus() == DeleteStatus.Y)
            return getDeletableAncestorComment(parent);
        return comment;
    }*/


    private Comment getCommentWithParent(Long commentId) {
        return commentRepository.findCommentByIdWithParent(commentId).orElseThrow(
            () -> new CommentNotFoundException()
        );
    }

    /**
     * 댓글 정보를 수정합니다.
     *
     * @param
     */
    @Transactional
    public RetrieveCommentResponse modifyComment(Long scheduleId, Long commentId, ModifyCommentRequest request) {
        Schedule findSchedule = findSchedule(scheduleId);

        Comment comment = findCommentByScheduleIdAndCommentId(findSchedule.getId(), commentId);

        if (!comment.getUserId().equals(request.getUserId())) {
            throw new CommentException(ErrorCode.DELETE_COMMENT_ONLY_WRITER);
        }

        comment.modify(request.getContent());

        return RetrieveCommentResponse.fromEntity(comment);
    }

    private Comment findCommentByScheduleIdAndCommentId(Long scheduleId, Long commentId) {
        return commentRepository.findByIdAndDeleteStatus(commentId, DeleteStatus.N).orElseThrow(
            () -> new CommentNotFoundException()
        );
    }
}
