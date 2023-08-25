package com.empathday.empathdayapi.infrastructure.schedule;

import com.empathday.empathdayapi.domain.schedule.ScheduleImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleImageRepository extends JpaRepository<ScheduleImage, Long> {

}
