package com.empathday.empathdayapi.domain.schedule;

import static javax.persistence.EnumType.STRING;

import com.empathday.empathdayapi.domain.common.AbstractEntity;
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
import org.hibernate.annotations.ColumnDefault;

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

    @ColumnDefault("false")
    @Column(columnDefinition = "TINYINT(1)")
    private boolean isPublic;

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

    /** 생성 메서드 **/
    public Schedule(
        Long userId, LocalDate scheduleDate, String title, String content,
        List<ScheduleImage> scheduleImages, Emotion emotion, boolean isPublic
    ) {
        this.userId = userId;
        this.scheduleDate = scheduleDate;
        this.title = title;
        this.content = content;
        this.scheduleImages = scheduleImages;
        this.emotion = emotion;
        this.isPublic = isPublic;
    }

    public static Schedule of(
        Long userId, LocalDate scheduleDate, String title, String content,
        List<ScheduleImage> scheduleImage, Emotion emotion, boolean isPublic
    ) {
        return new Schedule(
            userId,
            scheduleDate,
            StringUtils.isNotBlank(title) ? title : "",
            StringUtils.isNotBlank(content) ? content : "",
            scheduleImage,
            emotion,
            isPublic
        );
    }

    public void addScheduleImage(List<ScheduleImage> scheduleImage) {
        this.scheduleImages = scheduleImage;
    }

    public void addScheduleTodos(List<Todo> todos) {
        this.todos = todos;
        todos.forEach(todo -> todo.addSchedule(this));
    }
}
