package com.empathday.empathdayapi.interfaces.schedule;

import com.empathday.empathdayapi.domain.schedule.ScheduleService;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.RegisterScheduleRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping("/api/v1/schedule")
    public ResponseEntity<String> createSchedule(
        @RequestBody RegisterScheduleRequest request
    ) {
        scheduleService.createSchedule(request);

        return ResponseEntity.ok("ok");
    }
}
