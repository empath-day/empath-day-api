package com.empathday.empathdayapi.api.service.schedule;

import static org.assertj.core.api.Assertions.assertThat;

import com.empathday.empathdayapi.domain.schedule.Emotion;
import com.empathday.empathdayapi.domain.schedule.Schedule;
import com.empathday.empathdayapi.domain.schedule.ScheduleImage;
import com.empathday.empathdayapi.domain.schedule.ScheduleService;
import com.empathday.empathdayapi.infrastructure.schedule.ScheduleImageRepository;
import com.empathday.empathdayapi.infrastructure.schedule.ScheduleRepository;
import java.time.LocalDate;
import java.util.List;
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

    @AfterEach
    void tearDown() {
        scheduleRepository.deleteAllInBatch();
        scheduleImageRepository.deleteAllInBatch();
    }

    @DisplayName("스케쥴을 등록할 수 있습니다.")
    @Test
    void saveSchedule() {
        // given
        LocalDate scheduleDate = LocalDate.of(2023, 8, 25);
        ScheduleImage scheduleImage = createScheduleImage("scheduleImage.jpg");
        scheduleImageRepository.save(scheduleImage);

        // when
        Schedule schedule = createSchedule(scheduleDate, Emotion.SO_BAD, scheduleImage);
        scheduleRepository.save(schedule);

        // then
        assertThat(schedule.getId()).isEqualTo(1L);
        assertThat(schedule.getEmotion()).isEqualTo(Emotion.SO_BAD);
        assertThat(schedule.getScheduleDate()).isEqualTo(scheduleDate);
    }

    public static Schedule createSchedule(LocalDate scheduleDate, Emotion emotion, ScheduleImage scheduleImage) {
        Schedule schedule = Schedule.builder()
            .scheduleDate(scheduleDate)
            .emotion(emotion)
            .title("하루공감")
            .content("오늘은 금요일입니다.")
            .build();

        schedule.addScheduleImage(List.of(scheduleImage));

        return schedule;
    }

    public static ScheduleImage createScheduleImage(String filename) {
        return ScheduleImage.builder()
            .filename(filename)
            .build();
    }
}