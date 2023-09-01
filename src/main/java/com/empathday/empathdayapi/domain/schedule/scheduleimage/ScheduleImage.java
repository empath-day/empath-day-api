package com.empathday.empathdayapi.domain.schedule.scheduleimage;

import static javax.persistence.FetchType.LAZY;

import com.empathday.empathdayapi.domain.common.AbstractEntity;
import com.empathday.empathdayapi.domain.schedule.Schedule;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "schedule_iamges")
public class ScheduleImage extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    /** 생성 메서드 **/
    public ScheduleImage(String filename) {
        this.filename = filename;
    }

    public static ScheduleImage toEntity(String filename) {
        return new ScheduleImage(filename);
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
