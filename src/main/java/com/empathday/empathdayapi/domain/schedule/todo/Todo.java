package com.empathday.empathdayapi.domain.schedule.todo;

import static javax.persistence.FetchType.LAZY;

import com.empathday.empathdayapi.domain.common.AbstractEntity;
import com.empathday.empathdayapi.domain.schedule.Schedule;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "todos")
public class Todo extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    private String content;

    private boolean isCompleted;

    @Builder
    private Todo(Schedule schedule, String content, boolean isCompleted) {
        this.schedule = schedule;
        this.content = content;
        this.isCompleted = isCompleted;
    }

    public static Todo of(Schedule schedule, String content) {
        return Todo.builder()
            .schedule(schedule)
            .content(content)
            .isCompleted(false)
            .build();
    }
}
