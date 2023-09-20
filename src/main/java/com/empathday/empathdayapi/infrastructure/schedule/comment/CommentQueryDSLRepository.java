package com.empathday.empathdayapi.infrastructure.schedule.comment;


import static com.empathday.empathdayapi.domain.schedule.comment.QComment.comment;

import com.empathday.empathdayapi.domain.common.DeleteStatus;
import com.empathday.empathdayapi.domain.schedule.Schedule;
import com.empathday.empathdayapi.domain.schedule.comment.Comment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CommentQueryDSLRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Comment> findAllBySchedule(Schedule schedule){
        return jpaQueryFactory.selectFrom(comment)
            .leftJoin(comment.parentComment)
            .fetchJoin()
            .where(comment.schedule.id.eq(schedule.getId()))
            .orderBy(comment.parentComment.id.asc().nullsFirst(), comment.createdAt.asc())
            .fetch();
    }
}