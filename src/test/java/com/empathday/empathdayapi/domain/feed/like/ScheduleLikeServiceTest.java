package com.empathday.empathdayapi.domain.feed.like;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import com.empathday.empathdayapi.domain.emotion.emotion.Emotion;
import com.empathday.empathdayapi.domain.schedule.like.ScheduleLike.LikeStatus;
import com.empathday.empathdayapi.domain.schedule.Schedule;
import com.empathday.empathdayapi.domain.schedule.ScheduleService;
import com.empathday.empathdayapi.domain.schedule.like.ScheduleLikeService;
import com.empathday.empathdayapi.infrastructure.schedule.feed.ScheduleLikeRepository;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.RegisterScheduleRequest;
import java.time.LocalDate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ScheduleLikeServiceTest {

    @Autowired
    private ScheduleLikeService scheduleLikeService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private ScheduleLikeRepository scheduleLikeRepository;

    @AfterEach
    void tearDown() {
        scheduleLikeRepository.deleteAllInBatch();
    }

    @DisplayName("스케줄을 좋아요할 수 있습니다.")
    @Test
    void likeSchedule() {
        // given
        Long userId = 1L;
        LocalDate scheduleDate = LocalDate.of(2023, 8, 25);
        String title = "";
        String content = "";
        Emotion emotion = Emotion.SO_BAD;
        RegisterScheduleRequest request = createScheduleRequest(scheduleDate, title, content, emotion);

        Schedule savedSchedule = scheduleService.registerSchedule(request);

        // when
        scheduleLikeService.likeSchedule(savedSchedule.getId(), userId);

        // then
        assertThat(scheduleLikeRepository.findAll()).hasSize(1)
            .extracting("userId", "likeStatus")
            .containsExactlyInAnyOrder(
                tuple(userId, LikeStatus.LIKE)
            );
    }

    @DisplayName("스케줄을 좋아요 취소 할 수 있습니다.")
    @Test
    void unLikeSchedule() {
        // given
        Long userId = 1L;
        LocalDate scheduleDate = LocalDate.of(2023, 8, 25);
        String title = "";
        String content = "";
        Emotion emotion = Emotion.SO_BAD;
        RegisterScheduleRequest request = createScheduleRequest(scheduleDate, title, content, emotion);

        Schedule savedSchedule = scheduleService.registerSchedule(request);
        scheduleLikeService.likeSchedule(savedSchedule.getId(), userId);

        // when
        scheduleLikeService.unLikeSchedule(savedSchedule.getId(), userId);

        // then
        assertThat(scheduleLikeRepository.findAll()).hasSize(1)
            .extracting("userId", "likeStatus")
            .containsExactlyInAnyOrder(
                tuple(userId, LikeStatus.UN_LIKE)
            );
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