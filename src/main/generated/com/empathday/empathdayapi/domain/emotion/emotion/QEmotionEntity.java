package com.empathday.empathdayapi.domain.emotion.emotion;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEmotionEntity is a Querydsl query type for EmotionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEmotionEntity extends EntityPathBase<EmotionEntity> {

    private static final long serialVersionUID = -978579643L;

    public static final QEmotionEntity emotionEntity = new QEmotionEntity("emotionEntity");

    public final com.empathday.empathdayapi.domain.common.QAbstractEntity _super = new com.empathday.empathdayapi.domain.common.QAbstractEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final EnumPath<Emotion> emotion = createEnum("emotion", Emotion.class);

    public final StringPath emotionImageUrl = createString("emotionImageUrl");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QEmotionEntity(String variable) {
        super(EmotionEntity.class, forVariable(variable));
    }

    public QEmotionEntity(Path<? extends EmotionEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEmotionEntity(PathMetadata metadata) {
        super(EmotionEntity.class, metadata);
    }

}

