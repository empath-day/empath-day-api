package com.empathday.empathdayapi.domain.schedule.scheduleimage;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QScheduleImage is a Querydsl query type for ScheduleImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QScheduleImage extends EntityPathBase<ScheduleImage> {

    private static final long serialVersionUID = 1631977782L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QScheduleImage scheduleImage = new QScheduleImage("scheduleImage");

    public final com.empathday.empathdayapi.domain.common.QAbstractEntity _super = new com.empathday.empathdayapi.domain.common.QAbstractEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath filename = createString("filename");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.empathday.empathdayapi.domain.schedule.QSchedule schedule;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QScheduleImage(String variable) {
        this(ScheduleImage.class, forVariable(variable), INITS);
    }

    public QScheduleImage(Path<? extends ScheduleImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QScheduleImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QScheduleImage(PathMetadata metadata, PathInits inits) {
        this(ScheduleImage.class, metadata, inits);
    }

    public QScheduleImage(Class<? extends ScheduleImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.schedule = inits.isInitialized("schedule") ? new com.empathday.empathdayapi.domain.schedule.QSchedule(forProperty("schedule")) : null;
    }

}

