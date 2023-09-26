package com.empathday.empathdayapi.domain.schedule.comment;

import static com.empathday.empathdayapi.domain.schedule.Schedule.Scope.PUBLIC;
import static org.assertj.core.api.Assertions.*;

import com.empathday.empathdayapi.IntegrationTestSupport;
import com.empathday.empathdayapi.domain.common.DeleteStatus;
import com.empathday.empathdayapi.domain.emotion.emotion.Emotion;
import com.empathday.empathdayapi.domain.schedule.ScheduleService;
import com.empathday.empathdayapi.domain.user.User;
import com.empathday.empathdayapi.domain.user.UserService;
import com.empathday.empathdayapi.infrastructure.schedule.ScheduleRepository;
import com.empathday.empathdayapi.infrastructure.schedule.comment.CommentRepository;
import com.empathday.empathdayapi.infrastructure.user.UserRepository;
import com.empathday.empathdayapi.interfaces.comment.CommentDto.DeleteCommentRequest;
import com.empathday.empathdayapi.interfaces.comment.CommentDto.ModifyCommentRequest;
import com.empathday.empathdayapi.interfaces.comment.CommentDto.RegisterCommentRequest;
import com.empathday.empathdayapi.interfaces.comment.CommentDto.RegisterCommentResponse;
import com.empathday.empathdayapi.interfaces.comment.CommentDto.RetrieveCommentResponse;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.RegisterScheduleRequest;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.RegisterScheduleResponse;
import com.empathday.empathdayapi.interfaces.user.UserSignUpDto;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

class CommentServiceTest extends IntegrationTestSupport {

    @Autowired
    private UserService userService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    EntityManager em;

    @AfterEach
    void tearDown() {
        userRepository.deleteAllInBatch();
        commentRepository.deleteAllInBatch();
        scheduleRepository.deleteAllInBatch();
    }

    @DisplayName("댓글(1뎁스) 정보를 등록합니다")
    @Test
    void registerComment1Depth() throws Exception {
        // given
        LocalDate now = LocalDate.of(2023, 9, 10);
        UserSignUpDto userRequest = createUser();
        User user = userService.signUp(userRequest);

        RegisterScheduleRequest request = createScheduleRequest(now, "스케줄 제목", "스케줄 내용", Emotion.GOOD);
        RegisterScheduleResponse savedSchedule = scheduleService.registerSchedule(request);

        String commentContent = "부모댓글 등록";
        RegisterCommentRequest commentRequest = createCommentRequest(user.getId(), null, commentContent);

        // when
        RegisterCommentResponse comment = commentService.registerComment(savedSchedule.getId(), commentRequest);

        // then
        assertThat(comment.getId()).isNotNull();
        assertThat(comment.getContent()).isEqualTo(commentContent);
        assertThat(comment.getChildComments()).isEmpty();
    }

    @DisplayName("대댓글(2뎁스) 정보를 등록합니다")
    @Test
    void registerComment2Depth() throws Exception {
        // given
        LocalDate now = LocalDate.of(2023, 9, 10);
        UserSignUpDto userRequest = createUser();
        User savedUser = userService.signUp(userRequest);

        RegisterScheduleRequest request = createScheduleRequest(now, "스케줄 제목", "스케줄 내용", Emotion.GOOD);
        RegisterScheduleResponse savedSchedule = scheduleService.registerSchedule(request);

        RegisterCommentResponse parentCommentRes
            = commentService.registerComment(savedSchedule.getId(), createCommentRequest(savedUser.getId(), null, "부모 댓글 등록"));

        RegisterCommentRequest childComment = createCommentRequest(savedUser.getId(), parentCommentRes.getId(), "자식 댓글 등록");

        // when
        commentService.registerComment(savedSchedule.getId(), childComment);

        // then
        List<Comment> allComment = commentService.getAllCommentByParentId(savedSchedule.getId(), savedUser.getId());
        Comment parent = allComment.stream()
            .filter(comment1 -> comment1.getParentComment() != null)
            .findFirst().get();

        assertThat(parent.getId()).isNotNull();
        assertThat(parent.getChildComment()).isNotNull();
    }

    @DisplayName("댓글 정보를 수정합니다")
    @Test
    void modifyComment() throws Exception {
        // given
        LocalDate now = LocalDate.of(2023, 9, 10);
        UserSignUpDto userRequest = createUser();
        User savedUser = userService.signUp(userRequest);

        RegisterScheduleRequest request = createScheduleRequest(now, "스케줄 제목", "스케줄 내용", Emotion.GOOD);
        RegisterScheduleResponse savedSchedule = scheduleService.registerSchedule(request);

        RegisterCommentResponse parentCommentRes
            = commentService.registerComment(savedSchedule.getId(), createCommentRequest(savedUser.getId(), null, "부모 댓글 등록"));

        // when
        String modifyCommentContent = "댓글 수정";
        ModifyCommentRequest modifyReq = createCommentModifyRequest(savedUser.getId(), modifyCommentContent);

        RetrieveCommentResponse res = commentService.modifyComment(savedSchedule.getId(), parentCommentRes.getId(), modifyReq);

        // then
        Assertions.assertThat(res.getContent()).isEqualTo(modifyCommentContent);
    }

    @DisplayName("1개의 댓글 삭제")
    @Test
    void deleteCommentOnlyOne() throws Exception {
        // given
        UserSignUpDto userRequest = createUser();
        User savedUser = userService.signUp(userRequest);
        RegisterScheduleRequest request = createScheduleRequest(LocalDate.of(2023, 9, 16), "스케줄제목", "스케줄내용", Emotion.SO_BAD);
        RegisterScheduleResponse savedSchedule = scheduleService.registerSchedule(request);

        RegisterCommentRequest commentRequest = createCommentRequest(savedUser.getId(), null, "부모댓글 등록");
        RegisterCommentResponse comment = commentService.registerComment(savedSchedule.getId(), commentRequest);

        DeleteCommentRequest deleteCommentRequest = new DeleteCommentRequest(comment.getId(), savedUser.getId());

        // when
        commentService.deleteComment(deleteCommentRequest);

        // then
        Comment findComment = commentRepository.findById(comment.getId()).get();
        assertThat(findComment.getDeleteStatus()).isEqualTo(DeleteStatus.Y);
    }

    private static RegisterScheduleRequest createScheduleRequest(LocalDate scheduleDate, String title, String content, Emotion emotion) {
        return RegisterScheduleRequest.builder()
            .userId(1L)
            .scheduleDate(scheduleDate)
            .title(title)
            .content(content)
            .imageId(null)
            .emotion(emotion)
            .scope(PUBLIC)
            .todos(null)
            .build();
    }

    private static RegisterCommentRequest createCommentRequest(Long userId, Long commentParentId, String content) {
        return RegisterCommentRequest.builder()
            .userId(userId)
            .commentParentId(commentParentId)
            .content(content)
            .build();
    }

    private static ModifyCommentRequest createCommentModifyRequest(Long userId, String content) {
        return ModifyCommentRequest.builder()
            .userId(userId)
            .content(content)
            .build();
    }

    private UserSignUpDto createUser() {
        return UserSignUpDto.builder()
            .email("empathyday@gmail.com")
            .nickname("nickName")
            .password(passwordEncoder.encode("1q2w3e4r!@"))
            .build();
    }
}