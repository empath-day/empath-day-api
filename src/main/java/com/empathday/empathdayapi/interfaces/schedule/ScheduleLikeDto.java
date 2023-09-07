package com.empathday.empathdayapi.interfaces.schedule;

import javax.validation.constraints.NotBlank;
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

        @NotBlank(message = "userId 정보는 필수 정보입니다.")
        private Long userId;
        @NotBlank(message = "feedId 정보는 필수 정보입니다.")
        private Long feedId;
    }
}
