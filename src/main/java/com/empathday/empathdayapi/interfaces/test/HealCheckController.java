package com.empathday.empathdayapi.interfaces.test;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealCheckController {

    @Operation(
        summary = "헬스 체크",
        description = "일일 단위의 스케줄 정보를 조회할 수 있습니다."
    )
    @GetMapping("/api/v1/health-check")
    public String healthCheck() {
        return "ok";
    }
}
