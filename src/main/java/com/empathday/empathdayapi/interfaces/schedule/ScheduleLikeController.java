package com.empathday.empathdayapi.interfaces.schedule;

import com.empathday.empathdayapi.common.response.CommonResponse;
import com.empathday.empathdayapi.domain.schedule.like.ScheduleLikeService;
import com.empathday.empathdayapi.interfaces.schedule.ScheduleLikeDto.CommandScheduleLikeRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ScheduleLikeController {

    private final ScheduleLikeService scheduleLikeService;

    @PutMapping("/api/v1/schedule/like")
    public CommonResponse<String> likeSchedule(
        @RequestBody CommandScheduleLikeRequest request
    ) {
        scheduleLikeService.likeSchedule(request.getFeedId(), request.getUserId());

        return CommonResponse.success("OK");
    }

    @PutMapping("/api/v1/schedule/unlike")
    public CommonResponse<String> unLikeSchedule(
        @RequestBody CommandScheduleLikeRequest request
    ) {
        scheduleLikeService.unLikeSchedule(request.getFeedId(), request.getUserId());

        return CommonResponse.success("OK");
    }
}
