package com.empathday.empathdayapi.interfaces.emotion;

import com.empathday.empathdayapi.common.response.CommonResponse;
import com.empathday.empathdayapi.domain.emotion.EmotionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class EmotionController {

    private final EmotionService emotionService;

    @Operation(
        summary = "감정 정보 조회",
        description = "스케줄 등록시 필요한 감정 정보 목록을 조회합니다."
    )
    @GetMapping("/api/v1/emotion")
    public CommonResponse retrieveEmotions() {
        return CommonResponse.success(emotionService.retrieveEmotions());
    }
}
