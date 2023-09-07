package com.empathday.empathdayapi.interfaces.schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class ScheduleLikeDto {

    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    public static class CommandScheduleLikeRequest {

        private Long userId;
        private Long feedId;
    }
}
