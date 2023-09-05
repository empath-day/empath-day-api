package com.empathday.empathdayapi.domain.schedule;

import com.empathday.empathdayapi.domain.schedule.emotion.Emotion;
import com.empathday.empathdayapi.infrastructure.schedule.ScheduleRepository;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.RegisterScheduleRequest;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.RetrieveScheduleMainResponse;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("test")
@SpringBootTest
class CalendarFactoryServiceTest {

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private ScheduleRepository scheduleRepository;

    @AfterEach
    void tearDown() {
        scheduleRepository.deleteAllInBatch();
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
//        List<Schedule> schedules = scheduleRepository.findAllByUserIdAndScheduleDateBetween(1L, monday, sunday).get();
//        List<RetrieveScheduleMainResponse> result = CalendarFactory.createOneWeekCalendarForUser(now, schedules);

        // then
        for (RetrieveScheduleMainResponse mainResponse : result) {
            System.out.println("mainResponse = " + mainResponse);
        }

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