package com.empathday.empathdayapi.interfaces.schedule;

import static java.util.Collections.EMPTY_LIST;

import com.empathday.empathdayapi.common.utils.NumberUtils;
import com.empathday.empathdayapi.domain.schedule.Schedule;
import com.empathday.empathdayapi.domain.emotion.emotion.Emotion;
import com.empathday.empathdayapi.domain.schedule.Schedule.Scope;
import com.empathday.empathdayapi.domain.schedule.scheduleimage.ScheduleImage;
import com.empathday.empathdayapi.domain.schedule.todo.Todo;
import com.empathday.empathdayapi.interfaces.comment.ScheduleCommentDto.RetrieveCommentResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
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

        @NotNull(message = "userId는 필수 정보입니다.")
        private Long userId;
        private LocalDate scheduleDate;
        private String title;
        private String content;
        private Long imageId;
        @NotNull(message = "emotion 정보는 필수 정보입니다.")
        private Emotion emotion;
        private Scope scope;
        private List<String> todos;

        public Schedule toEntity(ScheduleImage scheduleImage) {
            if (scheduleImage == null) {
                return Schedule.of(
                    this.userId, this.scheduleDate, this.title, this.content, EMPTY_LIST, this.emotion, this.scope
                );
            }

            return Schedule.of(
                this.userId, this.scheduleDate, this.title, this.content, List.of(scheduleImage), this.emotion, this.scope
            );
        }

        @JsonIgnore
        public boolean imageExists() {
            return NumberUtils.isNotNullOrZero(this.imageId);
        }

        @JsonIgnore
        public boolean todoExists() {
            return !CollectionUtils.isEmpty(todos);
        }

        @JsonIgnore
        public void setScheduleImageId(Long imageId) {
            this.imageId = imageId;
        }

        @JsonIgnore
        public void setTodoContents(ArrayList<String> todoContents) {
            this.todos = todoContents;
        }

        @JsonIgnore
        public boolean isEmotionBlank() {
            return Emotion.isAvailable(this.emotion) == null;
        }
    }

    /** response **/
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    public static class RetrieveScheduleMainResponse {

        private RetrieveScheduleResponse scheduleResponse;
        private DefaultCalendarInfo calendarInfo;

        public void mappingSchedule(RetrieveScheduleResponse response) {
            scheduleResponse.mappingFrom(response);
        }
    }

    @Getter
    @Builder
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DefaultCalendarInfo {

        private LocalDate date;
        private DayOfWeek day;

        public static DefaultCalendarInfo create(LocalDate date, DayOfWeek day) {
            return DefaultCalendarInfo.builder()
                .date(date)
                .day(day)
                .build();
        }
    }

    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    public static class RetrieveScheduleDetailMainResponse {

        private RetrieveScheduleResponse scheduleResponse;
        private List<RetrieveCommentResponse> comments;
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
        private String emotionImageUrl;
        private Scope scope;
        private List<ScheduleImageResponse> images;
        private List<TodoResponse> todos;

        public static RetrieveScheduleResponse fromEntity(Schedule schedule) {
            return RetrieveScheduleResponse.builder()
                .id(schedule.getId())
                .scheduleDate(schedule.getScheduleDate())
                .title(schedule.getTitle())
                .content(schedule.getContent())
                .emotion(schedule.getEmotion())
                .emotionImageUrl(schedule.getEmotion().getEmotionImageUrl())
                .scope(schedule.getScope())
                .images(checkImageIsNull(schedule.getScheduleImages()))
                .todos(checkTodoIsNull(schedule.getTodos()))
                .build();
        }

        private static List<ScheduleImageResponse> checkImageIsNull(List<ScheduleImage> scheduleImages) {
            if (CollectionUtils.isEmpty(scheduleImages)) {
                return null;
            }

            return ScheduleImageResponse.fromEntity(scheduleImages);
        }

        private static List<TodoResponse> checkTodoIsNull(List<Todo> todos) {
            if (CollectionUtils.isEmpty(todos)) {
                return null;
            }

            return TodoResponse.fromEntity(todos);
        }

        public void mappingFrom(RetrieveScheduleResponse response) {
            this.id = response.getId();
            this.scheduleDate = response.getScheduleDate();
            this.title = response.getTitle();
            this.content = response.getContent();
            this.emotion = response.getEmotion();
            this.emotionImageUrl = response.getEmotion().getEmotionImageUrl();
            this.scope = response.getScope();
            this.images = response.getImages();
            this.todos = response.getTodos();
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
                .filter(Objects::nonNull)
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
