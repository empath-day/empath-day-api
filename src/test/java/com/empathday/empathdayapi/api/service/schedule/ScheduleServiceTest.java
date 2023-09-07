package com.empathday.empathdayapi.api.service.schedule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.empathday.empathdayapi.common.utils.NumberUtils;
import com.empathday.empathdayapi.domain.schedule.Schedule;
import com.empathday.empathdayapi.domain.schedule.ScheduleService;
import com.empathday.empathdayapi.domain.emotion.emotion.Emotion;
import com.empathday.empathdayapi.domain.schedule.scheduleimage.ScheduleImage;
import com.empathday.empathdayapi.domain.schedule.todo.Todo;
import com.empathday.empathdayapi.infrastructure.schedule.ScheduleImageRepository;
import com.empathday.empathdayapi.infrastructure.schedule.ScheduleRepository;
import com.empathday.empathdayapi.infrastructure.schedule.todo.TodoRepository;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.RegisterScheduleRequest;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.RetrieveScheduleMainResponse;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.RetrieveScheduleResponse;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ScheduleServiceTest {

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ScheduleImageRepository scheduleImageRepository;
    @Autowired
    private TodoRepository todoRepository;

    @AfterEach
    void tearDown() {
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

        Schedule schedule = Schedule.of(1L, scheduleDate, title, content, null, Emotion.SO_BAD, true);

        // when
        scheduleRepository.save(schedule);

        // then
        assertThat(schedule.getScheduleDate()).isEqualTo(scheduleDate);
        assertThat(schedule.getTitle()).isEqualTo(title);
        assertThat(schedule.getScheduleImages()).isNull();
    }

    @DisplayName("감정(기분) 정보만으로도 스케쥴을 등록할 수 있다.")
    @Test
    void saveScheduleOnlyEmotion() {
        // given
        LocalDate scheduleDate = LocalDate.of(2023, 8, 25);
        String title = "";
        String content = "";
        Emotion emotion = Emotion.SO_BAD;

        Schedule schedule = Schedule.of(1L, scheduleDate, title, content, null, emotion, true);

        // when
        scheduleRepository.save(schedule);

        // then
        assertThat(schedule.getScheduleDate()).isEqualTo(scheduleDate);
        assertThat(schedule.getTitle()).isEqualTo(title);
        assertThat(schedule.getContent()).isEqualTo(content);
        assertThat(schedule.getScheduleImages()).isNull();
        assertThat(schedule.getEmotion()).isEqualTo(emotion);
    }

    @DisplayName("스케쥴을 등록할때, 할일(Todo)도 함께 등록할 수 있다.")
    @Test
    void saveScheduleWithTodos() {
        // given
        LocalDate scheduleDate = LocalDate.of(2023, 8, 25);
        String title = "";
        String content = "";
        Emotion emotion = Emotion.SO_BAD;
        Schedule schedule = Schedule.of(1L, scheduleDate, title, content, null, emotion, true);
        Todo todo1 = Todo.of(schedule, "투두리스트1");
        Todo todo2 = Todo.of(schedule, "투두리스트2");
        schedule.addScheduleTodos(List.of(todo1, todo2));

        // when
        scheduleRepository.save(schedule);

        // then
        assertThat(schedule.getId()).isNotNull();
        assertThat(schedule.getTodos()).hasSize(2);
    }

    @DisplayName("스케쥴을 등록할 수 있습니다.")
    @Test
    void saveSchedule() {
        // given
        LocalDate scheduleDate = LocalDate.of(2023, 8, 25);
        String title = "";
        String content = "";
        Emotion emotion = Emotion.SO_BAD;
        RegisterScheduleRequest registerScheduleRequest = createScheduleRequest(scheduleDate, title, content, emotion);

        // when
        Schedule savedSchedule = scheduleService.createSchedule(registerScheduleRequest);

        // then
        assertThat(savedSchedule.getId()).isNotNull();
        assertThat(savedSchedule.getScheduleDate()).isEqualTo(scheduleDate);
        assertThat(savedSchedule.getScheduleImages()).hasSize(0);
        assertThat(savedSchedule.isPublic()).isTrue();
        assertThat(savedSchedule.getTodos()).isNull();
    }

    @DisplayName("스케쥴과 이미지를 함께 등록할 수 있습니다.")
    @Test
    void saveScheduleWithScheduleImage() {
        // given
        LocalDate scheduleDate = LocalDate.of(2023, 8, 25);
        String title = "";
        String content = "";
        Emotion emotion = Emotion.SO_BAD;
        String filename = "newFile.jpg";
        ScheduleImage scheduleImage = new ScheduleImage(filename);
        scheduleImageRepository.save(scheduleImage);
        RegisterScheduleRequest registerScheduleRequest = createScheduleRequest(scheduleDate, title, content, emotion);
        registerScheduleRequest.setScheduleImageId(scheduleImage.getId());

        // when
        Schedule savedSchedule = scheduleService.createSchedule(registerScheduleRequest);

        // then
        assertThat(savedSchedule.getId()).isNotNull();
        assertThat(savedSchedule.getScheduleDate()).isEqualTo(scheduleDate);
        assertThat(savedSchedule.getScheduleImages()).hasSize(1);
        assertThat(savedSchedule.isPublic()).isTrue();
        assertThat(savedSchedule.getTodos()).isNull();
    }

    @DisplayName("스케쥴과 투두릭스트 정보를 함께 등록할 수 있습니다.")
    @Test
    void saveScheduleWithTodoList() {
        // given
        LocalDate scheduleDate = LocalDate.of(2023, 8, 25);
        String title = "";
        String content = "";
        Emotion emotion = Emotion.SO_BAD;
        RegisterScheduleRequest registerScheduleRequest = createScheduleRequest(scheduleDate, title, content, emotion);
        ArrayList<String> todoContents = new ArrayList<>();
//        todoContents.addAll(List.of("오늘 하루는~~", "피곤한 하루다~~"));
        todoContents.add("오늘 하루는~~");
        todoContents.add("피곤한 하루다~~");
        registerScheduleRequest.setTodoContents(todoContents);

        // when
        Schedule savedSchedule = scheduleService.createSchedule(registerScheduleRequest);

        // then
        assertThat(savedSchedule.getId()).isNotNull();
        assertThat(savedSchedule.getScheduleDate()).isEqualTo(scheduleDate);
        assertThat(savedSchedule.getScheduleImages()).hasSize(0);
        assertThat(savedSchedule.isPublic()).isTrue();
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
        RegisterScheduleRequest registerScheduleRequest = createScheduleRequest(scheduleDate, title, content, emotion);

        ArrayList<String> todoContents = new ArrayList<>();
        String firstTodo = "오늘 하루는~~";
        String secondTodo = "피곤한 하루다~~";

        todoContents.addAll(List.of(firstTodo, secondTodo));
        registerScheduleRequest.setTodoContents(todoContents);

        Schedule savedSchedule = scheduleService.createSchedule(registerScheduleRequest);

        // when
        RetrieveScheduleResponse result = scheduleService.retrieveScheduleDetail(savedSchedule.getId(), 1L).getScheduleResponse();

        // then
        assertThat(result.getScheduleDate()).isEqualTo(scheduleDate);
        assertThat(result.getTitle()).isEqualTo(title);
        assertThat(result.getContent()).isEqualTo(content);
        assertThat(result.getEmotion()).isEqualTo(emotion);
        assertThat(result.getImageResponses()).isNull();
        assertThat(result.getTodoResponses()).hasSize(2)
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
        LocalDate now = LocalDate.of(2023, 9, 5);
        LocalDate monday = LocalDate.of(2023, 9, 4);
        LocalDate thuesday = LocalDate.of(2023, 9, 5);
        LocalDate wednesday = LocalDate.of(2023, 9, 6);
        LocalDate sunday = now.with(DayOfWeek.SUNDAY);

        RegisterScheduleRequest monReq = createScheduleRequest(monday, "월요일", "월요일 스케줄", Emotion.SO_BAD);
        RegisterScheduleRequest thuReq = createScheduleRequest(thuesday, "화요일", "화요일 스케줄", Emotion.BAD);
        RegisterScheduleRequest wedReq = createScheduleRequest(wednesday, "수요일", "수요일 스케줄", Emotion.GOOD);

        scheduleService.createSchedule(monReq);
        scheduleService.createSchedule(thuReq);
        scheduleService.createSchedule(wedReq);

        // when
        List<RetrieveScheduleMainResponse> result = scheduleService.retrieveOneWeekScheduleInfo(1L);

        // then
        List<RetrieveScheduleMainResponse> filtered = result.stream()
            .filter(response -> NumberUtils.isNotNullOrZero(response.getScheduleResponse().getId()))
            .collect(Collectors.toList());

        assertThat(result).hasSize(7);
        assertThat(filtered).hasSize(3);
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
}