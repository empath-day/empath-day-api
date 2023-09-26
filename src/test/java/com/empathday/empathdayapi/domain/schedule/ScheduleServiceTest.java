package com.empathday.empathdayapi.domain.schedule;

import static com.empathday.empathdayapi.domain.schedule.Schedule.Scope.PUBLIC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.empathday.empathdayapi.IntegrationTestSupport;
import com.empathday.empathdayapi.common.utils.NumberUtils;
import com.empathday.empathdayapi.domain.emotion.emotion.Emotion;
import com.empathday.empathdayapi.domain.schedule.scheduleimage.ScheduleImage;
import com.empathday.empathdayapi.infrastructure.schedule.ScheduleImageRepository;
import com.empathday.empathdayapi.infrastructure.schedule.ScheduleRepository;
import com.empathday.empathdayapi.infrastructure.schedule.comment.CommentRepository;
import com.empathday.empathdayapi.infrastructure.schedule.todo.TodoRepository;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.RegisterScheduleRequest;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.RegisterScheduleResponse;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.RetrieveScheduleMainResponse;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.RetrieveScheduleResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

class ScheduleServiceTest extends IntegrationTestSupport {

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ScheduleImageRepository scheduleImageRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TodoRepository todoRepository;

    @AfterEach
    void tearDown() {
        commentRepository.deleteAllInBatch();
        todoRepository.deleteAllInBatch();
        scheduleRepository.deleteAllInBatch();
        scheduleImageRepository.deleteAllInBatch();
    }

    @DisplayName("이미지 정보가 없이 스케쥴을 등록할 수 있다.")
    @Test
    void saveScheduleWithoutImage() {
        // given
        LocalDate scheduleDate = LocalDate.of(2023, 8, 25);
        String title = "스케쥴 제목";
        String content = "스케쥴 내용";

        // when
        RegisterScheduleRequest request = createScheduleRequest(scheduleDate, title, content, Emotion.SO_BAD, null, null);
        RegisterScheduleResponse savedSchedule = scheduleService.registerSchedule(request);

        // then
        assertThat(savedSchedule.getId()).isNotNull();
        assertThat(savedSchedule.getScheduleDate()).isEqualTo(scheduleDate);
        assertThat(savedSchedule.getTitle()).isEqualTo(title);
        assertThat(savedSchedule.getImages()).isEmpty();
    }

    @DisplayName("감정(기분) 정보만으로도 스케쥴을 등록할 수 있다.")
    @Test
    void saveScheduleOnlyEmotion() {
        // given
        LocalDate scheduleDate = LocalDate.of(2023, 8, 25);
        String title = "";
        String content = "";
        Emotion emotion = Emotion.SO_BAD;

        RegisterScheduleRequest request = createScheduleRequest(scheduleDate, title, content, emotion, null, null);

        // when
        RegisterScheduleResponse savedSchedule = scheduleService.registerSchedule(request);

        // then
        assertThat(savedSchedule.getId()).isNotNull();
        assertThat(savedSchedule.getScheduleDate()).isEqualTo(scheduleDate);
        assertThat(savedSchedule.getTitle()).isEqualTo(title);
        assertThat(savedSchedule.getContent()).isEqualTo(content);
        assertThat(savedSchedule.getImages()).isEmpty();
        assertThat(savedSchedule.getEmotion()).isEqualTo(emotion);
    }

    @DisplayName("스케쥴을 등록할때, 할일(Todo)도 함께 등록할 수 있다.")
    @Test
    void saveScheduleWithTodos() {
        // given
        LocalDate scheduleDate = LocalDate.of(2023, 8, 25);
        String title = "";
        String content = "";
        Emotion emotion = Emotion.SO_BAD;
        RegisterScheduleRequest request = createScheduleRequest(scheduleDate, title, content, emotion, null, List.of("투두리스트1", "투두리스트2"));

        // when
        RegisterScheduleResponse savedSchedule = scheduleService.registerSchedule(request);

        // then
        assertThat(savedSchedule.getId()).isNotNull();
        assertThat(savedSchedule.getTodos()).hasSize(2);
    }

    @DisplayName("스케쥴을 등록할 수 있습니다.")
    @Test
    void saveSchedule() {
        // given
        LocalDate scheduleDate = LocalDate.of(2023, 8, 25);
        String title = "";
        String content = "";
        Emotion emotion = Emotion.SO_BAD;
        RegisterScheduleRequest request = createScheduleRequest(scheduleDate, title, content, emotion, null, null);

        // when
        RegisterScheduleResponse savedSchedule = scheduleService.registerSchedule(request);

        // then
        assertThat(savedSchedule.getId()).isNotNull();
        assertThat(savedSchedule.getScheduleDate()).isEqualTo(scheduleDate);
        assertThat(savedSchedule.getImages()).isEmpty();
        assertThat(savedSchedule.getScope()).isEqualTo(PUBLIC);
        assertThat(savedSchedule.getTodos()).isEmpty();
    }

    @DisplayName("스케쥴과 이미지를 함께 등록할 수 있습니다.")
    @Test
    void saveScheduleWithScheduleImage() {
        // given
        LocalDate scheduleDate = LocalDate.of(2023, 8, 25);
        String title = "";
        String content = "";
        Emotion emotion = Emotion.SO_BAD;
        ScheduleImage scheduleImage = new ScheduleImage("newFile.jpg");
        scheduleImageRepository.save(scheduleImage);

        RegisterScheduleRequest request = createScheduleRequest(scheduleDate, title, content, emotion, null, null);
        request.setScheduleImageId(scheduleImage.getId());

        // when
        RegisterScheduleResponse savedSchedule = scheduleService.registerSchedule(request);

        // then
        assertThat(savedSchedule.getId()).isNotNull();
        assertThat(savedSchedule.getScheduleDate()).isEqualTo(scheduleDate);
        assertThat(savedSchedule.getImages()).hasSize(1);
        assertThat(savedSchedule.getScope()).isEqualTo(PUBLIC);
        assertThat(savedSchedule.getTodos()).isEmpty();
    }

    @DisplayName("스케쥴과 투두릭스트 정보를 함께 등록할 수 있습니다.")
    @Test
    void saveScheduleWithTodoList() {
        // given
        LocalDate scheduleDate = LocalDate.of(2023, 8, 25);
        String title = "";
        String content = "";
        Emotion emotion = Emotion.SO_BAD;
        RegisterScheduleRequest request = createScheduleRequest(scheduleDate, title, content, emotion, null, List.of("오늘 하루는~~", "피곤한 하루다~~"));

        // when
        RegisterScheduleResponse savedSchedule = scheduleService.registerSchedule(request);

        // then
        assertThat(savedSchedule.getId()).isNotNull();
        assertThat(savedSchedule.getScheduleDate()).isEqualTo(scheduleDate);
        assertThat(savedSchedule.getImages()).hasSize(0);
        assertThat(savedSchedule.getScope()).isEqualTo(PUBLIC);
        assertThat(savedSchedule.getTodos()).hasSize(2);
    }

    @DisplayName("스케줄의 상세정보를 조회할 수 있다")
    @Test
    void retrieveScheduleDetail() {
        // given
        LocalDate scheduleDate = LocalDate.of(2023, 8, 25);
        String title = "";
        String content = "";
        Emotion emotion = Emotion.SO_BAD;
        String firstTodo = "오늘 하루는~~";
        String secondTodo = "피곤한 하루다~~";

        RegisterScheduleRequest request = createScheduleRequest(scheduleDate, title, content, emotion, null, List.of(firstTodo, secondTodo));
        RegisterScheduleResponse savedSchedule = scheduleService.registerSchedule(request);

        // when
        RetrieveScheduleResponse result = scheduleService.retrieveScheduleDetail(savedSchedule.getId(), 1L).getScheduleResponse();

        // then
        assertThat(result.getScheduleDate()).isEqualTo(scheduleDate);
        assertThat(result.getTitle()).isEqualTo(title);
        assertThat(result.getContent()).isEqualTo(content);
        assertThat(result.getEmotion()).isEqualTo(emotion);
        assertThat(result.getImages()).isNull();
        assertThat(result.getTodos()).hasSize(2)
            .extracting("content", "isCompleted")
            .containsExactlyInAnyOrder(
                tuple(firstTodo, false),
                tuple(secondTodo, false)
            );
    }

    @DisplayName("회원의 1주일 스케줄 정보를 조회합니다.")
    @Test
    void createOneWeekCalendarForUser() {
        // given
        LocalDate monday = LocalDate.of(2023, 9, 4);
        LocalDate thuesday = LocalDate.of(2023, 9, 5);
        LocalDate wednesday = LocalDate.of(2023, 9, 6);

        RegisterScheduleRequest monReq = createScheduleRequest(monday, "월요일", "월요일 스케줄", Emotion.SO_BAD, null, null);
        RegisterScheduleRequest thuReq = createScheduleRequest(thuesday, "화요일", "화요일 스케줄", Emotion.BAD, null, null);
        RegisterScheduleRequest wedReq = createScheduleRequest(wednesday, "수요일", "수요일 스케줄", Emotion.GOOD, null, null);

        scheduleService.registerSchedule(monReq);
        scheduleService.registerSchedule(thuReq);
        scheduleService.registerSchedule(wedReq);

        // when
        List<RetrieveScheduleMainResponse> result = scheduleService.retrieveOneWeekScheduleInfo(1L, thuesday);

        // then
        List<RetrieveScheduleMainResponse> filtered = result.stream()
            .filter(response -> NumberUtils.isNotNullOrZero(response.getScheduleResponse().getId()))
            .collect(Collectors.toList());

        assertThat(result).hasSize(7);
        assertThat(filtered).hasSize(3);
    }

    private static RegisterScheduleRequest createScheduleRequest(
        LocalDate scheduleDate, String title, String content,
        Emotion emotion, Long imageId, List<String> todos
    ) {
        return RegisterScheduleRequest.builder()
            .userId(1L)
            .scheduleDate(scheduleDate)
            .title(title)
            .content(content)
            .imageId(imageId)
            .emotion(emotion)
            .scope(PUBLIC)
            .todos(todos)
            .build();
    }
}