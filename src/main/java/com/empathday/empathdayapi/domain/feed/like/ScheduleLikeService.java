package com.empathday.empathdayapi.domain.feed.like;

import com.empathday.empathdayapi.domain.schedule.Schedule;
import com.empathday.empathdayapi.domain.schedule.ScheduleService;
import com.empathday.empathdayapi.exception.feed.like.FeedLikeNotFoundException;
import com.empathday.empathdayapi.infrastructure.schedule.feed.ScheduleLikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ScheduleLikeService {

    private final ScheduleService scheduleService;
    private final ScheduleLikeRepository scheduleLikeRepository;

    public void likeSchedule(Long scheduleId, Long userId) {
        Schedule schedule = scheduleService.getScheduleInfo(scheduleId);

        saveLike(userId, schedule);
    }

    private void saveLike(Long userId, Schedule schedule) {
        scheduleLikeRepository.save(ScheduleLike.createLikeEntity(userId, schedule));
    }

    @Transactional
    public void unLikeSchedule(Long scheduleId, Long userId) {
        Schedule schedule = scheduleService.getScheduleInfo(scheduleId);

        ScheduleLike scheduleLike = getScheduleLike(userId, schedule);

        scheduleLike.unLike();
    }

    private ScheduleLike getScheduleLike(Long userId, Schedule schedule) {
        return scheduleLikeRepository.findByUserIdAndScheduleId(userId, schedule.getId()).orElseThrow(
            () -> new FeedLikeNotFoundException()
        );
    }

    private void saveUnLike(Long userId, Schedule schedule) {
        scheduleLikeRepository.save(ScheduleLike.createUnLikeEntity(userId, schedule));
    }
}
