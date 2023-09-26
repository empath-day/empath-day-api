package com.empathday.empathdayapi.domain.schedule;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSchedule is a Querydsl query type for Schedule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSchedule extends EntityPathBase<Schedule> {

    private static final long serialVersionUID = -1764284261L;

    public static final QSchedule schedule = new QSchedule("schedule");

    public final com.empathday.empathdayapi.domain.common.QAbstractEntity _super = new com.empathday.empathdayapi.domain.common.QAbstractEntity(this);

    public final ListPath<com.empathday.empathdayapi.domain.schedule.comment.Comment, com.empathday.empathdayapi.domain.schedule.comment.QComment> comments = this.<com.empathday.empathdayapi.domain.schedule.comment.Comment, com.empathday.empathdayapi.domain.schedule.comment.QComment>createList("comments", com.empathday.empathdayapi.domain.schedule.comment.Comment.class, com.empathday.empathdayapi.domain.schedule.comment.QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final EnumPath<com.empathday.empathdayapi.domain.common.DeleteStatus> deleteStatus = createEnum("deleteStatus", com.empathday.empathdayapi.domain.common.DeleteStatus.class);

    public final EnumPath<com.empathday.empathdayapi.domain.emotion.emotion.Emotion> emotion = createEnum("emotion", com.empathday.empathdayapi.domain.emotion.emotion.Emotion.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DatePath<java.time.LocalDate> scheduleDate = createDate("scheduleDate", java.time.LocalDate.class);

    public final ListPath<com.empathday.empathdayapi.domain.schedule.scheduleimage.ScheduleImage, com.empathday.empathdayapi.domain.schedule.scheduleimage.QScheduleImage> scheduleImages = this.<com.empathday.empathdayapi.domain.schedule.scheduleimage.ScheduleImage, com.empathday.empathdayapi.domain.schedule.scheduleimage.QScheduleImage>createList("scheduleImages", com.empathday.empathdayapi.domain.schedule.scheduleimage.ScheduleImage.class, com.empathday.empathdayapi.domain.schedule.scheduleimage.QScheduleImage.class, PathInits.DIRECT2);

    public final EnumPath<Schedule.Scope> scope = createEnum("scope", Schedule.Scope.class);

    public final StringPath title = createString("title");

    public final ListPath<com.empathday.empathdayapi.domain.schedule.todo.Todo, com.empathday.empathdayapi.domain.schedule.todo.QTodo> todos = this.<com.empathday.empathdayapi.domain.schedule.todo.Todo, com.empathday.empathdayapi.domain.schedule.todo.QTodo>createList("todos", com.empathday.empathdayapi.domain.schedule.todo.Todo.class, com.empathday.empathdayapi.domain.schedule.todo.QTodo.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QSchedule(String variable) {
        super(Schedule.class, forVariable(variable));
    }

    public QSchedule(Path<? extends Schedule> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSchedule(PathMetadata metadata) {
        super(Schedule.class, metadata);
    }

}

