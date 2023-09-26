package com.empathday.empathdayapi.domain.schedule.like;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QScheduleLike is a Querydsl query type for ScheduleLike
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QScheduleLike extends EntityPathBase<ScheduleLike> {

    private static final long serialVersionUID = 243621505L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QScheduleLike scheduleLike = new QScheduleLike("scheduleLike");

    public final com.empathday.empathdayapi.domain.common.QAbstractEntity _super = new com.empathday.empathdayapi.domain.common.QAbstractEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<ScheduleLike.LikeStatus> likeStatus = createEnum("likeStatus", ScheduleLike.LikeStatus.class);

    public final com.empathday.empathdayapi.domain.schedule.QSchedule schedule;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QScheduleLike(String variable) {
        this(ScheduleLike.class, forVariable(variable), INITS);
    }

    public QScheduleLike(Path<? extends ScheduleLike> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QScheduleLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QScheduleLike(PathMetadata metadata, PathInits inits) {
        this(ScheduleLike.class, metadata, inits);
    }

    public QScheduleLike(Class<? extends ScheduleLike> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.schedule = inits.isInitialized("schedule") ? new com.empathday.empathdayapi.domain.schedule.QSchedule(forProperty("schedule")) : null;
    }

}

