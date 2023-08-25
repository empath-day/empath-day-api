package com.empathday.empathdayapi.infrastructure.schedule;

import com.empathday.empathdayapi.domain.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

}
