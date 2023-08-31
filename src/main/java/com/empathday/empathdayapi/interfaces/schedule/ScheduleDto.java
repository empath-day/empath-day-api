package com.empathday.empathdayapi.interfaces.schedule;

import static java.util.Collections.EMPTY_LIST;

import com.empathday.empathdayapi.common.utils.NumberUtils;
import com.empathday.empathdayapi.domain.schedule.Schedule;
import com.empathday.empathdayapi.domain.schedule.emotion.Emotion;
import com.empathday.empathdayapi.domain.schedule.scheduleimage.ScheduleImage;
import com.empathday.empathdayapi.domain.schedule.todo.Todo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotEmpty;
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
        @NotEmpty(message = "감정 표현을 선택해주세요.")
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

    /** response **/
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    public static class RetrieveScheduleMainResponse {

        private RetrieveScheduleResponse scheduleResponse;
    }

    @Getter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RetrieveScheduleResponse {

        private Long id;
        private LocalDate scheduleDate;
        private String title;
        private String content;
        private Emotion emotion;
        private boolean isPublic;
        private List<ScheduleImageResponse> imageResponses;
        private List<TodoResponse> todoResponses;

        public static RetrieveScheduleResponse fromEntity(Schedule schedule) {
            return RetrieveScheduleResponse.builder()
                .id(schedule.getId())
                .scheduleDate(schedule.getScheduleDate())
                .title(schedule.getTitle())
                .content(schedule.getContent())
                .emotion(schedule.getEmotion())
                .isPublic(schedule.isPublic())
                .imageResponses(ScheduleImageResponse.fromEntity(schedule.getScheduleImages()))
                .todoResponses(TodoResponse.fromEntity(schedule.getTodos()))
                .build();
        }
    }

    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    public static class ScheduleImageResponse {

        private Long id;
        private String filename;

        public static List<ScheduleImageResponse> fromEntity(List<ScheduleImage> scheduleImages) {
            return scheduleImages.stream()
                .map(image -> ScheduleImageResponse.of(image.getId(), image.getFilename()))
                .collect(Collectors.toList());
        }
    }

    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    public static class TodoResponse {

        private Long id;
        private String content;
        private boolean isCompleted;

        public static List<TodoResponse> fromEntity(List<Todo> todos) {
            return todos.stream()
                .map(todo -> TodoResponse.of(todo.getId(), todo.getContent(), todo.isCompleted()))
                .collect(Collectors.toList());
        }
    }
}
