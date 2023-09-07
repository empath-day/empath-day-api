package com.empathday.empathdayapi.infrastructure.schedule.feed;

import com.empathday.empathdayapi.domain.feed.like.ScheduleLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleLikeRepository extends JpaRepository<ScheduleLike, Long> {

    Optional<ScheduleLike> findByUserIdAndScheduleId(Long userId, Long id);
}
