package com.empathday.empathdayapi.interfaces.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealCheckController {

    @GetMapping("/api/v1/health-check")
    public String healthCheck() {
        return "ok";
    }
}
