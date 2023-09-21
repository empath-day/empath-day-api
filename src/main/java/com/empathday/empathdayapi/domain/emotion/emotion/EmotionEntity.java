package com.empathday.empathdayapi.domain.emotion.emotion;

import static javax.persistence.EnumType.STRING;

import com.empathday.empathdayapi.domain.common.AbstractEntity;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "emotions")
public class EmotionEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(STRING)
    private Emotion emotion;

    private String emotionImageUrl;

    public EmotionEntity(Emotion emotion) {
        this.emotion = emotion;
        this.emotionImageUrl = emotion.getEmotionImageUrl();
    }
}
