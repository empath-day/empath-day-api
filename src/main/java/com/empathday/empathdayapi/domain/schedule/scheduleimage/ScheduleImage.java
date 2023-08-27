package com.empathday.empathdayapi.domain.schedule.scheduleimage;

import com.empathday.empathdayapi.domain.common.AbstractEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class ScheduleImage extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

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
