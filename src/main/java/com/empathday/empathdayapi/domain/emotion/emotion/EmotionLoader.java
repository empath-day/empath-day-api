package com.empathday.empathdayapi.domain.emotion.emotion;

import com.empathday.empathdayapi.infrastructure.schedule.emotion.EmotionRepository;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EmotionLoader {

    private final EmotionRepository emotionRepository;

    @PostConstruct
    public void initEmotion() {
        for (Emotion emotion : Emotion.values()) {
            emotionRepository.save(new EmotionEntity(emotion));
        }
    }
}
