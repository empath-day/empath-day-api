package com.empathday.empathdayapi.interfaces.schedule;

import static com.empathday.empathdayapi.common.response.CommonResponse.success;

import com.empathday.empathdayapi.common.response.CommonResponse;
import com.empathday.empathdayapi.domain.schedule.ScheduleService;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleDto.RegisterScheduleRequest;
import io.swagger.v3.oas.annotations.Operation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Operation(
        summary = "스케줄 등록",
        description = "스케줄을 등록할 수 있습니다."
    )
    @PostMapping("/api/v1/schedules")
    public CommonResponse<String> createSchedule(
        @RequestBody RegisterScheduleRequest request
    ) {
        scheduleService.createSchedule(request);

        return success("OK");
    }

    @Operation(
        summary = "스케줄 조회",
        description = "일일 단위의 스케줄 정보를 조회할 수 있습니다."
    )
    @GetMapping("/api/v1/schedules/{id}")
    public CommonResponse retrieveScheduleDetail(
        @PathVariable("id") Long id
    ) {
        return success(scheduleService.retrieveScheduleDetail(id, 0L));
    }
}
