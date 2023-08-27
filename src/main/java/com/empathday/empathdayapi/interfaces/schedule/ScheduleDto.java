package com.empathday.empathdayapi.interfaces.schedule;

import static java.util.Collections.EMPTY_LIST;

import com.empathday.empathdayapi.common.utils.NumberUtils;
import com.empathday.empathdayapi.domain.schedule.Schedule;
import com.empathday.empathdayapi.domain.schedule.emotion.Emotion;
import com.empathday.empathdayapi.domain.schedule.scheduleimage.ScheduleImage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.CollectionUtils;

public class ScheduleDto {

    /** request **/
    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RegisterScheduleRequest {

        private Long userId;
        private LocalDate scheduleDate;
        private String title;
        private String content;
        private Long imageId;
        private Emotion emotion;
        private boolean isPublic;
        private List<String> todos;

        public Schedule toEntity(ScheduleImage scheduleImage) {
            if (scheduleImage == null) {
                return Schedule.of(
                    this.userId, this.scheduleDate, this.title, this.content, EMPTY_LIST, this.emotion, this.isPublic
                );
            }

            return Schedule.of(
                this.userId, this.scheduleDate, this.title, this.content, List.of(scheduleImage), this.emotion, this.isPublic
            );
        }

        public boolean imageExists() {
            return NumberUtils.isNotNullOrZero(this.imageId);
        }

        public boolean todoExists() {
            return !CollectionUtils.isEmpty(todos);
        }

        public void setScheduleImageId(Long imageId) {
            this.imageId = imageId;
        }

        public void setTodoContents(ArrayList<String> todoContents) {
            this.todos = todoContents;
        }
    }
}
