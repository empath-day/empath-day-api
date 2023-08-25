package com.empathday.empathdayapi.interfaces.schedule;

import com.empathday.empathdayapi.domain.schedule.Emotion;
import com.empathday.empathdayapi.domain.schedule.Schedule;
import com.empathday.empathdayapi.domain.schedule.ScheduleImage;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

public class ScheduleDto {

    /** request **/
    @Getter
    @ToString
    @NoArgsConstructor
    public static class RegisterScheduleRequest {

        private LocalDate scheduleDate;
        private String title;
        private String content;
        private Long imageId;
        private Emotion emotion;

        public Schedule toEntity(ScheduleImage scheduleImage) {
            return new Schedule(title, content, List.of(scheduleImage), emotion);
        }
    }
}
