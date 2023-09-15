package com.empathday.empathdayapi.domain.schedule.comment;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

import com.empathday.empathdayapi.domain.emotion.emotion.Emotion;
import com.empathday.empathdayapi.domain.schedule.Schedule;
import com.empathday.empathdayapi.domain.schedule.ScheduleService;
import com.empathday.empathdayapi.domain.user.User;
import com.empathday.empathdayapi.domain.user.UserService;
import com.empathday.empathdayapi.infrastructure.schedule.ScheduleRepository;
import com.empathday.empathdayapi.infrastructure.schedule.comment.CommentRepository;
import com.empathday.empathdayapi.infrastructure.user.UserRepository;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleCommentDto.RegisterScheduleCommentRequest;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.RegisterScheduleRequest;
import com.empathday.empathdayapi.interfaces.user.UserSignUpDto;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class CommentServiceTest {

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
        Schedule savedSchedule = scheduleService.registerSchedule(request);

        RegisterScheduleCommentRequest commentRequest = createCommentRequest(savedSchedule.getId(), user.getId(), null, "부모댓글 등록");

        // when
        Comment comment = commentService.registerComment(commentRequest);

        // then
        assertThat(comment.getId()).isNotNull();
        assertThat(comment.getParentComment()).isNull();
        assertThat(comment.getChildComment()).isNull();
    }

    @DisplayName("대댓글(2뎁스) 정보를 등록합니다")
    @Test
    void registerComment2Depth() throws Exception {
        // given
        LocalDate now = LocalDate.of(2023, 9, 10);
        UserSignUpDto userRequest = createUser();
        User savedUser = userService.signUp(userRequest);

        RegisterScheduleRequest request = createScheduleRequest(now, "스케줄 제목", "스케줄 내용", Emotion.GOOD);
        Schedule savedSchedule = scheduleService.registerSchedule(request);

        RegisterScheduleCommentRequest commentRequest = createCommentRequest(savedSchedule.getId(), savedUser.getId(), null, "부모댓글 등록");
        Comment comment = commentService.registerComment(commentRequest);

        RegisterScheduleCommentRequest replyRequest = createCommentRequest(savedSchedule.getId(), savedUser.getId(), comment.getId(), "자식 댓글 등록");

        // when
        Comment reply = commentService.registerComment(replyRequest);

        // then
        List<Comment> allComment = commentService.getAllCommentByParentId(savedSchedule.getId(), savedUser.getId());
        Comment parent = allComment.stream()
            .filter(comment1 -> comment1.getParentComment() != null)
            .findFirst().get();

        assertThat(parent.getId()).isNotNull();
        assertThat(parent.getChildComment()).isNotNull();
    }

    private static RegisterScheduleRequest createScheduleRequest(LocalDate scheduleDate, String title, String content, Emotion emotion) {
        RegisterScheduleRequest registerScheduleRequest = RegisterScheduleRequest.builder()
            .userId(1L)
            .scheduleDate(scheduleDate)
            .title(title)
            .content(content)
            .imageId(null)
            .emotion(emotion)
            .isPublic(true)
            .todos(null)
            .build();
        return registerScheduleRequest;
    }

    private static RegisterScheduleCommentRequest createCommentRequest(Long scheduleId, Long userId, Long commentParentId, String content) {
        return RegisterScheduleCommentRequest.builder()
            .scheduleId(scheduleId)
            .userId(userId)
            .commentParentId(commentParentId)
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