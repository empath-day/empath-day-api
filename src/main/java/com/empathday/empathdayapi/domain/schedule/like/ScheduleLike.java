package com.empathday.empathdayapi.domain.schedule.like;

import com.empathday.empathdayapi.domain.common.AbstractEntity;
import com.empathday.empathdayapi.domain.schedule.Schedule;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "schedule_likes")
public class ScheduleLike extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @Enumerated(EnumType.STRING)
    private LikeStatus likeStatus;

    public static ScheduleLike createLikeEntity(Long userId, Schedule schedule) {
        return ScheduleLike.builder()
            .userId(userId)
            .schedule(schedule)
            .likeStatus(LikeStatus.LIKE)
            .build();
    }

    public void unLike() {
        this.likeStatus = LikeStatus.UN_LIKE;
    }

    public enum LikeStatus {
        LIKE,
        UN_LIKE
    }
}