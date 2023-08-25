package com.empathday.empathdayapi.domain.schedule;

import static javax.persistence.EnumType.STRING;

import com.empathday.empathdayapi.domain.common.AbstractEntity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Schedule extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate scheduleDate;

    @Enumerated(STRING)
    private Emotion emotion;

    @OneToMany(mappedBy = "schedule", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ScheduleImage> scheduleImage = new ArrayList<>();

    private String title;

    private String content;

    public Schedule(List<ScheduleImage> scheduleImage) {
        this.scheduleImage = scheduleImage;
    }

    public Schedule(String title, String content, List<ScheduleImage> scheduleImages, Emotion emotion) {
        this.title = title;
        this.content = content;
        this.scheduleImage = scheduleImages;
        this.emotion = emotion;
    }
}
