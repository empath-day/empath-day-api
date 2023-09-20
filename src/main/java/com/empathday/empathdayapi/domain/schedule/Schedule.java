package com.empathday.empathdayapi.domain.schedule;

import static javax.persistence.EnumType.STRING;

import com.empathday.empathdayapi.domain.common.AbstractEntity;
import com.empathday.empathdayapi.domain.common.DeleteStatus;
import com.empathday.empathdayapi.domain.emotion.emotion.Emotion;
import com.empathday.empathdayapi.domain.schedule.comment.Comment;
import com.empathday.empathdayapi.domain.schedule.scheduleimage.ScheduleImage;
import com.empathday.empathdayapi.domain.schedule.todo.Todo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "schedules")
public class  Schedule extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate scheduleDate;
    private String title;
    private String content;

    @Enumerated(EnumType.STRING)
    private Scope scope;

    @Enumerated(STRING)
    private Emotion emotion;

    @Builder.Default
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.PERSIST)
    private List<Comment> comments = new ArrayList<>();

    private Long userId;

    @Builder.Default
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.PERSIST)
    private List<ScheduleImage> scheduleImages = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "schedule", cascade = CascadeType.PERSIST)
    private List<Todo> todos = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DeleteStatus deleteStatus;

    /** 생성 메서드 **/
    public Schedule(
        Long userId, LocalDate scheduleDate, String title, String content,
        List<ScheduleImage> scheduleImages, Emotion emotion, Scope scope, DeleteStatus deleteStatus
    ) {
        this.userId = userId;
        this.scheduleDate = scheduleDate;
        this.title = title;
        this.content = content;
        this.scheduleImages = scheduleImages;
        this.emotion = emotion;
        this.scope = scope;
        this.deleteStatus = deleteStatus;
    }

    public static Schedule of(
        Long userId, LocalDate scheduleDate, String title, String content,
        List<ScheduleImage> scheduleImage, Emotion emotion, Scope scope
    ) {
        return new Schedule(
            userId,
            scheduleDate,
            StringUtils.isNotBlank(title) ? title : "",
            StringUtils.isNotBlank(content) ? content : "",
            scheduleImage,
            emotion,
            scope,
            DeleteStatus.N
        );
    }

    public void addScheduleImage(List<ScheduleImage> scheduleImage) {
        this.scheduleImages = scheduleImage;
    }

    public void addScheduleTodos(List<Todo> todos) {
        this.todos = todos;
        todos.forEach(todo -> todo.addSchedule(this));
    }

    public enum Scope {
        PUBLIC, PRIVATE;
    }
}
